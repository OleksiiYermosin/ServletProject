<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="res"/>

<input type="hidden" value="${requestScope.orderPage.sortField}" name="sort">
<input type="hidden" value="${requestScope.orderPage.sortDirection}"
       name="sortDirection">
<c:if test="${requestScope.orderPage.name!=null && requestScope.orderPage.name.length()!=0}">
    <input type="hidden" value="${requestScope.orderPage.name}" name="name">
</c:if>
<c:if test="${requestScope.orderPage.surname!=null && requestScope.orderPage.surname.length()!=0}">
    <input type="hidden" value="${requestScope.orderPage.surname}" name="surname">
</c:if>
<c:if test="${requestScope.orderPage.searchByDate && requestScope.orderPage.date!=null}">
    <input type="hidden" value="${requestScope.orderPage.searchByDate ? 'on' : ''}" name="searchByDate">
</c:if>
<c:if test="${requestScope.orderPage.searchByName && requestScope.orderPage.name!=null && requestScope.orderPage.name.length()!=0  && requestScope.orderPage.surname!=null && requestScope.orderPage.surname.length()!=0}">
    <input type="hidden" value="${requestScope.orderPage.searchByName ? 'on' : ''}" name="searchByName">
</c:if>
<c:if test="${requestScope.orderPage.searchByDate && requestScope.orderPage.date!=null}">
    <input type="hidden" value="${requestScope.orderPage.date}" name="date">
</c:if>