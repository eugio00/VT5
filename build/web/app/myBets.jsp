<%-- 
    Document   : myBets
    Created on : 11.01.2016, 1:33:48
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Bets</title>
    </head>
    <body>
        <%@include file="/header.jsp" %>
        
        <c:choose>
            <c:when test="${not empty my_bets}">
                <div align="center">
                    <fmt:message key="message.my.bets"/>
                </div>
                <table width="100%">
                    <tr>
                        <td><fmt:message key="table.header.bet.state"/></td>
                        <td><fmt:message key="table.header.horse.name"/></td>
                        <td><fmt:message key="table.header.bet.coefficient"/></td>
                        <td><fmt:message key="table.header.bet.amount"/></td>
                        <td><fmt:message key="table.header.horse.position"/></td>
                        <td><fmt:message key="table.header.race.place"/></td>
                        <td><fmt:message key="table.header.race.time"/></td>
                        <td><fmt:message key="table.header.bet.place.time"/></td>
                    </tr>
                    <c:forEach items="${my_bets}" var="bet">
                        <tr>
                            <td><fmt:message key="bet.state.${bet.state.toBundleString()}"/></td>
                            <td>${bet.horseName}</td>
                            <td>${bet.coefficient}</td>
                            <td>${bet.amount}</td>
                            <td>${bet.horsePosition}</td>
                            <td>${bet.racePlace}</td>
                            <td><fmt:formatDate value="${bet.raceStartTime}" type="both" dateStyle="short" timeStyle="short"/></td>
                            <td><fmt:formatDate value="${bet.betPlaceTime}" type="both" dateStyle="short" timeStyle="short"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <h3 style="align: center"><fmt:message key="message.you.have.no.bets" /></h3>
            </c:otherwise>
        </c:choose>
        <%@include file="/footer.jsp" %>
    </body>
</html>
