<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    h2 {
        color: #00BFFF;
        padding-bottom: 25px;
    }

    .mainbody {
        margin: 0;
        padding: 40px;
    }
    
    .container {
        display: flex;
        gap: 30px;
        align-items: flex-start;
    }

    .form-section {
        width: 66.66%;
        background: #fff;
        border-radius: 12px;
        padding: 30px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    .picture-section {
        width: 33.33%;
        background: #fff;
        border-radius: 12px;
        padding: 20px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        text-align: center;
    }

    .picture-section img {
        width: 150px;
        height: 150px;
        border-radius: 10px;
        border: 1px solid #ccc;
        object-fit: cover;
    }

    .form-table td {
        padding: 10px 8px;
        vertical-align: top;
    }

    .form-table input,
    .form-table select {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 8px;
        box-sizing: border-box;
        font-size: 15px;
    }

    .form-table input:focus,
    .form-table select:focus {
        border-color: #00BFFF;
        background-color: #F0FFFF;
        outline: none;
    }

    .form-table .text-display {
        padding: 10px;
        background-color: #F8F9FA;
        border-radius: 8px;
    }

    button, .btn {
        padding: 10px 16px;
        margin: 10px 6px 0 0;
        border: none;
        border-radius: 8px;
        font-size: 15px;
        cursor: pointer;
    }

    #editBtn {
        background-color: #00BFFF;
        color: white;
    }

    #saveBtn {
        background-color: #28A745;
        color: white;
    }

    #cancelBtn {
        background-color: #DC3545;
        color: white;
    }

    #backBtn {
        background-color: #20C997;
        color: white;
        text-decoration: none;
        display: inline-block;
    }
    .editable {
        display: none;
    }
</style>
<div class="mainbody">
<h2>Edit Profile</h2>
<div class="container">
    <div class="form-section">
        <form action="${pageContext.request.contextPath}/edit-profile" method="post" id="editProfileForm">
            <input type="hidden" name="id" value="${staff.id}" />

            <table class="form-table">
                <tr>
                    <td><strong>Name:</strong></td>
                    <td>
                        <span class="text-display">${staff.name}</span>
                        <input type="text" name="name" value="${staff.name}" class="editable" style="display: none;" />
                    </td>
                </tr>
                <tr>
                    <td><strong>Email:</strong></td>
                    <td>
                        <span class="text-display">${staff.email}</span>
                        <input type="email" name="email" value="${staff.email}" class="editable" style="display:none;" />
                    </td>
                </tr>
                <tr>
                    <td><strong>Phone Number:</strong></td>
                    <td>
                        <span class="text-display">${staff.phoneNumber}</span>
                        <input type="text" name="phoneNumber" value="${staff.phoneNumber}" style="display:none;" />
                    </td>
                </tr>
                <tr>
                    <td><strong>Username:</strong></td>
                    <td>
                        <span class="text-display">${staff.username}</span>
                        <input type="text" name="username" value="${staff.username}" style="display:none;" />
                    </td>
                </tr>
                <tr>
                    <td><strong>Status:</strong></td>
                    <td>
                        <span class="text-display">${staff.status}</span>
                        <select name="status" style="display:none;">
                            <option value="ACTIVE" ${staff.status == 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
                            <option value="INACTIVE" ${staff.status == 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><strong>Old Password:</strong></td>
                    <td>
                        <span class="text-display">********</span>
                        <input type="password" name="oldPassword" placeholder="Enter current password" style="display:none;" />
                    </td>
                </tr>
                <tr>
                    <td><strong>New Password:</strong></td>
                    <td>
                        <span class="text-display">********</span>
                        <input type="password" name="newPassword" placeholder="Leave blank to keep unchanged" style="display:none;" />
                    </td>
                </tr>
            </table>

            <br />
            <button type="button" id="editBtn" onclick="enableEdit()">Edit</button>
            <button type="submit" id="saveBtn" style="display: none;">Save</button>
            <a href="${pageContext.request.contextPath}/staff/dashboard" class="btn" id="backBtn">Back</a>
            <button type="button" id="cancelBtn" style="display: none;" onclick="disableEdit()">Cancel</button>
        </form>
    </div>

    <div class="picture-section">
        <h4>Profile Picture</h4>
        <c:choose>
            <c:when test="${not empty staff.profilePicture}">
                <img src="${staff.profilePicture}" alt="Profile Picture" />
            </c:when>
            <c:otherwise>
                <p>No picture uploaded</p>
            </c:otherwise>
        </c:choose>

        <form method="post" action="${pageContext.request.contextPath}/staff/edit-profile-picture" enctype="multipart/form-data" style="margin-top: 15px;">
            <input type="hidden" name="id" value="${staff.id}" />
            <input type="file" name="profilePicture" accept="image/*" required />
            <button type="submit" style="background-color:#00BFFF; color:white;">Upload</button>
        </form>
    </div>
</div>
</div>

<script>
    function enableEdit() {
        const displays = document.querySelectorAll('.text-display');
        const inputs = document.querySelectorAll('.editable');

        displays.forEach(el => el.style.display = 'none');
        inputs.forEach(el => el.style.display = 'block');

        document.getElementById('editBtn').style.display = 'none';
        document.getElementById('saveBtn').style.display = 'inline';
        document.getElementById('backBtn').style.display = 'none';
        document.getElementById('cancelBtn').style.display = 'inline';
    }

    function disableEdit() {
        const displays = document.querySelectorAll('.text-display');
        const inputs = document.querySelectorAll('.editable');

        displays.forEach(el => el.style.display = 'block');
        inputs.forEach(el => el.style.display = 'none');

        document.getElementById('editBtn').style.display = 'inline';
        document.getElementById('saveBtn').style.display = 'none';
        document.getElementById('backBtn').style.display = 'inline';
        document.getElementById('cancelBtn').style.display = 'none';
    }
</script>
