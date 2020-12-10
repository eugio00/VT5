<%-- 
    Document   : footer
    Created on : 05.01.2016, 18:22:07
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>    </head>
    <body>
        <table width="100%">
            <tr>
                <td align="center">
                    <a href="${pageContext.request.contextPath}/betMakingInfo.jsp"><fmt:message key="message.bet.making.info" /></a>
                </td>
            </tr>
        </table>
    </body>
</html>
