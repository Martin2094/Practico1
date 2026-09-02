package datos;

import jakarta.ejb.Local;
import java.util.List;

import entidadMG.TrabajadorSalud;

@Local
public interface TrabajadorLocal {
    void agregar(TrabajadorSalud trabajador);

    List<TrabajadorSalud> listar();

    TrabajadorSalud buscarPorCI(Integer CI);
}