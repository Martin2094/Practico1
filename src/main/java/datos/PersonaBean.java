package datos;

import java.util.ArrayList;
import java.util.List;
import jakarta.ejb.Singleton;

import entidadMG.Persona;

@Singleton
public class PersonaBean implements PersonaLocal, PersonaRemota{
	private List<Persona> personas = new ArrayList<>(); // Lista donde se guardan las personas
	
	private Integer Cedula = -1;
	
	@Override
	public void agregar(Persona persona) {
		personas.add(persona);
	}

	@Override
	public List<Persona> listar() {
		return new ArrayList<>(personas);
	}
	
	@Override
	public Persona buscarPorCI(Integer CI) {
		for (Persona persona : personas) {
			Cedula = persona.getCI();
			if (Cedula == CI) {
				return persona;
			}
		}
		return null;
	}
}
