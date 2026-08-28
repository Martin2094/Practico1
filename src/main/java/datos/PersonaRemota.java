package datos;

import jakarta.ejb.Remote;
import java.util.List;

import entidadMG.Persona;

@Remote
public interface PersonaRemota {
	void agregar(Persona persona);

    List<Persona> listar();

    Persona buscarPorCI(Integer CI);
}
