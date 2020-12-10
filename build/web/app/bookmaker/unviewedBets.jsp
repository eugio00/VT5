<%-- 
    Document   : unviewedBets
    Created on : 11.01.2016, 1:41:19
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Unviewed bets</title>
    </head>
    <body>
        <%@include file="//header.jsp" %>
        <br>
        
        <c:if test="${not empty error}">
            <h3 style="text-align: center; color:red;"><fmt:message key="${error}"/></h3>
	</c:if>
        
        <br>    
            
        <c:if test="${not empty message}">
            <h3 style="text-align: center; color:red;"><fmt:message key="${message}"/></h3>
	</c:if>
            
        <c:choose>
            <c:when test="${not empty unviewed_bets}">
                <table width="100%">
                    <tr>
                        <td><fmt:message key="table.header.bet.state" /></td>
                        <td><fmt:message key="table.header.bet.owner.first.name" /></td>
                        <td><fmt:message key="table.header.bet.owner.last.name" /></td>
                        <td><fmt:message key="table.header.bet.owner.email" /></td>
                        <td><fmt:message key="table.header.bet.owner.balance" /></td>
                        <td><fmt:message key="table.header.bet.amount" /></td>
                        <td><fmt:message key="table.header.bet.coefficient" /></td>
                        <td><fmt:message key="table.header.bet.place.time" /></td>
                        <td><fmt:message key="table.header.race.place" /></td>
                        <td><fmt:message key="table.header.race.time" /></td>
                        <td><fmt:message key="table.header.horse.name" /></td>
                        <td><fmt:message key="table.header.horse.position" /></td>
                    </tr>
                    <c:forEach items="${unviewed_bets}" var="bet">
                        <tr>
                            <td><fmt:message key="bet.state.${bet.state.toBundleString()}" /></td>
                            <td>${bet.owner.firstName}</td>
                            <td>${bet.owner.lastName}</td>
                            <td>${bet.owner.email}</td>
                            <td>${bet.owner.balance}</td>
                            <td>${bet.amount}</td>
                            <td>${bet.coefficient}</td>
                            <td><fmt:formatDate value="${bet.betPlaceTime}" type="both" dateStyle="short" timeStyle="short"/></td>
                            <td>${bet.racePlace}</td>
                            <td><fmt:formatDate value="${bet.raceStartTime}" type="both" dateStyle="short" timeStyle="short"/></td>
                            <td>${bet.horseName}</td>
                            <td>${bet.horsePosition}</td>
                            <c:choose>
                                <c:when test="${bet.state eq 'WAITING_FOR_ACCEPT'}">
                                    <td>
                                        <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
                                            <input type="hidden" name="command" value="accept_bet">
                                            <input type="hidden" name="bet_id" value="${bet.id}">
                                            <button type="submit"><fmt:message key="message.accept.bet"/></button>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
                                            <input type="hidden" name="command" value="decline_bet">
                                            <input type="hidden" name="bet_id" value="${bet.id}">
                                            <button type="submit"><fmt:message key="message.decline.bet"/></button>
                                        </form>
                                    </td>
                                </c:when>
                                <c:when test="${bet.state eq 'ACCEPTED' and (not empty bet.horsePosition)}">
                                    <td>
                                        <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
                                            <input type="hidden" name="command" value="determine_bet_result">
                                            <input type="hidden" name="bet_id" value="${bet.id}">
                                            <input type="hidden" name="position" value="${bet.horsePosition}">
                                            <button type="submit"><fmt:message key="message.result.bet"/></button>
                                        </form>
                                    </td>
                                </c:when>
                                <c:when test="${bet.state eq 'WON_WAITING_FOR_PAY'}">
                                    <td>
                                        <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
                                            <input type="hidden" name="command" value="pay_bet">
                                            <input type="hidden" name="bet_id" value="${bet.id}">
                                            <button type="submit"><fmt:message key="message.pay.bet"/></button>
                                        </form>
                                    </td>
                                </c:when>
                            </c:choose>

                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <fmt:message key="message.no.unviewed.bets"/>
            </c:otherwise>
        </c:choose>

        <br>
        <%@include file="//footer.jsp" %>
    </body>
</html>

