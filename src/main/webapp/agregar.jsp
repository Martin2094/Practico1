<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Agregar Trabajador de la Salud</title>
</head>
<body>
    <h1>Agregar Trabajador de la Salud</h1>
    <form action="agregarTrabajadorSalud" method="post">
        <div>
            <label>CI:</label>
            <input type="number"
                   name="CI"
                   required>
        </div>
        <div>
            <label>Nombre:</label>
            <input type="text"
                   name="Nombre"
                   required>
        </div>
        <div>
            <label>Fecha de nacimiento:</label>
            <input type="date"
                   name="FechaNacimiento"
                   required>
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