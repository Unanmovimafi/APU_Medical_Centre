<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Customer List</h2>

<form method="get" action="${pageContext.request.contextPath}/staff/customer">
    <label for="column">Search by:</label>
    <select name="column" id="column">
        <option value="name" ${param.column == 'name' ? 'selected' : ''}>Name</option>
        <option value="username" ${param.column == 'username' ? 'selected' : ''}>Username</option>
        <option value="email" ${param.column == 'email' ? 'selected' : ''}>Email</option>
    </select>
    <input type="text" name="keyword" placeholder="Enter keyword" value="${param.keyword}" />
    <button type="submit">Search</button>
    <a href="${pageContext.request.contextPath}/doctor/customer">Reset</a>

</form>

<br/>

<table border="1" cellpadding="8" cellspacing="0" style="width:100%; border-collapse:collapse;">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Username</th>
            <th>Email</th>
            <th>Status</th>
            <th>Phone</th>
            <th>Created By</th>
            <th>Updated By</th>
            <th>Created At</th>
            <th>Updated At</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="cust" items="${customerList}">
            <tr>
                <td>${cust.id}</td>
                <td>${cust.name}</td>
                <td>${cust.username}</td>
                <td>${cust.email}</td>
                <td>${cust.status}</td>
                <td>${cust.phoneNumber}</td>
                <td>${cust.createBy}</td>
                <td>${cust.lastUpdateBy}</td>
                <td><fmt:formatDate value="${cust.creationDatetime}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td><fmt:formatDate value="${cust.lastUpdateDatetime}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/doctor/customer/detail?id=${cust.id}">Detail</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
