<%-- 
    Document   : login
    Created on : 11.01.2016, 1:16:57
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        
        
        <c:if test="${not empty error}">
            <h3 style="text-align: center; color:red;"><fmt:message key="${error}"/></h3>
	</c:if>
        
        <form action="HorseraceAppController" method="post">
            <input type="hidden" name="command" value="login">
            <h3><fmt:message key="message.your.email"/></h3>
            <input type="text" name="email">
            <br>
            <h3><fmt:message key="message.your.password"/></h3>
            <input type="password" name="password">
            <br>
            <br>
            <input type="hidden" name="request" value="login">
            <button type="submit"><fmt:message key="message.log.in"/></button>
        </form>
    <%@include file="footer.jsp" %>
    </body>
</html>
