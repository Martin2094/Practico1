package datos;

import java.util.List;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import entidadMG.TrabajadorSalud;

@Singleton
public class TrabajadorBean implements TrabajadorLocal, TrabajadorRemoto {
    @PersistenceContext(unitName = "Practico1PU")
    private EntityManager em;

    @Override
    public void agregar(TrabajadorSalud trabajador) {
        em.persist(trabajador);
    }

    @Override
    public List<TrabajadorSalud> listar() {
        return em.createQuery("SELECT t FROM TrabajadorSalud t", TrabajadorSalud.class).getResultList();
    }

    @Override
    public TrabajadorSalud buscarPorCI(Integer CI) {
        return em.find(TrabajadorSalud.class, CI);
    }
}