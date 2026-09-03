package consola;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

import entidadMG.TrabajadorSalud;
import negocio.TrabajadorNegRemota;

public class ConsolaJava {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Context contexto = crearContexto();
            TrabajadorNegRemota service = obtenerServicioRemoto(contexto);
            ConnectionFactory connectionFactory = (ConnectionFactory) contexto.lookup("jms/RemoteConnectionFactory");
            Queue colaAlta = (Queue) contexto.lookup("jms/queue/queue_alta_trabajador");
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
                        agregarTrabajador(
                                scanner,
                                connectionFactory,
                                colaAlta
                        );
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
            contexto.close();
        } catch (Exception e) {
            System.out.println("Error al conectarse con WildFly:");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static Context crearContexto() throws Exception {
        Hashtable<String, Object> propiedades = new Hashtable<>();
        propiedades.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        propiedades.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        propiedades.put("jboss.naming.client.ejb.context", true);
        return new InitialContext(propiedades);
    }

    private static TrabajadorNegRemota obtenerServicioRemoto(Context contexto) throws Exception {
        String jndi =
                "ejb:/Practico1-1.0.0/"
                + "TrabajadorNegBean!"
                + "negocio.TrabajadorNegRemota";
        return (TrabajadorNegRemota) contexto.lookup(jndi);
    }

    private static void agregarTrabajador(Scanner scanner, ConnectionFactory connectionFactory, Queue colaAlta) {
        try {
            System.out.print("CI: ");
            Integer ci = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Fecha nacimiento (DD-MM-AAAA): ");
            String fechaTexto = scanner.nextLine();
            LocalDate fechaNacimiento = LocalDate.parse(fechaTexto, FORMATO_FECHA);
            String mensajeAlta =
                    ci
                    + "|"
                    + nombre
                    + "|"
                    + fechaNacimiento.format(
                            FORMATO_FECHA
                    );
            try (JMSContext contextoJMS = connectionFactory.createContext("martin", "martin")) {
                contextoJMS.createProducer().send(colaAlta, mensajeAlta);
            }
            System.out.println("Solicitud de alta enviada correctamente.");
        } catch (Exception e) {
            System.out.println("Error enviando solicitud de alta: " + e.getMessage());
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
                            .format(FORMATO_FECHA)
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
        System.out.println("Fecha nacimiento: " + trabajador.getNacimiento().format(FORMATO_FECHA));
    }
}