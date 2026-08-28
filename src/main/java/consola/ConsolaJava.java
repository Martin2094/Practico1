package consola;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;

import entidadMG.Persona;
import negocio.PersonaNegocioRemota;

public class ConsolaJava {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

        	PersonaNegocioRemota service = obtenerServicioRemoto();

            int opcion;

            do {

                System.out.println();
                System.out.println("=== GESTOR DE PERSONAS ===");
                System.out.println("1 - Agregar persona");
                System.out.println("2 - Listar personas");
                System.out.println("3 - Buscar persona");
                System.out.println("0 - Salir");
                System.out.print("Opcion: ");

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {

                    case 1:
                        agregarPersona(scanner, service);
                        break;

                    case 2:
                        listarPersonas(service);
                        break;

                    case 3:
                        buscarPersona(scanner, service);
                        break;

                    case 0:
                        System.out.println("Fin del programa.");
                        break;

                    default:
                        System.out.println("Opcion invalida.");
                }

            } while (opcion != 0);

        } catch (Exception e) {

            System.out.println("Error al conectarse con WildFly:");
            e.printStackTrace();

        } finally {

            scanner.close();
        }
    }

    private static PersonaNegocioRemota obtenerServicioRemoto()
            throws Exception {

        Hashtable<String, Object> propiedades = new Hashtable<>();

        propiedades.put(
                Context.INITIAL_CONTEXT_FACTORY,
                "org.wildfly.naming.client.WildFlyInitialContextFactory"
        );

        propiedades.put(
                Context.PROVIDER_URL,
                "http-remoting://localhost:8080"
        );

        propiedades.put(
                "jboss.naming.client.ejb.context",
                true
        );

        Context contexto = new InitialContext(propiedades);

        String jndi =
                "ejb:/Practico1-1.0.0/"
                + "PersonaNegocioBean!"
                + "negocio.PersonaNegocioRemota";

        return (PersonaNegocioRemota) contexto.lookup(jndi);
    }

    private static void agregarPersona(
            Scanner scanner,
            PersonaNegocioRemota service) {

        System.out.print("ID: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Fecha nacimiento (AAAA-MM-DD): ");
        String fechaTexto = scanner.nextLine();

        LocalDate fechaNacimiento = LocalDate.parse(fechaTexto);

        Persona persona = new Persona();

        persona.setCI(id);
        persona.setNombre(nombre);
        persona.setNacimiento(fechaNacimiento);

        try {

            service.agregar(persona);

            System.out.println("Persona agregada correctamente.");

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarPersonas(
    		PersonaNegocioRemota service) {

        List<Persona> personas = service.listar();

        if (personas.isEmpty()) {

            System.out.println("No hay personas registradas.");
            return;
        }

        for (Persona persona : personas) {

            System.out.println(
                    persona.getCI()
                    + " | "
                    + persona.getNombre()
                    + " | "
                    + persona.getNacimiento()
            );
        }
    }

    private static void buscarPersona(
            Scanner scanner,
            PersonaNegocioRemota service) {

        System.out.print("ID: ");

        Integer id = scanner.nextInt();
        scanner.nextLine();

        Persona persona = service.buscarPorCI(id);

        if (persona == null) {

            System.out.println(
                    "No existe una persona con ese ID."
            );

            return;
        }

        System.out.println("ID: " + persona.getCI());
        System.out.println("Nombre: " + persona.getNombre());
        System.out.println(
                "Fecha nacimiento: "
                + persona.getNacimiento()
        );
    }
}