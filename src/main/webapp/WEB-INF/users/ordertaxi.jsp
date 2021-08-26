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
        <fmt:message key="title.order.taxi"/>
    </title>
    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../../common/header.jsp"/>
<div class="content-holder">
    <jsp:include page="sidebar.jsp"/>
    <div style="width: available" class="content">
        <div class="container">
            <div class="content-container">
                <form method="post" action="${pageContext.request.contextPath}/service/user/order-taxi" class="dataForm">
                    <label class="form-element" style="margin-top: 10%" for="startAddress">
                        <fmt:message key="start.address.prompt"/>
                    </label>
                    <select name="first-street" id="startAddress" style="width: 185px" class="form-element" required>
                        <option selected value="вул. Перша, б. 1">
                            <fmt:message key="first.street.value"/>
                        </option>
                        <option value="вул. Друга, б. 2">
                            <fmt:message key="second.street.value"/>
                        </option>
                        <option value="вул. Третя, б. 3">
                            <fmt:message key="third.street.value"/>
                        </option>
                        <option value="вул. Четверта, б. 4">
                            <fmt:message key="fourth.street.value"/>
                        </option>
                        <option value="вул. П`ята, б. 5">
                            <fmt:message key="fifth.street.value"/>
                        </option>
                    </select>
                    <c:if test="${requestScope.validationError!=null && requestScope.validationError==true}">
                        <p style="font-size: 16px" class="form-element">
                            <fmt:message key="order.taxi.address.error.message"/>
                        </p>
                    </c:if>
                    <label class="form-element" for="finishAddress">
                        <fmt:message key="finish.address.prompt"/>
                    </label>
                    <select name="second-street" id="finishAddress" style="width: 185px" class="form-element" required>
                        <option selected value="вул. Перша, б. 1">
                            <fmt:message key="first.street.value"/>
                        </option>
                        <option value="вул. Друга, б. 2">
                            <fmt:message key="second.street.value"/>
                        </option>
                        <option value="вул. Третя, б. 3">
                            <fmt:message key="third.street.value"/>
                        </option>
                        <option value="вул. Четверта, б. 4">
                            <fmt:message key="fourth.street.value"/>
                        </option>
                        <option value="вул. П`ята, б. 5">
                            <fmt:message key="fifth.street.value"/>
                        </option>
                    </select>
                    <c:if test="${requestScope.validationError!=null && requestScope.validationError==true}">
                        <p style="font-size: 16px" class="form-element">
                            <fmt:message key="order.taxi.address.error.message"/>
                        </p>
                    </c:if>
                    <label for="taxiClassCombo" class="form-element">
                        <fmt:message key="taxi.class.prompt"/>
                    </label>
                    <select name="taxi-class" id="taxiClassCombo" style="width: 185px" class="form-element" required>
                        <option selected value="ECONOMY">
                            <fmt:message key="taxi.class.economy.value"/>
                        </option>
                        <option value="COMFORT">
                            <fmt:message key="taxi.class.comfort.value"/>
                        </option>
                        <option value="BUSINESS">
                            <fmt:message key="taxi.class.business.value"/>
                        </option>
                    </select>
                    <label class="form-element" for="humanCapacity">
                        <fmt:message key="human.amount.prompt"/>
                    </label>
                    <input type="number" min="1" name="human-capacity" class="form-element" id="humanCapacity" required>

                    <label class="form-element" for="comment">
                        <fmt:message key="order.comment.prompt"/>
                    </label>
                    <textarea name="comment" rows="3" cols="23" class="form-element" id="comment"></textarea>

                    <button style="margin-bottom: 10%" type="submit" class="btn btn-success">
                        <fmt:message key="title.order.taxi"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
<footer style="min-height: 70px" class="p-3 bg-dark text-white"></footer>
</body>
</html>