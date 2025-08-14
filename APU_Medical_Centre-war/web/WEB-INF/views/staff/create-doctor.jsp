<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    body {
        background-color: #E0F7FA;
        font-family: Arial, sans-serif;
    }

    h2 {
        color: #00BFFF;
        padding-bottom: 25px;
        justify-self: center;
    }

    .mainbody {
        padding: 40px;
    }

    .form-section {
        width: 100%;
        max-width: 800px;
        margin: 0 auto;
        background: #fff;
        border-radius: 12px;
        padding: 30px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    .form-table {
        width: 100%;
    }

    .form-table td {
        padding: 10px 8px;
        vertical-align: middle;
    }

    .form-table td:first-child {
        width: 150px;
        white-space: nowrap;
        font-weight: bold;
    }

    input[type="text"], input[type="email"], input[type="tel"], select {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 8px;
        box-sizing: border-box;
        font-size: 15px;
    }

    input:focus, select:focus {
        border-color: #00BFFF;
        background-color: #F0FFFF;
        outline: none;
    }

    .button-footer {
        display: flex;
        justify-content: space-between;
        gap: 15px;
        width: 100%;
        margin-top: 30px;
    }

    .button-footer button,
    .button-footer .btn {
        padding: 12px 24px;
        font-size: 14px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.2s ease;
        text-decoration: none;
        text-align: center;
        min-width: 120px;
    }

    .create-btn {
        background-color: #28A745;
        color: white;
    }

    .create-btn:hover {
        background-color: #218838;
    }

    .cancel-btn {
        background-color: #6C757D;
        color: white;
    }

    .cancel-btn:hover {
        background-color: #5A6268;
    }

    .error-message {
        color: #DC3545;
        background-color: #F8D7DA;
        border: 1px solid #F5C6CB;
        border-radius: 8px;
        padding: 10px;
        margin-bottom: 20px;
    }

    input:required:invalid {
        border-color: #DC3545;
        background-color: #fff5f5;
    }
</style>

<div class="mainbody">
    <h2>Create Doctor</h2>
    
    <div class="form-section">
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        
        <form method="post">
            <table class="form-table">
                <tr>
                    <td><strong>Name :</strong></td>
                    <td><input type="text" name="name" value="${param.name}" required /></td>
                </tr>
                <tr>
                    <td><strong>Email :</strong></td>
                    <td><input type="email" name="email" value="${param.email}" /></td>
                </tr>
                <tr>
                    <td><strong>Phone Number :</strong></td>
                    <td><input type="tel" name="phoneNumber" value="${param.phoneNumber}" pattern="[0-9]+" title="Please enter numbers only" oninput="this.value = this.value.replace(/[^0-9]/g, '')" /></td>
                </tr>
                <tr>
                    <td><strong>Gender :</strong></td>
                    <td>
                        <select name="gender">
                            <option value="">-- Select Gender --</option>
                            <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                            <option value="Other" ${param.gender == 'Other' ? 'selected' : ''}>Other</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><strong>Username :</strong></td>
                    <td><input type="text" name="username" value="${param.username}" required /></td>
                </tr>
                <tr>
                    <td><strong>Status :</strong></td>
                    <td>
                        <select name="status">
                            <option value="ACTIVE" ${param.status == 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
                            <option value="INACTIVE" ${param.status == 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                        </select>
                    </td>
                </tr>
            </table>

            <div class="button-footer">
                <button type="submit" class="create-btn">Create Doctor</button>
                <a href="${pageContext.request.contextPath}/staff/employee/list" class="btn cancel-btn">Cancel</a>
            </div>
        </form>
    </div>
</div>
