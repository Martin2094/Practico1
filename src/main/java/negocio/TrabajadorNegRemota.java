package negocio;

import java.util.List;
import jakarta.ejb.Remote;

import entidadMG.TrabajadorSalud;

@Remote
public interface TrabajadorNegRemota {
    void agregar(TrabajadorSalud trabajador);

    List<TrabajadorSalud> listar();

    TrabajadorSalud buscarPorCI(Integer CI);
}