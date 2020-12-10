<%-- 
    Document   : betMakingInfo
    Created on : 11.01.2016, 1:20:44
    Author     : Koroid Daniil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Information about bet making</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div align="center">
            <br>
            <fmt:message key="message.briefly" />: <fmt:message key="message.you.make.bet" />
            <br>
            <img src="${pageContext.request.contextPath}/resources/And_its_gone.jpg" alt="and_its_gone"/>
        </div>
        
        <%@include file="footer.jsp" %>
    </body>
</html>
