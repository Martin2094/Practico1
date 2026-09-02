package datos;

import jakarta.ejb.Remote;
import java.util.List;

import entidadMG.TrabajadorSalud;

@Remote
public interface TrabajadorRemoto {
    void agregar(TrabajadorSalud trabajador);

    List<TrabajadorSalud> listar();

    TrabajadorSalud buscarPorCI(Integer CI);
}