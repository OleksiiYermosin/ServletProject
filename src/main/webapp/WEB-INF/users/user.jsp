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
    <title>
        <fmt:message key="title.user.page"/>
    </title>
    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../../common/header.jsp" />
<div class="content-holder">
    <jsp:include page="sidebar.jsp" />
    <div style="width: available" class="content">
        <div class="container">
            <div class="content-container">
                <p>
                    <fmt:message key="welcome.value"/>,
                     <span style="font-weight: bold">
                        ${sessionScope.user.name} ${sessionScope.user.surname}
                    </span>
                </p>
                <p style="margin-bottom: 0">
                    <fmt:message key="balance.value"/>:
                     <span style="font-weight: bold">
                         ${sessionScope.user.balance}
                    </span>
                </p>
                <a href="${pageContext.request.contextPath}/service/user/recharge-balance" style="font-size: 20px">
                    <fmt:message key="recharge.balance.value"/>
                </a>
                <p>
                    <fmt:message key="discount.value"/>:
                     <span style="font-weight: bold">
                        ${sessionScope.user.discount}%
                    </span>
                </p>
                <a href="${pageContext.request.contextPath}/service/user/order-taxi">
                    <fmt:message key="order.taxi.value"/>
                </a>
            </div>
        </div>
    </div>
</div>
<footer style="min-height: 70px" class="p-3 bg-dark text-white"></footer>
</body>
</html>