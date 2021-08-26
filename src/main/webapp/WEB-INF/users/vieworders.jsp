<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="res"/>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>
        <fmt:message key="title.orders"/>
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
                    <c:if test="${sessionScope.orders==null}">
                        <div style="display: flex; align-items: center; justify-content: center">
                            <p style="font-size: 18px">
                                <fmt:message key="orders.error.message"/>
                            </p>
                        </div>
                    </c:if>
                    <form th:action="@{/user/orders}" method="get" class="sorting-form">
                        <label for="sort" th:text="#{sort.message}" class="sorting-form-element"></label>
                        <select name="sort" id="sort" class="sorting-form-element" required>
                            <option th:selected="${sort.equals('id')}"  value="id" th:text="#{sort.id.message}"></option>
                            <option th:selected="${sort.equals('date')}" value="date" th:text="#{sort.date.message}"></option>
                         </select>
                        <label for="sort-direction" th:text="#{sort.direction.message}" class="sorting-form-element"></label>
                        <select name="sortDirection" id="sort-direction" class="sorting-form-element" required>
                            <option th:selected="${sortDirection.equals('asc')}" value="asc" th:text="#{sort.direction.asc.message}"></option>
                            <option th:selected="${sortDirection.equals('desc')}" value="desc" th:text="#{sort.direction.desc.message}"></option>
                        </select>
                        <button type="submit" class="btn btn-success" th:text="#{sort.action}"></button>
                    </form>
                    <div th:if="${orders.isEmpty()==false}" style="display: flex; align-items: center; justify-content: center">
                        <p th:text="#{description.message}" style="font-size: 18px"></p>
                    </div>
                    <table th:if="${orders.isEmpty()==false}" class="table">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col" >#</th>
                            <th scope="col" th:text="#{start.address.prompt}"></th>
                            <th scope="col" th:text="#{finish.address.prompt}"></th>
                            <th scope="col" th:text="#{human.amount.prompt}"></th>
                            <th scope="col" th:text="#{order.total.message}"></th>
                            <th scope="col" th:text="#{order.date.prompt}"></th>
                            <th scope="col" th:text="#{order.action.message}"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr th:each="order, iter : ${orders}">
                            <td th:text="${order.getId()}"></td>
                            <td th:text="${order.getAddressFrom()}"></td>
                            <td th:text="${order.getAddressTo()}"></td>
                            <td th:text="${order.getPeopleAmount()}"></td>
                            <td th:text="${order.getTotal()}"></td>
                            <td th:text="${order.getDate()}"></td>
                            <td style="display: flex; justify-content: space-around">
                                <form method="post" th:if="${order.getOrderStatus().getName().equals('ACTIVE')}" th:action="@{/user/orders/finish}">
                                    <input type="hidden" th:value="${order.getId()}" name="id">
                                    <button type="submit" class="btn btn-success">✓</button>
                                </form>
                                <button type="button" th:if="${!order.getOrderStatus().getName().equals('CANCELED')}" th:attr="onclick=|myFunction('details${iter.index}')|" class="btn btn-info">
                                    <span style="font-style: italic; font-weight: bold">i</span>
                                </button>
                                <form method="post" th:if="${order.getOrderStatus().getName().equals('ACTIVE')}" th:action="@{/user/orders/cancel}">
                                    <input type="hidden" th:value="${order.getId()}" name="id">
                                    <button type="submit" class="btn btn-danger">×</button>
                                </form>
                                <p th:if="${order.getOrderStatus().getName().equals('CANCELED')}" th:text="#{order.canceled.value}" style="font-size: 18px"></p>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div th:if="${orders.isEmpty()==false}" th:each="order, iter : ${orders}" class="con" th:id="'details'+${iter.index}">
                        <div th:each="taxi : ${order.getTaxi()}">
                            <p th:text="#{taxi.info.message } + ': ' + ${taxi.getInfo()} + '. ' + #{driver.name.value} + ': ' + ${taxi.getDriver().getSurname() + ' ' + taxi.getDriver().getName().charAt(0)} + '.; ' + #{driver.phone.value} + ': ' + ${taxi.getDriver().getPhone()}"></p>
                            <p th:if="${taxi.getTaxiClass().getName().equals('ECONOMY')}" th:text="#{taxi.class.value} + ': ' + #{taxi.class.economy.value} + '. ' + #{order.taxi.capacity} + ': ' + ${taxi.getCapacity()}"></p>
                            <p th:if="${taxi.getTaxiClass().getName().equals('COMFORT')}" th:text="#{taxi.class.value} + ': ' + #{taxi.class.comfort.value} + '. ' + #{order.taxi.capacity} + ': ' + ${taxi.getCapacity()}"></p>
                            <p th:if="${taxi.getTaxiClass().getName().equals('BUSINESS')}" th:text="#{taxi.class.value} + ': ' + #{taxi.class.business.value} + '. ' + #{order.taxi.capacity} + ': ' + ${taxi.getCapacity()}"></p>
                        </div>
                        <p th:text="#{distance.value} + ': ' + ${order.getDistance()}" style="margin-top: 0"></p>
                    </div>
                    <div th:if="${orders.isEmpty()==false && (orders.hasNext()||orders.hasPrevious())}" style="display: flex; flex-direction: row; justify-content: center; margin-bottom: 5%">
                        <form method="get" style="width: 40px; display: flex; align-items: center" th:if="${orders.isFirst()==false}" th:action="@{/user/orders}">
                            <input type="hidden" th:value="${orders.previousOrFirstPageable().first().getPageNumber()}" name="page">
                            <input type="hidden" th:value="${sort}" name="sort">
                            <input type="hidden" th:value="${sortDirection}" name="sortDirection">
                            <button type="submit" class="btn btn-primary">«</button>
                        </form>
                        <form method="get" style="width: 40px; display: flex; align-items: center" th:if="${orders.hasPrevious()==true}" th:action="@{/user/orders}">
                            <input type="hidden" th:value="${orders.previousPageable().getPageNumber()}" name="page">
                            <input type="hidden" th:value="${sort}" name="sort">
                            <input type="hidden" th:value="${sortDirection}" name="sortDirection">
                            <button type="submit" class="btn btn-primary">‹</button>
                        </form>
                        <form method="get" style="width: 40px; display: flex; align-items: center" th:if="${orders.hasNext()==true}" th:action="@{/user/orders}">
                            <input type="hidden" th:value="${orders.nextPageable().getPageNumber()}" name="page">
                            <input type="hidden" th:value="${sort}" name="sort">
                            <input type="hidden" th:value="${sortDirection}" name="sortDirection">
                            <button type="submit" class="btn btn-primary">›</button>
                        </form>
                        <form method="get" style="width: 40px; display: flex; align-items: center" th:if="${orders.isLast()==false}" th:action="@{/user/orders}">
                            <input type="hidden" th:value="${orders.getTotalPages()-1}" name="page">
                            <input type="hidden" th:value="${sort}" name="sort">
                            <input type="hidden" th:value="${sortDirection}" name="sortDirection">
                            <button type="submit" class="btn btn-primary">»</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<footer style="min-height: 70px" class="p-3 bg-dark text-white"></footer>
</body>
</html>