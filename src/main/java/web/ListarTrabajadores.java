package web;

import java.io.IOException;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import entidadMG.TrabajadorSalud;
import negocio.TrabajadorNegLocal;

@WebServlet("/listarTrabajadores")
public class ListarTrabajadores extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private TrabajadorNegLocal trabajadorNegocio;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TrabajadorSalud> trabajadores = trabajadorNegocio.listar();
        request.setAttribute("trabajadores", trabajadores);
        request.getRequestDispatcher("/listar.jsp").forward(request, response);
    }
}