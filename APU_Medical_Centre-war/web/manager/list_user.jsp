<%-- 
    Document   : list_user
    Created on : 7 Jun 2025, 12:06:49 am
    Author     : zihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List User Page</title>
    </head>
    <body>
        <h1>Customer Detail</h1>
        <form action="DeleteUser" method="POST" onsubmit="return confirmDelete()">
            <button onchange="javascript:confirmDelete()">Delete Selected Users</button>
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Creation Date Time</th>
                        <th>Username</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>Edit</th>
                        <th>Select</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${listUser}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.creationDatetime}</td>
                            <td>${user.username}</td>
                            <td>${user.role.code}</td>
                            <td>${user.userStatus.code}</td>
                            <td><a href="EditUser?id=${user.id}">Edit</a></td>
                            <td><input type="checkbox" name="selectedUsers" value="${user.id}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form>
        <script type="text/javascript">
            function confirmDelete() {
                return confirm("Are you sure you want to delete the selected users?");
            }
        </script>
    </body>
</html>
