package datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.ejb.Singleton;

import entidadMG.TrabajadorSalud;

@Singleton
public class TrabajadorBean implements TrabajadorLocal, TrabajadorRemoto {
    private List<TrabajadorSalud> trabajadores = new ArrayList<>();

    @Override
    public void agregar(TrabajadorSalud trabajador) {
        trabajadores.add(trabajador);
    }

    @Override
    public List<TrabajadorSalud> listar() {
        return new ArrayList<>(trabajadores);
    }

    @Override
    public TrabajadorSalud buscarPorCI(Integer CI) {
        for (TrabajadorSalud trabajador : trabajadores) {
            if (Objects.equals(trabajador.getCI(), CI)) {
                return trabajador;
            }
        }
        return null;
    }
}