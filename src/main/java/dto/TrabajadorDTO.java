package dto;

public class TrabajadorDTO {
    private Integer ci;
    private String nombre;
    private String nacimiento;

    public TrabajadorDTO() {
    }

    public TrabajadorDTO(Integer ci, String nombre, String nacimiento) {
        this.ci = ci;
        this.nombre = nombre;
        this.nacimiento = nacimiento;
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

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }
}