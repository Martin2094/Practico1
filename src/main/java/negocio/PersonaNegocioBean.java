package negocio;

import java.util.List;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import datos.PersonaLocal;
import entidadMG.Persona;

@Stateless
public class PersonaNegocioBean implements PersonaNegocioLocal, PersonaNegocioRemota {
	@EJB
	private PersonaLocal personaDAO;
	
	@Override
	public void agregar(Persona persona) {
		Persona existeP = personaDAO.buscarPorCI(persona.getCI());
		if (existeP != null) {
			throw new IllegalArgumentException("Ya existe una persona con esa CI");
		}
		personaDAO.agregar(persona);
	}
	
	@Override
	public List<Persona> listar() {
		return personaDAO.listar();
	}
	
	@Override
	public Persona buscarPorCI(Integer CI) {
		return personaDAO.buscarPorCI(CI);
	}
}
