<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="res"/>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp" />
<div class="content">
    <form method="post" action="${pageContext.request.contextPath}/service/login" class="dataForm">
        <c:if test = "${requestScope.loginError != null && requestScope.loginError == true}">
            <p  class="form-element" style="margin-top: 10%"><fmt:message key="login.wrong.values" /></p>
        </c:if>
        <label class="form-element" style="margin-top: 10%" for="username"><fmt:message key="username.prompt.value" /></label>
        <input name="username" class="form-element" type="text" id="username" required>
        <label class="form-element" for="password"><fmt:message key="password.prompt.value" /></label>
        <input name="password" class="form-element" type="password" id="password" required>
        <button style="margin-bottom: 10%" type="submit" class="btn btn-success"><fmt:message key="header.login.value" /></button>
    </form>
</div>
<footer style="min-height: 70px" class="p-3 bg-dark text-white"></footer>
</body>
</html>