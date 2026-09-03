package mensajeria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

import entidadMG.TrabajadorSalud;
import negocio.TrabajadorNegLocal;

@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/queue_alta_trabajador")
    }
)
public class TrabajadorMDB implements MessageListener {
    @EJB
    private TrabajadorNegLocal trabajadorNegocio;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage textMessage) {
                String contenido = textMessage.getText();
                System.out.println("MDB - Mensaje recibido: " + contenido);

                String[] datos = contenido.split("\\|");

                Integer ci = Integer.parseInt(datos[0]);
                String nombre = datos[1];

                LocalDate nacimiento = LocalDate.parse(datos[2], FORMATO_FECHA);

                TrabajadorSalud trabajador = new TrabajadorSalud();

                trabajador.setCI(ci);
                trabajador.setNombre(nombre);
                trabajador.setNacimiento(nacimiento);

                trabajadorNegocio.agregar(trabajador);

                System.out.println("MDB - Trabajador agregado: " + nombre);
            }

        } catch (Exception e) {
            System.err.println("MDB - Error procesando mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
}