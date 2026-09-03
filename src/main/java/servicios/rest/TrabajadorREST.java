package servicios.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import dto.TrabajadorDTO;
import entidadMG.TrabajadorSalud;
import negocio.TrabajadorNegLocal;

@Stateless
@Path("/trabajadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrabajadorREST {
    @EJB
    private TrabajadorNegLocal trabajadorNegocio;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @POST
    public Response agregarTrabajador(TrabajadorDTO dto) {
        try {
            TrabajadorSalud trabajador = new TrabajadorSalud();
            trabajador.setCI(dto.getCi());
            trabajador.setNombre(dto.getNombre());
            trabajador.setNacimiento(LocalDate.parse(dto.getNacimiento(), FORMATO_FECHA));

            trabajadorNegocio.agregar(trabajador);
            return Response.status(Response.Status.CREATED).entity("Trabajador agregado correctamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    public List<TrabajadorDTO> listarTrabajadores() {
        List<TrabajadorSalud> trabajadores = trabajadorNegocio.listar();
        List<TrabajadorDTO> resultado = new ArrayList<>();
        for (TrabajadorSalud trabajador : trabajadores) {
            resultado.add(convertirDTO(trabajador));
        }
        return resultado;
    }

    @GET
    @Path("/{ci}")
    public Response buscarTrabajador(@PathParam("ci") Integer ci) {
        TrabajadorSalud trabajador = trabajadorNegocio.buscarPorCI(ci);
        if (trabajador == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Trabajador no encontrado").build();
        }
        return Response.ok(convertirDTO(trabajador)).build();
    }

    private TrabajadorDTO convertirDTO(TrabajadorSalud trabajador) {
        return new TrabajadorDTO(trabajador.getCI(), trabajador.getNombre(), trabajador.getNacimiento().format(FORMATO_FECHA));
    }
}