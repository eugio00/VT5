<%-- 
    Document   : main
    Created on : 10.01.2016, 22:25:31
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main page</title>
    </head>
    <body>
        <%@ include file="/header.jsp" %>

        <table>
            <tr>
                <td valign="top" align="center">
                    <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
                        <input type="hidden" name="command" value="get_races">
                        <button type="submit"><fmt:message key="message.watch.all.races"/></button>
                    </form>
                </td>
            </tr>
            <tr>
                <td valign="top" align="center">
                    <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
                        <input type="hidden" name="command" value="get_user_bets">
                        <button type="submit"><fmt:message key="message.watch.my.bets"/></button>
                    </form>
                </td>
            </tr>
            <c:choose>
                <c:when test="${user.type eq 'ADMIN'}">
                    <tr>
                        <td valign="top" align="center">
                            <a href="${pageContext.request.contextPath}/app/admin/main.jsp"><fmt:message key="link.admin.panel"/></a>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${user.type eq 'BOOKMAKER'}">
                    <tr>
                        <td valign="top" align="center">
                            <a href="${pageContext.request.contextPath}/app/bookmaker/main.jsp"><fmt:message key="link.bookmaker.panel"/></a>
                        </td>
                    </tr>
                </c:when>
            </c:choose>
        </table>

        <%@include file="/footer.jsp" %>
    </body>
</html>
