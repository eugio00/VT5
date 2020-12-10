<%-- 
    Document   : races
    Created on : 11.01.2016, 1:22:06
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Races</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>

        <br>
        
        <table width="100%">
            <tr>
                <td><fmt:message key="table.header.race.place" /></td>
                <td><fmt:message key="table.header.race.time" /></td>
                <td><fmt:message key="table.header.race.distance" /></td>
                <c:if test="${not empty user && user.balance gt 0}">
                    <td><fmt:message key="table.header.view.horses.in.race" /></td>
                </c:if>
            </tr>
            <c:forEach items="${races}" var="race">
                <tr>
                    <td>${race.place}</td>
                    <td><fmt:formatDate value="${race.startTime}" type="both" dateStyle="short" timeStyle="short"/></td>
                    <td>${race.distance}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/HorseraceAppController" method="POST">
                            <input type="hidden" name="command" value="race_info">
                            <button type="submit" name="race_id" value="${race.id}"><fmt:message key="message.view.race.info" /></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    <%@include file="footer.jsp" %>
    </body>
</html>
