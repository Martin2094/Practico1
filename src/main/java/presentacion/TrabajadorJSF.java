package presentacion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import entidadMG.TrabajadorSalud;
import negocio.TrabajadorNegLocal;

@Named("trabajadorSaludJSF")
@ViewScoped
public class TrabajadorJSF implements Serializable {
    private static final long serialVersionUID = 1L;

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
        TrabajadorSalud trabajador = new TrabajadorSalud();
        trabajador.setCI(ci);
        trabajador.setNombre(nombre);
        trabajador.setNacimiento(nacimiento);
        try {
            trabajadorNegocio.agregar(trabajador);
            mensaje = "Trabajador de la salud agregado correctamente.";
            limpiarFormulario();
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
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