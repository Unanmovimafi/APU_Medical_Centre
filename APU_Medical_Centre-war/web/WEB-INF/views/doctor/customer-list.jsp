<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<style>
    .mainbody {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
    }

    h2 {
        color: #00BFFF;
        margin-bottom: 25px;
    }

    form {
        display: flex;
        align-items: center;
        justify-content: space-between;
        gap: 10px;
        flex-wrap: wrap;
        margin-bottom: 20px;
    }
    
    .search-controls {
        display: flex;
        align-items: center;
        gap: 10px;
        flex-wrap: wrap;
    }

    .create-btn {
        margin-left: auto;
    }
    
    input[type="text"], select {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 8px;
    }

    button, .btn {
        padding: 10px 16px;
        border: none;
        border-radius: 8px;
        font-size: 14px;
        cursor: pointer;
        text-decoration: none;
        background-color: #00BFFF;
        color: white;
        transition: background-color 0.2s ease;
    }

    .btn:hover, button:hover {
        background-color: #00ACC1;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        background: white;
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    }

    th, td {
        text-align: left;
        padding: 12px;
        border-bottom: 1px solid #ddd;
        color: #1C1C1C;
    }

    th {
        background-color: #E8FAFD;
    }

    td a {
        color: #00BFFF;
        text-decoration: none;
    }

    td a:hover {
        text-decoration: underline;
    }

    .icon-action {
        color: #33C9E7;
        font-size: 25px;
        vertical-align: middle;
        cursor: pointer;
        transition: color 0.2s ease;
    }

    .icon-action:hover {
        color: #00BFFF;
    }
    
    .action-link {
        text-decoration: none;
    }

    .action-link:hover {
        text-decoration: none;
    }
</style>

<div class="mainbody">
<h2>Patient List</h2>

<form method="get" action="${pageContext.request.contextPath}/doctor/customer/list">
    <div class="search-controls">
        <label for="column">Search by:</label>
        <select name="column" id="column">
            <option value="name" ${param.column == 'name' ? 'selected' : ''}>Name</option>
            <option value="email" ${param.column == 'email' ? 'selected' : ''}>Email</option>
        </select>
        <input type="text" name="keyword" placeholder="Enter keyword" value="${param.keyword}" />
        <button type="submit">Search</button>
        <a href="${pageContext.request.contextPath}/doctor/customer/list" class="btn">Reset</a>
    </div>
</form>

<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Status</th>
            <th>Phone</th>
            <th>Updated At</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="cust" items="${customerList}">
            <tr>
                <td>${cust.name}</td>
                <td>${cust.email}</td>
                <td>${cust.status}</td>
                <td>${cust.phoneNumber}</td>
                <td><fmt:formatDate value="${cust.lastUpdateDatetime}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/doctor/customer/detail?id=${cust.id}" class="action-link" title="View Patient Details">
                        <span class="material-icons icon-action">visibility</span>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</div>
