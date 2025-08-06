<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Create New Customer</h2>

<form method="post" action="${pageContext.request.contextPath}/staff/customer/new">
    <label>Name:</label>
    <input type="text" name="name" required /><br/>

    <label>Email:</label>
    <input type="email" name="email" /><br/>

    <label>Phone Number:</label>
    <input type="text" name="phoneNumber" /><br/>

    <label>Date of Birth:</label>
    <input type="date" name="dateOfBirth" /><br/>

    <label>Username:</label>
    <input type="text" name="username" required /><br/>

    <label>Blood Type:</label>
    <input type="text" name="bloodType" /><br/>

    <label>Allergic Info:</label>
    <textarea name="allergic"></textarea><br/>

    <label>Status:</label>
    <select name="status">
        <option value="ACTIVE">ACTIVE</option>
        <option value="INACTIVE">INACTIVE</option>
    </select><br/>

    <br/>
    <button type="submit">Create</button>
    <a href="${pageContext.request.contextPath}/staff/customer/list">Cancel</a>
</form>
<c:if test="${not empty error}">
    <div style="color: red;">${error}</div>
</c:if>
