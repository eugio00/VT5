<%-- 
    Document   : recharge
    Created on : 11.01.2016, 1:39:24
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recharge balance</title>
    </head>
    <body>
        <%@include file="/header.jsp" %>
        <fmt:message key="message.recharging.page.info"/>
        <br>
        <c:if test="${not empty error}">
            <h3 style="text-align: center; color:red;"><fmt:message key="${error}"/></h3>
        </c:if>

        <br>    

        <c:if test="${not empty message}">
            <h3 style="text-align: center; color:red;"><fmt:message key="${message}"/></h3>
        </c:if>

        <br>

        <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
            <input type="hidden" name="command" value="recharge">
            <button type="submit"><fmt:message key="message.recharge"/></button>
        </form>
        <%@include file="/footer.jsp" %>
    </body>
</html>
