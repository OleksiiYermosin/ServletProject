<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="res"/>

<div class="side">
    <div class="container">
        <div class="side-content">
            <p style="font-size: 20px; font-weight: bold; margin-bottom: 0">
                ${sessionScope.user.name} ${sessionScope.user.surname}
            </p>
            <a href="${pageContext.request.contextPath}/service/logout">
                <fmt:message key="change.account.value"/>
            </a>
            <hr>
            <a href="${pageContext.request.contextPath}/service/user">
                <fmt:message key="personal.account.value"/>
            </a>
            <a href="${pageContext.request.contextPath}/service/user/order-taxi">
                <fmt:message key="order.taxi.value"/>
            </a>
            <a href="${pageContext.request.contextPath}/service/user/order-taxi/view-new-order">
                <fmt:message key="prepared.orders.value"/>
            </a>
            <a href=${pageContext.request.contextPath}/service/user/orders">
                <fmt:message key="view.orders.value"/>
            </a>
        </div>
    </div>
</div>