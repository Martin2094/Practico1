package datos;

import jakarta.ejb.Local;
import java.util.List;

import entidadMG.Persona;

@Local
public interface PersonaLocal {
	void agregar(Persona persona);
	
	List<Persona> listar();
	
	Persona buscarPorCI(Integer CI);
}
