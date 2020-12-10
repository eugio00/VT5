<%-- 
    Document   : unresultedRaces
    Created on : 11.01.2016, 1:48:04
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Unresulted races</title>
    </head>
    <body>
        <%@include file="//header.jsp" %>
        
        <c:if test="${not empty error}">
            <h3 style="text-align: center; color:red;"><fmt:message key="${error}"/></h3>
	</c:if>
        
        <c:choose>
            <c:when test="${not empty unresulted_races}">
                <table width="100%">
                    <tr>
                        <td><fmt:message key="table.header.race.time" /></td>
                        <td><fmt:message key="table.header.race.place" /></td>
                        <td><fmt:message key="table.header.race.distance" /></td>
                    </tr>
                    <c:forEach items="${unresulted_races}" var="race">
                        <tr>
                            <td>${race.startTime}</td>
                            <td>${race.place}</td>
                            <td>${race.distance}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
                                    <input type="hidden" name="command" value="create_result">
                                    <button type="submit" name="race_id" value="${race.id}"><fmt:message key="message.create.result" /></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <fmt:message key="message.no.unresulted.races" />
            </c:otherwise>
        </c:choose>

        <%@include file="//footer.jsp" %>
    </body>
</html>
