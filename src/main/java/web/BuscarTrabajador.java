package web;

import java.io.IOException;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import entidadMG.TrabajadorSalud;
import negocio.TrabajadorNegLocal;

@WebServlet("/buscarTrabajador")
public class BuscarTrabajador extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private TrabajadorNegLocal trabajadorNegocio;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ciTexto = request.getParameter("CI");
        Integer CI = Integer.valueOf(ciTexto);
        TrabajadorSalud trabajador = trabajadorNegocio.buscarPorCI(CI);
        if (trabajador != null) {
            request.setAttribute("trabajador", trabajador);
        } else {
            request.setAttribute("error", "No existe un trabajador con esa CI");
        }
        request.getRequestDispatcher("/buscar.jsp").forward(request, response);
    }
}