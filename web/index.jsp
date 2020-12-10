<%-- 
    Document   : index
    Created on : 11.01.2016, 1:14:49
    Author     : Koroid Daniil
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Horserace bets</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        
        <br>
        
        <table width="100%" cellpadding="5">
            <tr>
                <td align="center"><fmt:message key="message.index.welcome" /></td>
            </tr>
            <c:if test="${empty user}">
                <tr>
                    <td align="center">
                        <fmt:message key="message.index.registration.profits" />
                        <fmt:message key="message.index.bonus.money" />
                    </td>
                </tr>
            </c:if>
            <tr>
                <td valign="top" align="center">
                    <form action="HorseraceAppController" method="post">
                        <input type="hidden" name="command" value="get_races">
                        <button type="submit"><fmt:message key="message.watch.all.races"/></button>
                    </form>
                </td>
            </tr>
        </table> 
    <%@include file="footer.jsp" %>    
    </body>
</html>
