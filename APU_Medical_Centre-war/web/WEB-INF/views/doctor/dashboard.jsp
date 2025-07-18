<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Dashboard - APU Medical Centre</title>
  <link rel="stylesheet" href="../assets/css/style.css" />
  <script>
    function toggleSidebar() {
      document.getElementById('sidebar').classList.toggle('collapsed');
    }
  </script>
</head>
<body>


  <div class="layout">
    <%@ include file="../layout/sidebar.jsp" %>

    <main>
        
        <%@ include file="../layout/header.jsp" %>
        <content>
            <h2 style="color: var(--nav-dark);">Dashboard</h2>
            <p>This is your dashboard for managing student health, appointments, and records.</p>

            <!-- You can add charts, cards, and other dashboard widgets here -->
            <div class="card" style="margin-top: 20px; padding: 16px; background-color: white; border-radius: 12px; box-shadow: 0 2px 6px rgba(0,0,0,0.1);">
              <h3 style="margin-bottom: 10px;">Upcoming Appointments</h3>
              <p>No upcoming appointments.</p>
            </div>
        </content>
        
        <%@ include file="../layout/footer.jsp" %>
    </main>
  </div>


</body>
</html>
