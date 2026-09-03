package presentacion;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.jms.ConnectionFactory;
import jakarta.annotation.Resource;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

import entidadMG.TrabajadorSalud;
import negocio.TrabajadorNegLocal;

@Named("trabajadorSaludJSF")
@ViewScoped
public class TrabajadorJSF implements Serializable {
    private static final long serialVersionUID = 1L;

    @Resource(lookup = "java:/JmsXA")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/jms/queue/queue_alta_trabajador")
    private Queue colaAlta;
    
    @EJB
    private TrabajadorNegLocal trabajadorNegocio;

    private Integer ci;
    private String nombre;
    private LocalDate nacimiento;

    private Integer ciBusqueda;
    private TrabajadorSalud trabajadorEncontrado;

    private String mensaje;
    private String error;

    public void agregar() {
        mensaje = null;
        error = null;
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String mensajeAlta = ci + "|" + nombre + "|" + nacimiento.format(formato);
            
            try (JMSContext contextoJMS = connectionFactory.createContext()) {
                contextoJMS.createProducer().send(colaAlta, mensajeAlta);
            }
            mensaje = "Solicitud de alta enviada correctamente.";
            limpiarFormulario();
        } catch (Exception e) {
            error = "Error enviando solicitud de alta: " + e.getMessage();
        }
    }

    public void buscar() {
        mensaje = null;
        error = null;
        trabajadorEncontrado = trabajadorNegocio.buscarPorCI(ciBusqueda);
        if (trabajadorEncontrado == null) {
            error = "No existe un trabajador de la salud con esa CI.";
        }
    }

    public List<TrabajadorSalud> getTrabajadores() {
        return trabajadorNegocio.listar();
    }

    private void limpiarFormulario() {
        ci = null;
        nombre = null;
        nacimiento = null;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getCiBusqueda() {
        return ciBusqueda;
    }

    public void setCiBusqueda(Integer ciBusqueda) {
        this.ciBusqueda = ciBusqueda;
    }

    public TrabajadorSalud getTrabajadorEncontrado() {
        return trabajadorEncontrado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getError() {
        return error;
    }
}