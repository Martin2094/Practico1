package negocio;

import java.util.List;
import jakarta.ejb.Local;

import entidadMG.Persona;

@Local
public interface PersonaNegocioLocal {
	void agregar(Persona persona);

    List<Persona> listar();

    Persona buscarPorCI(Integer CI);
}
