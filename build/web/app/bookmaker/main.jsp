<%-- 
    Document   : main
    Created on : 10.01.2016, 22:26:06
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bookmaker Panel</title>
    </head>
    <body>
        <%@include file="//header.jsp" %>
        <br>
        <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
            <input type="hidden" name="command" value="unviewed_bets">
            <button type="submit"><fmt:message key="message.watch.unviewed.bets"/></button>
        </form>
        <br>
        <%@include file="//footer.jsp" %>
    </body>
</html>
