<%-- 
    Document   : raceInfo
    Created on : 11.01.2016, 1:26:09
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Horses in race</title>
    </head>
    <body>
        <%@include file="header.jsp" %>

        <br>

        <c:if test="${not empty race_info}" >
            <table width="100%">
                <tr>
                    <td><fmt:message key="table.header.race.place"/></td>
                    <td><fmt:message key="table.header.race.time"/></td>
                    <td><fmt:message key="table.header.race.distance"/></td>
                </tr>
                <tr>
                    <td>${race_info.race.place}</td>
                    <td><fmt:formatDate value="${race_info.race.startTime}" type="both" timeStyle="short" dateStyle="short" /></td>
                    <td>${race_info.race.distance}</td>
                </tr>
            </table>

            <br>

            <c:if test="${not empty error}">
                <h3 style="text-align: center; color:red;"><fmt:message key="${error}"/></h3>
            </c:if>

            <br>

            <c:if test="${not empty user}">
                <c:forEach items="${race_info.horses}" var="horse" begin="0" end="0">
                    <c:if test="${empty horse.position}" >
                        <c:choose>
                            <c:when test="${user.balance gt 0}">
                                <fmt:message key="message.bet.cant.be.returned" />
                            </c:when>
                            <c:when test="${user.balance eq 0}">
                                <fmt:message key="message.cant.make.bets.with.empty.balance" />
                            </c:when>
                        </c:choose>
                    </c:if>
                    <br>
                </c:forEach>
            </c:if>

            <table width="100%">
                <tr>
                    <td><fmt:message key="table.header.horse.name" /></td>
                    <td><fmt:message key="table.header.horse.coefficient" /></td>
                    <td><fmt:message key="table.header.horse.result" /></td>
                    <c:if test="${not empty user && user.balance > 0}" >
                        <c:forEach items="${race_info.horses}" var="horse" begin="0" end="0">
                            <c:if test="${empty horse.position}">
                                <td><fmt:message key="table.header.bet.amount" /></td>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </tr>
                <c:forEach items="${race_info.horses}" var="horse" >
                    <tr>
                        <td>${horse.horseName}</td>
                        <td>${horse.coefficient}</td>
                        <c:choose>
                            <c:when test="${(not empty horse.position) and (horse.position ne 0)}">
                                <td>${horse.position}</td>
                            </c:when>
                            <c:otherwise>
                                <td><fmt:message key="table.message.awaiting.for.result" /></td>
                                <c:if test="${(not empty user) and (user.balance gt 0)}">
                                    <td>
                                        <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
                                            <input type="hidden" name="command" value="make_bet">
                                            <input type="number" name="amount" min="1">
                                            <button type="submit" name="contestant_horse_id" value="${horse.id}"><fmt:message key="button.text.make.bet"/></button>
                                        </form>
                                    </td>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <%@include file="footer.jsp" %>
    </body>
</html>
