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
        <fmt:message key="title.admin.page"/>
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
                    <form action="${pageContext.request.contextPath}/service/admin/orders" method="get" style="width: 80%"
                          class="sorting-form">
                        <div class="form-inner">
                            <div class="form1">
                                <label for="name" class="sorting-form-element">
                                    <fmt:message key="name.message"/>
                                </label>
                                <input type="text" pattern="[A-Z][a-z]+|[А-ЯЁІЇ][а-яёії']+" value="${requestScope.orderPage.name}" name="name" id="name"
                                       class="sorting-form-element">
                                <label for="sur" class="sorting-form-element">
                                    <fmt:message key="sur.message"/>
                                </label>
                                <input type="text" pattern="[A-Z][a-z]+|[А-ЯЁІЇ][а-яёії']+" value="${requestScope.orderPage.surname}" name="surname" id="sur"
                                       class="sorting-form-element">
                                <input type="checkbox" ${requestScope.orderPage.searchByName ? 'checked' : ''} name="searchByName" class="sorting-form-small">
                                <label for="date" style="width: 50px" class="sorting-form-element">
                                    <fmt:message key="date.message"/>
                                </label>
                                <input type="date" name="date" value="${requestScope.orderPage.date}" id="date"
                                       class="sorting-form-element">
                                <input type="checkbox" ${requestScope.orderPage.searchByDate ? 'checked' : ''} name="searchByDate" class="sorting-form-small">
                                <button type="submit" class="btn btn-success">
                                    <fmt:message key="search.action"/>
                                </button>
                            </div>
                            <div class="form1">
                                <label for="sort" class="sorting-form-element">
                                    <fmt:message key="sort.message"/>
                                </label>
                                <select name="sort" id="sort" class="sorting-form-element" required>
                                    <option ${requestScope.orderPage.sortField.equals('id') ? 'selected="selected"' : ''}
                                            value="id">
                                        <fmt:message key="sort.id.message"/>
                                    </option>
                                    <option ${requestScope.orderPage.sortField.equals('date') ? 'selected="selected"' : ''}
                                            value="date">
                                        <fmt:message key="sort.date.message"/>
                                    </option>
                                    <option ${requestScope.orderPage.sortField.equals('total') ? 'selected="selected"' : ''}
                                            value="total">
                                        <fmt:message key="sort.total.message"/>
                                    </option>
                                </select>
                                <label for="sort-direction" class="sorting-form-element">
                                    <fmt:message key="sort.direction.message"/>
                                </label>
                                <select name="sortDirection" id="sort-direction" class="sorting-form-element" required>
                                    <option ${requestScope.orderPage.sortDirection.equals('ASC') ? 'selected="selected"' : ''}
                                            value="ASC">
                                        <fmt:message key="sort.direction.asc.message"/>
                                    </option>
                                    <option ${requestScope.orderPage.sortDirection.equals('DESC') ? 'selected="selected"' : ''}
                                            value="DESC">
                                        <fmt:message key="sort.direction.desc.message"/>
                                    </option>
                                </select>
                                <button type="submit" class="btn btn-success">
                                    <fmt:message key="sort.action"/>
                                </button>
                            </div>
                        </div>

                    </form>
                    <c:if test="${requestScope.orderPage.entity.size()==0}">
                        <div style="display: flex; align-items: center; justify-content: center">
                            <p style="font-size: 18px">
                                <fmt:message key="description.message"/>
                            </p>
                        </div>
                    </c:if>
                    <c:if test="${requestScope.orderPage.entity.size()!=0}">
                        <table class="table">
                            <thead class="thead-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">
                                    <fmt:message key="client.info.message"/>
                                </th>
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
                                    <fmt:message key="order.date.prompt"/>
                                </th>
                                <th scope="col">
                                    <fmt:message key="order.action.message"/>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="order" items="${requestScope.orderPage.entity}" varStatus="loop">
                                <tr>
                                    <td>
                                            ${order.id}
                                    </td>
                                    <td>
                                            ${order.user.surname} ${order.user.name}
                                    </td>
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
                                    <td>
                                            ${order.date}
                                    </td>
                                    <td style="display: flex; justify-content: space-around">
                                        <c:if test="${order.orderStatus.name.equals('ACTIVE')}">
                                            <form method="post"
                                                  action="${pageContext.request.contextPath}/service/admin/orders/finish"
                                                  style="display: flex; margin: 0">
                                                <input type="hidden" value="false" name="mode">
                                                <input type="hidden" value="${order.id}" name="id">
                                                <input type="hidden" value="${order.user.id}" name="user_id">
                                                <button type="submit" class="btn btn-success">✓</button>
                                            </form>
                                        </c:if>
                                        <c:if test="${!order.orderStatus.name.equals('CANCELED')}">
                                            <button type="button" onclick="myFunction('details${loop.index}')"
                                                    class="btn btn-info">
                                                <span style="font-style: italic; font-weight: bold">i</span>
                                            </button>
                                        </c:if>
                                        <c:if test="${order.orderStatus.name.equals('CANCELED')}">
                                            <p style="font-size: 18px">
                                                <fmt:message key="order.canceled.value"/>
                                            </p>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${requestScope.orderPage.entity.size()!=0}">
                        <c:forEach var="order" items="${requestScope.orderPage.entity}" varStatus="loop">
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
                    <c:if test="${requestScope.orderPage.entity.size()!=0 && (requestScope.orderPage.nextPage!=null || requestScope.orderPage.previousPage!=null)}">
                        <div style="display: flex; flex-direction: row; justify-content: center; margin-bottom: 5%">
                            <c:if test="${requestScope.orderPage.currentPage!=0}">
                                <form method="get" style="width: 40px; display: flex; align-items: center"
                                      action="${pageContext.request.contextPath}/service/admin/orders">
                                    <input type="hidden" value="0" name="page">
                                    <jsp:include page="searchdetails.jsp"/>
                                    <button type="submit" class="btn btn-primary">«</button>
                                </form>
                            </c:if>
                            <c:if test="${requestScope.orderPage.previousPage!=null}">
                                <form method="get" style="width: 40px; display: flex; align-items: center"
                                      action="${pageContext.request.contextPath}/service/admin/orders">
                                    <input type="hidden" value="${requestScope.orderPage.previousPage}" name="page">
                                    <jsp:include page="searchdetails.jsp"/>
                                    <button type="submit" class="btn btn-primary">‹</button>
                                </form>
                            </c:if>
                            <c:if test="${requestScope.orderPage.nextPage!=null}">
                                <form method="get" style="width: 40px; display: flex; align-items: center"
                                      action="${pageContext.request.contextPath}/service/admin/orders">
                                    <input type="hidden" value="${requestScope.orderPage.nextPage}" name="page">
                                    <jsp:include page="searchdetails.jsp"/>
                                    <button type="submit" class="btn btn-primary">›</button>
                                </form>
                            </c:if>
                            <c:if test="${requestScope.orderPage.currentPage!=requestScope.orderPage.lastPage}">
                                <form method="get" style="width: 40px; display: flex; align-items: center"
                                      action="${pageContext.request.contextPath}/service/admin/orders">
                                    <input type="hidden" value="${requestScope.orderPage.lastPage}" name="page">
                                    <jsp:include page="searchdetails.jsp"/>
                                    <button type="submit" class="btn btn-primary">»</button>
                                </form>
                            </c:if>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<footer style="min-height: 70px" class="p-3 bg-dark text-white"></footer>
</body>
</html>