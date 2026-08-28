package web;

import java.io.IOException;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import entidadMG.Persona;
import negocio.PersonaNegocioLocal;

@WebServlet("/buscarPersona")
public class BuscarPersonaID extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @EJB
    private PersonaNegocioLocal personaNegocio;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ciTexto = request.getParameter("CI");
        Integer CI = Integer.valueOf(ciTexto);
        Persona persona = personaNegocio.buscarPorCI(CI);
        if (persona != null) {
            request.setAttribute("persona", persona);
        } else {
            request.setAttribute("error", "No existe una persona con esa CI");
        }
        request.getRequestDispatcher("/buscar.jsp").forward(request, response);
    }
}
