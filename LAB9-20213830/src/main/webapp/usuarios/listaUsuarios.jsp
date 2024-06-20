<%@ page import="com.example.lab920213830.Beans.Usuario" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: josti
  Date: 19/06/2024
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //aqui va codigo java
    ArrayList<Usuario> lista = (ArrayList<Usuario>) request.getAttribute("Lista");

%>
<html>
<head>
    <title>Title</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Lista de usuarios</h1>
    <table>
        <tr>

            <th>Nombres</th>
            <th>Apellidos</th>
            <th>Tipo de licencia</th>
            <th>Estado de licencia</th>
            <th>Pais de procedencia</th>
            <th>Cantidad de reservas canceladas en el anio</th>

        </tr>
        <% for (Usuario usuario: lista){ %>
        <tr>

            <td> <%=usuario.getNombres()%></td>
            <td> <%=usuario.getApellidos()%></td>

            <td> <%=usuario.getLicencia().getCategoria()%></td>
            <td> <%=usuario.getLicencia().getPais()%></td>

        </tr>
        <% } %>

    </table>

</body>
</html>
