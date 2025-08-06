<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Medicine List</h2>

<form method="get" action="${pageContext.request.contextPath}/staff/medicine">
    <label for="column">Search by:</label>
    <select name="column" id="column">
        <option value="name" ${param.column == 'name' ? 'selected' : ''}>Name</option>
        <option value="createBy" ${param.column == 'createBy' ? 'selected' : ''}>Created By</option>
        <option value="lastUpdateBy" ${param.column == 'lastUpdateBy' ? 'selected' : ''}>Updated By</option>
    </select>
    <input type="text" name="keyword" placeholder="Enter keyword" value="${param.keyword}" />
    <button type="submit">Search</button>
    <a href="${pageContext.request.contextPath}/staff/medicine">Reset</a>
</form>

<br/>

<table border="1" cellpadding="8" cellspacing="0" style="width:100%; border-collapse:collapse;">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price (RM)</th>
            <th>Created By</th>
            <th>Created At</th>
            <th>Updated By</th>
            <th>Last Updated</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="med" items="${medicineList}">
            <tr>
                <td>${med.id}</td>
                <td>${med.name}</td>
                <td>${med.description}</td>
                <td>${med.price}</td>
                <td>${med.createBy}</td>
                <td><fmt:formatDate value="${med.creationDatetime}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td>${med.lastUpdateBy}</td>
                <td><fmt:formatDate value="${med.lastUpdateDatetime}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/staff/medicine/delete" style="display:inline;">
                        <input type="hidden" name="id" value="${med.id}" />
                        <button type="submit" onclick="return confirm('Are you sure to delete?');">Delete</button>
                    </form>
                    <a href="${pageContext.request.contextPath}/staff/medicine/detail?id=${med.id}">Details</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
