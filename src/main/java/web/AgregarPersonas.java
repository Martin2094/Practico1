package web;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import entidadMG.Persona;
import negocio.PersonaNegocioLocal;

@WebServlet("/agregarPersona")
public class AgregarPersonas extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PersonaNegocioLocal personaNegocio;
	
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String ciTexto = request.getParameter("CI");
		String nombre = request.getParameter("Nombre");
		String fechaTexto = request.getParameter("FechaNacimiento");
		
		Integer CI = Integer.valueOf(ciTexto);
		LocalDate fechaNacimiento = LocalDate.parse(fechaTexto);
		
		Persona persona = new Persona();
		
		persona.setCI(CI);
		persona.setNombre(nombre);
		persona.setNacimiento(fechaNacimiento);
		
		try {
			personaNegocio.agregar(persona);
			request.setAttribute("mensaje", "Persona agregada");
		} 
		catch (IllegalArgumentException e) {
			request.setAttribute("error", e.getMessage());
		}
		request.getRequestDispatcher("/agregar.jsp").forward(request, response);
	}
}
