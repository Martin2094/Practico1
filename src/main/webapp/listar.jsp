<%@ page import="java.util.List" %>
<%@ page import="entidadMG.Persona" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>
	<meta charset="UTF-8">
	<title>Lista de Personas</title>
</head>

<body>
	<h1>Lista de Personas</h1>
    <%
        List<Persona> personas = (List<Persona>) request.getAttribute("personas");
    %>

    <% if (personas == null || personas.isEmpty()) { 
	    %>
	        <p>No hay personas registradas.</p>
	    <%
    } else { %>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Fecha de nacimiento</th>
            </tr>
            <% for (Persona persona : personas) { %>
                <tr>
                    <td><%= persona.getCI() %></td>
                    <td><%= persona.getNombre() %></td>
                    <td><%= persona.getNacimiento() %></td>
                </tr>
            <% } %>
        </table>
    <% } %>
</body>

</html>