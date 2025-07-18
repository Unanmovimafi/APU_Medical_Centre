<%-- 
    Document   : create_user
    Created on : 6 Jun 2025, 1:54:38 pm
    Author     : zihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New User Page</title>
        
        <script>
            function toggleFieldsByRole() {
                // Get the selected role
                var role = document.getElementById('role').value;

                // Get fields
                var nameField = document.getElementById('name');
                var emailField = document.getElementById('email');
                var dateOfBirthField = document.getElementById('dateOfBirth');
                var phoneNumberField = document.getElementById('phoneNumber');
                var allergicField = document.getElementById('allergic');
                var bloodTypeField = document.getElementById('bloodType');

                // Show or hide fields based on the selected role
                if ('MANAGER' === role) {
                    nameField.style.display = 'block';
                    emailField.style.display = 'block';
                    dateOfBirthField.style.display = 'block';
                    phoneNumberField.style.display = 'block';
                    allergicField.style.display = 'none';
                    bloodTypeField.style.display = 'none';
                } else if ('COUNTER_STAFF' === role || 'DOCTOR' === role) {
                    nameField.style.display = 'block';
                    emailField.style.display = 'block';
                    dateOfBirthField.style.display = 'block';
                    phoneNumberField.style.display = 'block';
                    allergicField.style.display = 'block';
                    bloodTypeField.style.display = 'block';
                }
            }
            
            window.onload = function() {
                toggleFieldsByRole();
            };
    </script>
    </head>
    <body>
        <h1>Hello World!</h1>
    <form action="../CreateUser" method="POST">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <label for="role">Role:</label>
        <select id="role" name="role" onchange="javascript:toggleFieldsByRole()" required>
            <option value="" disabled selected>Please Select</option>
            <option value="MANAGER">Manager</option>
            <option value="COUNTER_STAFF">Counter Staff</option>
            <option value="DOCTOR">Doctor</option>
        </select><br><br>

        <div id="name" style="display: none;">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name"><br><br>
        </div>

        <div id="email" style="display: none;">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" style="display: none;"><br><br>
        </div>

        <div id="dateOfBirth" style="display: none;">
        <label for="dateOfBirth">Date of Birth:</label>
        <input type="date" id="dateOfBirth" name="dateOfBirth"><br><br>
        </div>

        <div id="phoneNumber" style="display: none;">
        <label for="phoneNumber">Phone Number:</label>
        <input type="tel" id="phoneNumber" name="phoneNumber" pattern="[0-9]{10}" placeholder="1234567890"><br><br>
        </div>

        <div id="allergic" style="display: none;">
        <label for="allergic">Allergic (Specify allergies):</label>
        <input type="text" id="allergic" name="allergic" placeholder="e.g., Peanuts, Dust"><br><br>
        </div>

        <div id="bloodType" style="display: none;">
        <label for="bloodType">Blood Type:</label>
        <input type="text" id="bloodType" name="bloodType"><br><br>
        </div>

        <button type="submit">Create User</button>
    </form>
    </body>
</html>
