package negocio;

import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import datos.TrabajadorLocal;
import entidadMG.TrabajadorSalud;

@Stateless
public class TrabajadorNegBean implements TrabajadorNegLocal, TrabajadorNegRemota {
    @EJB
    private TrabajadorLocal trabajadorDAO;

    @Override
    public void agregar(TrabajadorSalud trabajador) {
        TrabajadorSalud existente = trabajadorDAO.buscarPorCI(trabajador.getCI());
        if (existente != null) {
            throw new IllegalArgumentException("Ya existe un trabajador con esa CI");
        }
        trabajadorDAO.agregar(trabajador);
    }

    @Override
    public List<TrabajadorSalud> listar() {
        return trabajadorDAO.listar();
    }

    @Override
    public TrabajadorSalud buscarPorCI(Integer CI) {
        return trabajadorDAO.buscarPorCI(CI);
    }
}