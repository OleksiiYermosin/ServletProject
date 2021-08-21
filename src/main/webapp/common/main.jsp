<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="res"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Main page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp" />
<div class="content">
    <!--<div sec:authorize="hasRole('USER')">You are user</div>
    <div sec:authorize="hasRole('ADMIN')">You are admin</div>-->
    <div class="container">
        <div class="content-container">
            <img style="width: 800px" src="${pageContext.request.contextPath}/static/img/mainpicture.jpg" alt="Taxi picture">
            <hr style="width: 800px; margin-bottom: 0.6rem">
            <p style="width: 800px; font-size: 18px; margin-bottom: 0; line-height: 120%; display: flex">
                <fmt:message key="welcome.message" />
            </p>
        </div>
    </div>
</div>
<footer style="min-height: 70px" class="p-3 bg-dark text-white"></footer>
</body>
</html>