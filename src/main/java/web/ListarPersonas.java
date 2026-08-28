package web;

import java.io.IOException;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import entidadMG.Persona;
import negocio.PersonaNegocioLocal;

@WebServlet("/listarPersonas")
public class ListarPersonas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
    private PersonaNegocioLocal personaNegocio;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Persona> personas = personaNegocio.listar();
        request.setAttribute("personas", personas);
        request.getRequestDispatcher("/listar.jsp").forward(request, response);
    }
}
