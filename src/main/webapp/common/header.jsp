<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="res"/>

<header class="p-3 bg-dark text-white">
    <div class="container">
        <div style="display: flex; justify-content: space-between">
            <a href="${pageContext.request.contextPath}/service/index" class="d-flex align-items-center mb-lg-0 text-white text-decoration-none">
                <p style="margin: 0; font-size: 24px; color: #ffc107">
                    <fmt:message key="header.service.name" />
                </p>
            </a>
            <div style="display: flex" class="text-end">
                <c:if test = "${sessionScope.user == null}">
                    <a href="${pageContext.request.contextPath}/service/login" class="btn btn-warning me-2"><fmt:message key="header.login.value" /></a>
                    <a href="${pageContext.request.contextPath}/service/registration" class="btn btn-warning me-4"><fmt:message key="header.signUp.value" /></a>
                </c:if>
                <c:if test = "${sessionScope.user != null}">
                    <a href="/service/logout" class="btn btn-warning me-4"><fmt:message key="header.logout.value" /></a>
                </c:if>
                <div>
                    <a class="btn btn-warning me-2" href="?locale=en">ENG</a>
                    <a class="btn btn-warning" href="?locale=ua">UA</a>
                </div>
            </div>
        </div>
    </div>
</header>