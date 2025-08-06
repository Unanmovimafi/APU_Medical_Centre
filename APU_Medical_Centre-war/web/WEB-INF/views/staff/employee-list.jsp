<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<style>
  .dropdown {
    position: relative;
    display: inline-block;
  }
  .dropdown-content {
    display: none;
    position: absolute;
    background-color: #f0f0f0;
    min-width: 180px;
    z-index: 1;
  }
  .dropdown:hover .dropdown-content {
    display: block;
  }
  .dropdown-content a {
    color: black;
    padding: 10px 14px;
    display: block;
    text-decoration: none;
  }
  .dropdown-content a:hover {
    background-color: #ddd;
  }
</style>
<h2>Employee List</h2>

<form method="get" action="${pageContext.request.contextPath}/staff/employee/list">
    <label>Keyword:</label>
    <input type="text" name="keyword" value="${param.keyword}" placeholder="Search..." />

    <label>Filter by:</label>
    <select name="column">
        <option value="name" ${param.column == 'name' ? 'selected' : ''}>Name</option>
        <option value="username" ${param.column == 'username' ? 'selected' : ''}>Username</option>
        <option value="email" ${param.column == 'email' ? 'selected' : ''}>Email</option>
        <option value="phone" ${param.column == 'phone' ? 'selected' : ''}>Phone</option>
    </select>

    <label>Status:</label>
    <select name="status">
        <option value="">-- All --</option>
        <option value="ACTIVE" ${param.status == 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
        <option value="INACTIVE" ${param.status == 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
    </select>

    <label>Role:</label>
    <select name="role">
        <option value="">-- All --</option>
        <option value="Doctor" ${param.role == 'Doctor' ? 'selected' : ''}>Doctor</option>
        <option value="Counter Staff" ${param.role == 'Counter Staff' ? 'selected' : ''}>Counter Staff</option>
    </select>

    <button type="submit">Search</button>
</form>
<div class="dropdown">
  <button class="btn">+ Create New Employee</button>
  <div class="dropdown-content">
    <a href="${pageContext.request.contextPath}/staff/staff/new">Add Counter Staff</a>
    <a href="${pageContext.request.contextPath}/staff/doctor/new">Add Doctor</a>
  </div>
</div>

<br/>

<table border="1" cellpadding="8" cellspacing="0">
    <thead>
        <tr>
            <th>Role</th>
            <th>Name</th>
            <th>Username</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Status</th>
            <th>Last Updated</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="emp" items="${employeeList}">
            <tr>
                <td>${emp.role}</td>
                <td>${emp.name}</td>
                <td>${emp.username}</td>
                <td>${emp.email}</td>
                <td>${emp.phoneNumber}</td>
                <td>${emp.status}</td>
                <td>${emp.lastUpdateDatetime}</td>
                <td>
                    <c:choose>
                        <c:when test="${emp.role == 'Counter Staff'}">
                          <a href="${pageContext.request.contextPath}/staff/staff/detail?id=${emp.id}">Detail</a>
                        </c:when>
                        <c:otherwise>
                          <a href="${pageContext.request.contextPath}/staff/doctor/detail?id=${emp.id}">Detail</a>
                        </c:otherwise>
                    </c:choose> |
                    <a href="${pageContext.request.contextPath}/staff/employee/delete?id=${emp.id}&role=${emp.role}"
                       onclick="return confirm('Are you sure you want to delete this employee?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
