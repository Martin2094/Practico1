package entidadMG;

import java.io.Serializable;
import java.time.LocalDate;

public class TrabajadorSalud implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer CI;

    private String nombre;

    private LocalDate nacimiento;

    private String especialidad;

    public Integer getCI() {
        return CI;
    }

    public void setCI(Integer CI) {
        this.CI = CI;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}