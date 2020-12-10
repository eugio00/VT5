<%-- 
    Document   : header
    Created on : 11.01.2016, 1:07:19
    Author     : Koroid Daniil
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.epam.horseraceapp.properties.language" />
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <table style="width:100%">
            <tr>
                <c:choose>
                    <c:when test="${not empty user}">
                        <!-- Greets user -->
                        <td align="left">
                            <h3 style="text-align: left;"><fmt:message key="message.hello"/>, ${user.firstName}</h3>
                        </td>
                        <!-- Link to user's home page -->
                        <td align="center">
                            <a href="${pageContext.request.contextPath}/app/main.jsp"><fmt:message key="message.user.home.page" /></a>
                        </td>
                        <!-- Logout -->
                        <td align="center">
                            <form action="${pageContext.request.contextPath}/HorseraceAppController" method="post">
                                <input type="hidden" name="command" value="logout">
                                <button type="submit"><fmt:message key="message.log.out"/></button>
                            </form>
                        </td>
                        <!-- Balance -->
                        <td align="center">
                            <h3><fmt:message key="message.your.balance"/>: ${user.balance}</h3>
                        </td>
                        <td align="center">
                            <h3><a href="${pageContext.request.contextPath}/app/recharge.jsp"><fmt:message key="link.recharge"/></a></h3>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <!-- Log in -->
                        <td align="center">
                            <a href="login.jsp"><fmt:message key="message.log.in" /></a>
                        </td>
                        <!-- Register -->
                        <td align="center">
                            <a href="register.jsp"><fmt:message key="message.register" /></a>
                        </td>
                    </c:otherwise>
                </c:choose>
                <!-- Link to ste home page -->
                <td align="center">
                    <a href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="message.site.home.page" /></a>
                </td>
                <!-- English US language -->
                <td>
                    <form method="post" >
                        <input type="hidden" name="from" value="${pageContext.request.requestURI}">
                        <input type="hidden" name="language" value="en_US">
                        <c:if test="${not empty req}">
                            <input type="hidden" name="request" value="${req}">
                        </c:if>
                        <c:if test="${not empty com}">
                            <input type="hidden" name="command" value="${com}">
                        </c:if>
                        <button type="submit"><fmt:message key="language.english.US"/></button>
                    </form>
                </td>
                <!-- Russian language -->
                <td>
                    <form method="post" >
                        <input type="hidden" name="from" value="${pageContext.request.requestURI}">
                        <input type="hidden" name="language" value="ru_RU">
                        <c:if test="${not empty req}">
                            <input type="hidden" name="request" value="${req}">
                        </c:if>
                        <c:if test="${not empty com}">
                            <input type="hidden" name="command" value="${com}">
                        </c:if>
                        <button type="submit"><fmt:message key="language.russian.ru"/></button>
                    </form>
                </td>
                <c:if test="${not empty language}">
                    <!-- Language -->
                    <td align="right">     
                        <c:out value="${language}" />
                    </td>
                </c:if>
            </tr>
        </table>
    </body>
</html>
