<%@ page import="entidadMG.Persona" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Buscar Persona</title>
</head>

<body>
	<h1>Buscar Persona</h1>
	    <form action="buscarPersona" method="post">
	        <label>ID:</label>
	        <input type="number" name="CI" required>
	        <button type="submit"> Buscar </button>
	    </form>
	
	    <%
	        Persona persona = (Persona) request.getAttribute("persona");
	    %>
	
	    <% if (request.getAttribute("persona") != null) { %>
	        <h2>Persona encontrada</h2>
	        <p> ID: <%= persona.getCI() %> </p>
	        <p> Nombre: <%= persona.getNombre() %> </p>
	        <p> Fecha de nacimiento: <%= persona.getNacimiento() %> </p>
	    <% } %>
	    
	    <% if (request.getAttribute("error") != null) { %>
		    <p> <%= request.getAttribute("error") %> </p>
		<% } %>
</body>
</html>