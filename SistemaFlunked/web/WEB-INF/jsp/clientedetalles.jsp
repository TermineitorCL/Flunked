<%-- 
    Document   : clientedetalles
    Created on : 24-11-2018, 18:30:07
    Author     : Matia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show Clientes</title>
</head>
<body>
    id: <c:out value="${cliente.id}"/>
    nombre: <c:out value="${cliente.nombre}"/>
    apellido: <c:out value="${cliente.apellido}"/>
    rut: <c:out value="${cliente.rut}"/>
    email: <c:out value="${cliente.email}"/>
    telefono: <c:out value="${cliente.telefono}"/>

</body>
</html>
