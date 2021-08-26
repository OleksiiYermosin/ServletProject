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
        <fmt:message key="title.order.details"/>
    </title>
    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="${pageContext.request.contextPath}/static/js/scripts.js"></script>
</head>
<body>
<jsp:include page="../../common/header.jsp"/>
<div class="content-holder">
    <jsp:include page="sidebar.jsp"/>
    <div style="width: available" class="content">
        <div class="container">
            <div class="content-container">
                <div class="main">
                    <c:if test="${sessionScope.orders==null&&requestScope.orderDeprecated==null}">
                        <div style="display: flex; align-items: center; justify-content: center">
                            <p style="font-size: 18px">
                                <fmt:message key="session.error.message"/>
                            </p>
                        </div>
                    </c:if>
                    <c:if test="${requestScope.orderDeprecated!=null && requestScope.orderDeprecated}">
                        <div style="display: flex; align-items: center; justify-content: center">
                            <p style="font-size: 18px">
                                <fmt:message key="order.error.message"/>
                            </p>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.orders!=null && sessionScope.orders.size()==0}">
                        <div style="display: flex; align-items: center; justify-content: center">
                            <p style="font-size: 18px">
                                <fmt:message key="order.not.found.message"/>
                            </p>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.orders!=null && sessionScope.orders.size()!=0}">
                        <div style="display: flex; align-items: center; justify-content: center">
                            <p style="font-size: 18px">
                                <fmt:message key="possible.orders.message"/>
                            </p>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.orders!=null && sessionScope.orders.size()!=0}">
                        <table class="table">
                            <thead class="thead-dark">
                            <tr>
                                <th scope="col">
                                    <fmt:message key="start.address.prompt"/>
                                </th>
                                <th scope="col">
                                    <fmt:message key="finish.address.prompt"/>
                                </th>
                                <th scope="col">
                                    <fmt:message key="human.amount.prompt"/>
                                </th>
                                <th scope="col">
                                    <fmt:message key="order.total.message"/>
                                </th>
                                <th scope="col">
                                    <fmt:message key="order.action.message"/>
                                </th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach var="order" items="${sessionScope.orders}" varStatus="loop">
                                <tr>
                                    <td>
                                            ${order.addressFrom}
                                    </td>
                                    <td>
                                            ${order.addressTo}
                                    </td>
                                    <td>
                                            ${order.peopleAmount}
                                    </td>
                                    <td>
                                            ${order.total}
                                    </td>
                                    <td style="display: flex; justify-content: space-around">
                                        <form method="post" style="display: flex; margin: 0"
                                              action="${pageContext.request.contextPath}/service/user/order-taxi/new-order">
                                            <input type="hidden" value="${loop.index}" name="index">
                                            <button type="submit" class="btn btn-success">✓</button>
                                        </form>
                                        <button type="button" onclick="myFunction('details${loop.index}')"
                                                class="btn btn-info">
                                            <span style="font-style: italic; font-weight: bold">i</span>
                                        </button>
                                        <form method="post" style="display: flex; margin: 0"
                                              action="${pageContext.request.contextPath}/service/user/order-taxi/cancel-order">
                                            <input type="hidden" value="${loop.index}" name="index">
                                            <button type="submit" class="btn btn-danger">×</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${sessionScope.orders!=null && sessionScope.orders.size()!=0}">
                        <c:forEach var="order" items="${sessionScope.orders}" varStatus="loop">
                            <div class="con" id="details${loop.index}">
                                <c:forEach var="taxi" items="${order.taxi}" varStatus="loop">
                                    <div>
                                        <p>
                                            <fmt:message key="taxi.info.message"/>:
                                                ${taxi.info}.
                                            <fmt:message key="driver.name.value"/>:
                                                ${taxi.driver.surname} ${taxi.driver.name.charAt(0)}.;
                                            <fmt:message key="driver.phone.value"/>:
                                                ${taxi.driver.phone}
                                        </p>
                                        <c:if test="${taxi.taxiClass.name.equals('ECONOMY')}">
                                            <p style="font-size: 18px">
                                                <fmt:message key="taxi.class.value"/>:
                                                <fmt:message key="taxi.class.economy.value"/>.
                                                <fmt:message key="order.taxi.capacity"/>:
                                                    ${taxi.capacity}.
                                            </p>
                                        </c:if>
                                        <c:if test="${taxi.taxiClass.name.equals('COMFORT')}">
                                            <p style="font-size: 18px">
                                                <fmt:message key="taxi.class.value"/>:
                                                <fmt:message key="taxi.class.comfort.value"/>.
                                                <fmt:message key="order.taxi.capacity"/>:
                                                    ${taxi.capacity}.
                                            </p>
                                        </c:if>
                                        <c:if test="${taxi.taxiClass.name.equals('BUSINESS')}">
                                            <p style="font-size: 18px">
                                                <fmt:message key="taxi.class.value"/>:
                                                <fmt:message key="taxi.class.business.value"/>.
                                                <fmt:message key="order.taxi.capacity"/>:
                                                    ${taxi.capacity}.
                                            </p>
                                        </c:if>
                                    </div>
                                </c:forEach>

                                <p style="margin-top: 0">
                                    <fmt:message key="distance.value"/>: ${order.distance}
                                </p>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<footer style="min-height: 70px" class="p-3 bg-dark text-white"></footer>
</body>
</html>