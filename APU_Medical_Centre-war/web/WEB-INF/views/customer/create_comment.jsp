<%-- 
    Document   : create_comment
    Created on : 9 Jun 2025, 1:43:48 pm
    Author     : zihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Give Your Comment</title>
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
        <h1>Hello World!</h1>
        <form action="CreateComment" method="POST">
            
            <input type="hidden" name="selectedUserId" id="selectedUserId" value="">
            
            <!-- Target user with a button to open modal -->
            <label for="target_user">Target Staff:</label>
            <input type="text" id="target_user" name="target_user" readonly placeholder="Click to select a user" required>
            <td><a href="javascript:viewUser();">SELECT</a></td>
            <br><br>

            <!-- Rating selection -->
            <label for="rating">Rating (1-10):</label>
            <input type="number" id="rating" name="rating" min="1" max="10" required>
            <br><br>

            <!-- Comment content -->
            <label for="content">Your Comment:</label>
            <textarea id="content" name="content" rows="5" cols="40" required></textarea>
            <br><br>

            <!-- Submit button -->
            <button type="submit">Submit Comment</button>
        </form>
        
        
        <!-- Modal for user selection -->
        <div id="staffList" class="modal">
            <div class="modal-content">
                <ul class="user-list">
                    <span><a href="javascript:closeModal();">CLOSE</a></span>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Select</th>
                                <th>ID</th>
                                <th>Creation Date Time</th>
                                <th>Username</th>
                                <th>Role</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>

                            <c:forEach var="user" items="${userList}">
                                <tr>
                                    <td><input type="radio" name="selectedUser" value="${user.id}"></td>
                                    <td>${user.id}</td>
                                    <td>${user.creationDatetime}</td>
                                    <td>${user.username}</td>
                                    <td>${user.role.code}</td>
                                    <td>${user.userStatus.code}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>




            <td><a href="javascript:saveUser();">SAVE</a></td>

                </ul>

            </div>
        </div>
        
        
        
        
        <script type="text/javascript">
            
             function viewUser() {
                var element = document.getElementById("staffList");
                element.style.display = "block";
            }
            
            function closeModal() {
                var checkboxes = document.querySelectorAll('input[name="selectedUser"]:checked');
    
                checkboxes.forEach(function(checkbox) {
                    checkbox.checked = false;
                });
                var userModals = document.getElementById('staffList');
                    userModals.style.display = "none";

            }
            
            window.onclick = function(event) {
                var userModals = document.getElementById('staffList');
                        if (event.target === userModals) {
                            closeModal();
                        }
            };
            
            function saveUser() {
                // Check if a user is selected (i.e., a radio button is checked)
                var selectedUserId = document.querySelector("input[name='selectedUser']:checked");

                if (!selectedUserId) {
                    // If no user is selected, alert the user
                    alert("Please select a user to save.");
                } else {
                    // If a user is selected, update the hidden field and submit the form
                    var userId = selectedUserId.value;
                    document.getElementById("selectedUserId").value = userId;
                    
                     var userModals = document.getElementById('staffList');
                    userModals.style.display = "none";
                    
                    var targetUser = document.getElementById('target_user');
                    targetUser.value = userId;
                }
            }
            
        </script>
        
        

    </body>
</html>
