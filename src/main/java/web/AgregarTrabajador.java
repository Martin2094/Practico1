package web;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import entidadMG.TrabajadorSalud;
import negocio.TrabajadorNegLocal;

@WebServlet("/agregarTrabajador")
public class AgregarTrabajador extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private TrabajadorNegLocal trabajadorNegocio;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ciTexto = request.getParameter("CI");
        String nombre = request.getParameter("Nombre");
        String fechaTexto = request.getParameter("FechaNacimiento");
        Integer CI = Integer.valueOf(ciTexto);
        LocalDate fechaNacimiento = LocalDate.parse(fechaTexto);
        TrabajadorSalud trabajador = new TrabajadorSalud();
        trabajador.setCI(CI);
        trabajador.setNombre(nombre);
        trabajador.setNacimiento(fechaNacimiento);
        try {
            trabajadorNegocio.agregar(trabajador);
            request.setAttribute("mensaje", "Trabajador de la salud agregado");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/agregar.jsp").forward(request, response);
    }
}