package servicios.soap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import dto.TrabajadorDTO;
import entidadMG.TrabajadorSalud;
import negocio.TrabajadorNegLocal;

@Stateless
@WebService
public class TrabajadorSOAP {
    @EJB
    private TrabajadorNegLocal trabajadorNegocio;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @WebMethod
    public String agregarTrabajador(TrabajadorDTO dto) {
        TrabajadorSalud trabajador = new TrabajadorSalud();
        trabajador.setCI(dto.getCi());
        trabajador.setNombre(dto.getNombre());
        trabajador.setNacimiento(LocalDate.parse(dto.getNacimiento(), FORMATO_FECHA));
        trabajadorNegocio.agregar(trabajador);
        return "Trabajador agregado correctamente";
    }

    @WebMethod
    public TrabajadorDTO buscarTrabajador(Integer ci) {
        TrabajadorSalud trabajador = trabajadorNegocio.buscarPorCI(ci);
        if (trabajador == null) {
            return null;
        }
        return convertirDTO(trabajador);
    }

    @WebMethod
    public List<TrabajadorDTO> listarTrabajadores() {
        List<TrabajadorSalud> trabajadores = trabajadorNegocio.listar();
        List<TrabajadorDTO> resultado = new ArrayList<>();
        for (TrabajadorSalud trabajador : trabajadores) {
            resultado.add(convertirDTO(trabajador));
        }
        return resultado;
    }

    private TrabajadorDTO convertirDTO(TrabajadorSalud trabajador) {
        return new TrabajadorDTO(trabajador.getCI(), trabajador.getNombre(), trabajador.getNacimiento().format(FORMATO_FECHA));
    }
}