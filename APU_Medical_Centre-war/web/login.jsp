<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #E0F7FA;
            color: #1C1C1C;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-box {
            background: #FFFFFF;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            box-sizing: border-box;
        }

        .login-box h2 {
            margin-bottom: 25px;
            color: #00BFFF;
            text-align: center;
        }

        .login-box form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .login-box input,
        .login-box select,
        .login-box button {
            width: 100%;
            padding: 12px;
            margin: 8px 0;
            border-radius: 8px;
            font-size: 16px;
            box-sizing: border-box;
        }

        .login-box input,
        .login-box select {
            border: 1px solid #ccc;
        }
        
        .login-box select {
            border: none; /* Remove border */
            border-radius: 12px;
            font-size: 16px;
            background-color: #E0F7FA;
            color: #1C1C1C;
            box-shadow: 0 0 0 1px #00BFFF inset; /* Soft inner border */
            appearance: none;
            -webkit-appearance: none;
            -moz-appearance: none;
            background-image: url('data:image/svg+xml;utf8,<svg fill="%2300BFFF" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/></svg>');
            background-repeat: no-repeat;
            background-position-x: 95%;
            background-position-y: center;
            background-size: 1.2em;
            transition: box-shadow 0.3s ease;
        }

        .login-box input:focus,
        .login-box select:focus {
            border-color: #00BFFF;
            background-color: #F0FFFF;
            outline: none;
        }

        .login-box button {
            background-color: #00BFFF;
            color: white;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .login-box button:hover {
            background-color: #20C997;
        }

        .error-message {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="login-box">
        <h2>Login</h2>
        <form action="login" method="POST">
            <input type="text" id="username" name="username" placeholder="Username" required>
            <input type="password" id="password" name="password" placeholder="Password" required>
            <select id="role" name="role" required>
                <option value="">Select Role</option>
                <option value="CUSTOMER">Customer</option>
                <option value="MANAGER">Manager</option>
                <option value="COUNTER_STAFF">Counter Staff</option>
                <option value="DOCTOR">Doctor</option>
            </select>
            <button type="submit">Login</button>
        </form>

        <!-- Display error message if exists -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>
            
    </div>
</body>
</html>
