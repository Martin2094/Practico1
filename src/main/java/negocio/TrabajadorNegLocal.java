package negocio;

import java.util.List;
import jakarta.ejb.Local;

import entidadMG.TrabajadorSalud;

@Local
public interface TrabajadorNegLocal {
    void agregar(TrabajadorSalud trabajador);

    List<TrabajadorSalud> listar();

    TrabajadorSalud buscarPorCI(Integer CI);
}