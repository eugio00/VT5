<%-- 
    Document   : main
    Created on : 10.01.2016, 22:25:50
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Page</title>
    </head>
    <body>
        <%@include file="//header.jsp" %>
        <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
            <input type="hidden" name="command" value="unresulted_races">
            <button type="submit"><fmt:message key="message.watch.unresulted.races"/></button>
        </form>
        <%@include file="//footer.jsp" %>
    </body>
</html>
