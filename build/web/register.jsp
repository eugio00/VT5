<%-- 
    Document   : register
    Created on : 11.01.2016, 1:18:58
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Registration</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        
        <c:if test="${not empty error}">
            <h3 style="text-align: center; color: red;"><fmt:message key="${error}"/></h3>
        </c:if>
            
            <form action="./HorseraceAppController" method="post">
                <input type="hidden" name="command" value="register">
                <h3><fmt:message key="register.enter.your.email"/></h3>
                <input type="text" name="email">
                <br>
                <h3><fmt:message key="register.enter.your.first.name"/></h3>
                <input type="text" name="first_name">
                <br>
                <h3><fmt:message key="register.enter.your.last.name"/></h3>
                <input type="text" name="last_name">
                <br>
                <h3><fmt:message key="register.enter.your.password"/></h3>
                <input type="password" name="password">
                <br>
                <h3><fmt:message key="register.repeat.your.password"/></h3>
                <input type="password" name="password_repeat">
                <br>
                <button type="submit"><fmt:message key="message.sign.up"/></button>
            </form>
        <%@include file="footer.jsp" %>
    </body>
</html>
