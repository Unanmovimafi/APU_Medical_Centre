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
        <!-- For Modal CSS -->
    <style>
        /* Modal styles */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgba(0, 0, 0, 0.4); /* Black with transparency */
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%; /* Could be adjusted */
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
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
                            <td><a href="javascript:viewUser(${user.id});">View</a></td>
                            <td><input type="checkbox" name="selectedUsers" value="${user.id}" /></td>
                        </tr>
                        
                        <!--Pop Up Window for diplay user Details-->
                        <div class="modal" userId="${user.id}" style="display: none;">
                            <div class="modal-content">
                                <a href="javascript:closeModal();">CLOSE</a>
                                <h3>User Details</h3>
                                <a href="EditUser?id=${user.id}">Edit</a>
                                <div class="userDetails">
                                    Username: <input type="text" value="${user.username}" disabled></input
                                    Name: <input type="text" value="${user.staffDetail.name}" disabled></input>
                                    ${user.creationDatetime}
                                    ${user.role.code}
                                    ${user.userStatus.code}
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </tbody>
            </table>
        </form>
        <script type="text/javascript">
            function confirmDelete() {
                return confirm("Are you sure you want to delete the selected users?");
            }
            
             function viewUser(userId) {
                var element = document.querySelector('[userId="'+userId+'"]');
                element.style.display = "block";
            }
            
            function closeModal() {
                var userModals = document.getElementsByClassName('modal');
                for (let i = 0; i < userModals.length; i++) {
                    userModals[i].style.display = "none";
                }
            }
            
            // Close the modal if the user clicks outside of the modal content
            window.onclick = function(event) {
                var userModals = document.getElementsByClassName('modal');
                    for (var i = 0; i < userModals.length; i++) {
                        if (event.target === userModals[i]) {
                            closeModal();
                        }
                    }
            };
        </script>
    </body>
</html>
