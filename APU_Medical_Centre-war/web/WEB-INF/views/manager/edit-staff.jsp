<%-- 
    Document   : edit_user
    Created on : 8 Jun 2025, 12:04:53 am
    Author     : zihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit User Page</title>
    </head>
    <body>
        <form action="UpdateUser" method="POST">
            <input type="hidden" id="id" name="id" value="${user.id}" />

            <label for="name">Username:</label>
            <input type="text" id="username" name="username" value="${user.username}" required /><br><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" value="${user.password}" required /><br><br>

            <button type="submit">Update</button>
        </form>
    </body>
</html>
