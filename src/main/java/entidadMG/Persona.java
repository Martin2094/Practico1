package entidadMG;

import java.time.LocalDate;

public class Persona {
	private Integer CI;
	private String nombre;
	private LocalDate nacimiento;
	
	public int getCI() {
		return CI;
	}
	public void setCI(Integer cI) {
		this.CI = cI;
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
}
