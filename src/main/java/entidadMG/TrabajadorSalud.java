package entidadMG;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trabajadores_salud")
public class TrabajadorSalud implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ci")
    private Integer CI;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "nacimiento")
    private LocalDate nacimiento;

    @Column(name = "especialidad")
    private String especialidad;

    public TrabajadorSalud() {
    }

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