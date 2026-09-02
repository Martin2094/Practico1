<%@ page import="entidadMG.TrabajadorSalud" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Buscar Trabajador de la Salud</title>
</head>
<body>
    <h1>Buscar Trabajador de la Salud</h1>
    <form action="buscarTrabajadorSalud" method="post">
        <label>CI:</label>
        <input type="number"
               name="CI"
               required>
        <button type="submit">
            Buscar
        </button>
    </form>
    <%
        TrabajadorSalud trabajador = (TrabajadorSalud) request.getAttribute("trabajador");
    %>
    <% if (trabajador != null) { %>
        <h2>Trabajador encontrado</h2>
        <p>
            CI: <%= trabajador.getCI() %>
        </p>
        <p>
            Nombre: <%= trabajador.getNombre() %>
        </p>
        <p>
            Fecha de nacimiento:<%= trabajador.getNacimiento() %>
        </p>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <p>
            <%= request.getAttribute("error") %>
        </p>
    <% } %>
</body>
</html>