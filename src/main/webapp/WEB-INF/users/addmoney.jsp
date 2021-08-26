<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="res"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>
        <fmt:message key="recharge.balance.value"/>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../../common/header.jsp"/>
<div class="content-holder">
    <jsp:include page="sidebar.jsp"/>
    <div style="width: available" class="content">
        <div class="container" style="display: flex; justify-content: center">
            <form method="post" action="${pageContext.request.contextPath}/service/user/recharge-balance"
                  class="dataForm">
                <c:if test="${true}">
                    <label class="form-element" style="margin-top: 10%" for="value">
                        <fmt:message key="money.prompt.value"/>
                    </label>
                </c:if>
                <input name="value" class="form-element" type="text" id="value" required>
                <c:if test="${requestScope.validationError!=null && requestScope.validationError==true}">
                    <p>
                        <fmt:message key="recharge.balance.error.message"/>
                    </p>
                </c:if>
                <button style="margin-bottom: 10%" type="submit" class="btn btn-success">
                    <fmt:message key="recharge.balance.value"/>
                </button>
            </form>
        </div>
    </div>
</div>
<footer style="min-height: 70px" class="p-3 bg-dark text-white"></footer>
</body>
</html>