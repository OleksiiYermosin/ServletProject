<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="res"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp" />
<div class="content">
        <form method="post" action="${pageContext.request.contextPath}/service/registration" class="dataForm">
            <c:if test = "${requestScope.isValidationFailed != null && requestScope.isValidationFailed}">
                <p style="margin-top: 10%" class="form-element"><fmt:message key="incorrect.registration.values.message" /></p>
            </c:if>
            <label style="margin-top: 10%" for="name"><fmt:message key="name.prompt.value" /></label>
            <input name="name" class="form-element" type="text" id="name" required>
            <label class="form-element" for="surname"><fmt:message key="surname.prompt.value" /></label>
            <input name="surname" class="form-element" type="text" id="surname" required>
            <label class="form-element" for="username"><fmt:message key="username.prompt.value" /></label>
            <input name="username" class="form-element" type="text" id="username" required>
            <label class="form-element" for="phone"><fmt:message key="phone.prompt.value" /></label>
            <input name="phone" class="form-element" type="text" id="phone" required>
            <label class="form-element" for="password"><fmt:message key="password.prompt.value" /></label>
            <input name="password" class="form-element" type="text" id="password" required>
            <button style="margin-bottom: 10%" type="submit" class="btn btn-success"><fmt:message key="register.value" /></button>
        </form>
</div>
<footer style="min-height: 70px" class="p-3 bg-dark text-white"></footer>
</body>
</html>