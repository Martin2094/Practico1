package negocio;

import java.util.List;
import jakarta.ejb.Remote;

import entidadMG.Persona;

@Remote
public interface PersonaNegocioRemota {
	void agregar(Persona persona);

    List<Persona> listar();

    Persona buscarPorCI(Integer CI);
}
