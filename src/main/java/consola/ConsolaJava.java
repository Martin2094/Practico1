package consola;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;

import entidadMG.TrabajadorSalud;
import negocio.TrabajadorNegRemota;

public class ConsolaJava {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            TrabajadorNegRemota service = obtenerServicioRemoto();
            int opcion;
            do {
                System.out.println();
                System.out.println("=== GESTOR DE TRABAJADORES DE LA SALUD ===");
                System.out.println("1 - Agregar trabajador de la salud");
                System.out.println("2 - Listar trabajadores de la salud");
                System.out.println("3 - Buscar trabajador de la salud");
                System.out.println("0 - Salir");
                System.out.print("Opcion: ");
 
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        agregarTrabajador(scanner, service);
                        break;
                    case 2:
                        listarTrabajadores(service);
                        break;
                    case 3:
                        buscarTrabajador(scanner, service);
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
    private static TrabajadorNegRemota obtenerServicioRemoto()
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
                + "TrabajadorSaludNegocioBean!"
                + "negocio.TrabajadorSaludNegocioRemota";
        return (TrabajadorNegRemota) contexto.lookup(jndi);
    }
    
    private static void agregarTrabajador(Scanner scanner, TrabajadorNegRemota service) {
        System.out.print("CI: ");
        Integer ci = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Fecha nacimiento (AAAA-MM-DD): ");
        String fechaTexto = scanner.nextLine();

        LocalDate fechaNacimiento = LocalDate.parse(fechaTexto);

        TrabajadorSalud trabajador = new TrabajadorSalud();

        trabajador.setCI(ci);
        trabajador.setNombre(nombre);
        trabajador.setNacimiento(fechaNacimiento);
        try {
            service.agregar(trabajador);
            System.out.println("Trabajador agregado correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void listarTrabajadores(TrabajadorNegRemota service) {
        List<TrabajadorSalud> trabajadores = service.listar();
        if (trabajadores.isEmpty()) {
            System.out.println("No hay trabajadores registrados.");
            return;
        }
        for (TrabajadorSalud trabajador : trabajadores) {
            System.out.println(
                    trabajador.getCI()
                    + " | "
                    + trabajador.getNombre()
                    + " | "
                    + trabajador.getNacimiento()
            );
        }
    }

    private static void buscarTrabajador(Scanner scanner, TrabajadorNegRemota service) {
        System.out.print("CI: ");
        Integer ci = scanner.nextInt();
        scanner.nextLine();
        TrabajadorSalud trabajador = service.buscarPorCI(ci);
        if (trabajador == null) {
            System.out.println("No existe un trabajador con esa CI.");
            return;
        }
        System.out.println("CI: " + trabajador.getCI());
        System.out.println("Nombre: " + trabajador.getNombre());
        System.out.println("Fecha nacimiento: " + trabajador.getNacimiento());
    }
}