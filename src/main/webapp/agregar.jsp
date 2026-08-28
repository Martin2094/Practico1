<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Agregar Persona</title>
</head>

<body>
    <h1>Agregar Persona</h1>
    
    <form action="agregarPersona" method="post">
        <div>
            <label>ID:</label>
            <input type="number" name="CI" required>
        </div>
        <div>
            <label>Nombre:</label>
            <input type="text" name="Nombre" required>
        </div>
        <div>
            <label>Fecha de nacimiento:</label>
            <input type="date" name="FechaNacimiento" required>
        </div>
        <button type="submit">
            Agregar
        </button>
    </form>
    
    <% if (request.getAttribute("mensaje") != null) { %>
        <p>
            <%= request.getAttribute("mensaje") %>
        </p>
    <% } %>

    <% if (request.getAttribute("error") != null) { %>
        <p>
            <%= request.getAttribute("error") %>
        </p>
    <% } %>
</body>
</html>