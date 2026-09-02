<%@ page import="java.util.List" %>
<%@ page import="entidadMG.TrabajadorSalud" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Lista de Trabajadores de la Salud</title>
</head>

<body>
    <h1>Lista de Trabajadores de la Salud</h1>
    <% List<TrabajadorSalud> trabajadores = (List<TrabajadorSalud>) request.getAttribute("trabajadores"); %>
    <% if (trabajadores == null || trabajadores.isEmpty()) { %>
        <p>No hay trabajadores de la salud registrados.</p>
    <% } else { %>
        <table border="1">
            <tr>
                <th>CI</th>
                <th>Nombre</th>
                <th>Fecha de nacimiento</th>
            </tr>
            <% for (TrabajadorSalud trabajador : trabajadores) { %>
                <tr>
                    <td><%= trabajador.getCI() %></td>
                    <td><%= trabajador.getNombre() %></td>
                    <td><%= trabajador.getNacimiento() %></td>
                </tr>
            <% } %>
        </table>
    <% } %>
</body>
</html>