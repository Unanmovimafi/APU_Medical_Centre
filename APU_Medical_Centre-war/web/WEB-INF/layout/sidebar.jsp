<%@ page import="model.manager.Manager" %> 
<%@ page import="model.counterstaff.CounterStaff" %> 
<%@ page import="model.customer.Customer" %> 
<%@ page import="model.doctor.Doctor" %> 
<% Manager managerSession = (Manager) session.getAttribute("managerSession");
CounterStaff staffSession = (CounterStaff)
session.getAttribute("counterStaffSession"); Customer customerSession =
(Customer) session.getAttribute("customerSession"); Doctor doctorSession =
(Doctor) session.getAttribute("doctorSession"); String role = "GUEST"; String
displayName = "Guest"; if (managerSession != null) { role = "MANAGER";
displayName = managerSession.getName(); } else if (staffSession != null) { role
= "COUNTER_STAFF"; displayName = staffSession.getName(); } else if
(customerSession != null) { role = "CUSTOMER"; displayName =
customerSession.getName(); } else if (doctorSession != null) { role = "DOCTOR";
displayName = doctorSession.getName(); } %>

<link
  href="https://fonts.googleapis.com/icon?family=Material+Icons"
  rel="stylesheet"
/>
<link
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
  rel="stylesheet"
/>

<aside id="sidebar">
  <button class="toggle-btn" onclick="toggleSidebar()">
    <i class="fas fa-bars"></i>
  </button>

  <div class="profile-section">
    <img
      src="../assets/img/default-profile.png"
      alt="Profile"
      class="profile-img"
    />
    <h3><%= displayName %></h3>
    <span class="role-badge"><%= role.replace("_", " ") %></span>
  </div>

  <div class="nav-menu">
    <% if ("MANAGER".equalsIgnoreCase(role)) { %>

    <a href="../manager/dashboard.jsp" class="nav-item">
      <i class="fas fa-tachometer-alt"></i>Dashboard
    </a>
    <details class="nav-dropdown">
      <summary><i class="fas fa-users"></i>User Management</summary>
      <div class="nav-dropdown-content">
        <a
          href="${pageContext.request.contextPath}/manager/employee/list"
          class="nav-item"
        >
          <i class="fas fa-id-badge"></i>Employee List
        </a>
      </div>
    </details>
    <details class="nav-dropdown">
      <summary><i class="fas fa-calendar-check"></i>Appointments</summary>
      <div class="nav-dropdown-content">
        <a href="../appointments/calendar.jsp" class="nav-item">
          <i class="fas fa-calendar"></i>Calendar
        </a>
        <a href="../appointments/requestList.jsp" class="nav-item">
          <i class="fas fa-clock"></i>Request List
        </a>
        <a href="../appointments/comments.jsp" class="nav-item">
          <i class="fas fa-comments"></i>Comments
        </a>
      </div>
    </details>
    <details>
      <summary><i class="fas fa-chart-bar"></i>Reports</summary>
      <div style="margin-left: 10px;">
        <a href="${pageContext.request.contextPath}/manager/customer/report">Customer Report</a>
      </div>
    </details>

    <% } else if ("COUNTER_STAFF".equalsIgnoreCase(role)) { %>

    <a
      href="${pageContext.request.contextPath}/staff/dashboard"
      class="nav-item"
    >
      <i class="fas fa-tachometer-alt"></i>Dashboard
    </a>
    <a
      href="${pageContext.request.contextPath}/staff/edit-profile"
      class="nav-item"
    >
      <i class="fas fa-user-cog"></i>Edit Profile
    </a>
    <details class="nav-dropdown">
      <summary>
        <span class="summary-content">
          <i class="fas fa-users"></i>User Management
        </span>
      </summary>
      <div class="nav-dropdown-content">
        <a
          href="${pageContext.request.contextPath}/staff/customer/list"
          class="nav-item"
        >
          <i class="fas fa-user-friends"></i>Customer List
        </a>
        <a
          href="${pageContext.request.contextPath}/staff/employee/list"
          class="nav-item"
        >
          <i class="fas fa-id-badge"></i>Employee List
        </a>
      </div>
    </details>
    <details class="nav-dropdown">
      <summary>
        <span class="summary-content">
          <i class="fas fa-calendar-check"></i>Appointments
        </span>
      </summary>
      <div class="nav-dropdown-content">
        <a
          href="${pageContext.request.contextPath}/staff/appointment/new"
          class="nav-item"
        >
          <i class="fas fa-plus-circle"></i>Create Appointment
        </a>
        <a
          href="${pageContext.request.contextPath}/staff/appointment/list"
          class="nav-item"
        >
          <i class="fas fa-calendar"></i>Appointment Calendar
        </a>
        <a
          href="${pageContext.request.contextPath}/staff/appointment/request"
          class="nav-item"
        >
          <i class="fas fa-clock"></i>Appointment Request
        </a>
        <a
          href="${pageContext.request.contextPath}/staff/appointment/payment"
          class="nav-item"
        >
          <i class="fas fa-credit-card"></i>Appointment Payment
        </a>
      </div>
    </details>
    <a
      href="${pageContext.request.contextPath}/staff/medicine/list"
      class="nav-item"
    >
      <i class="fas fa-pills"></i>Medicine List
    </a>

    <% } else if ("DOCTOR".equalsIgnoreCase(role)) { %>

    <a
      href="${pageContext.request.contextPath}/doctor/dashboard"
      class="nav-item"
    >
      <i class="fas fa-tachometer-alt"></i>Dashboard
    </a>
    <details class="nav-dropdown">
      <summary><i class="fas fa-calendar-check"></i>Appointments</summary>
      <div class="nav-dropdown-content">
        <a
          href="${pageContext.request.contextPath}/doctor/appointment/list"
          class="nav-item"
        >
          <i class="fas fa-list"></i>Appointment List
        </a>
        <a
          href="${pageContext.request.contextPath}/doctor/appointment/calendar"
          class="nav-item"
        >
          <i class="fas fa-calendar"></i>Appointment Calendar
        </a>
        <a
          href="${pageContext.request.contextPath}/doctor/appointment/list"
          class="nav-item"
        >
          <i class="fas fa-clock"></i>Current Appointment
        </a>
        <a
          href="${pageContext.request.contextPath}/doctor/appointment/list"
          class="nav-item"
        >
          <i class="fas fa-star"></i>Feedback
        </a>
        <a
          href="${pageContext.request.contextPath}/doctor/appointment/list"
          class="nav-item"
        >
          <i class="fas fa-dollar-sign"></i>Charge
        </a>
      </div>
    </details>
    <details class="nav-dropdown">
      <summary><i class="fas fa-user-injured"></i>Patients</summary>
      <div class="nav-dropdown-content">
        <a
          href="${pageContext.request.contextPath}/doctor/customer/list"
          class="nav-item"
        >
          <i class="fas fa-users"></i>Patient List
        </a>
        <a
          href="${pageContext.request.contextPath}/doctor/customer/medical-history"
          class="nav-item"
        >
          <i class="fas fa-receipt"></i>Check Payments
        </a>
      </div>
    </details>

    <% } else if ("CUSTOMER".equalsIgnoreCase(role)) { %>

    <a href="../customer/dashboard.jsp" class="nav-item">
      <i class="fas fa-tachometer-alt"></i>Dashboard
    </a>
    <details class="nav-dropdown">
      <summary><i class="fas fa-calendar-check"></i>My Appointments</summary>
      <div class="nav-dropdown-content">
        <a href="../appointments/request.jsp" class="nav-item">
          <i class="fas fa-plus-circle"></i>Request Appointment
        </a>
        <a href="../appointments/myAppointments.jsp" class="nav-item">
          <i class="fas fa-calendar"></i>My Appointments
        </a>
        <a href="../appointments/history.jsp" class="nav-item">
          <i class="fas fa-history"></i>Appointment History
        </a>
      </div>
    </details>
    <details class="nav-dropdown">
      <summary><i class="fas fa-comments"></i>Feedback</summary>
      <div class="nav-dropdown-content">
        <a href="${pageContext.request.contextPath}/customer/create-comment" class="nav-item">
          <i class="fas fa-comment-dots"></i>Comment to Staff/Doctor
        </a>
      </div>
    </details>

    <% } else { %>
    <!-- No session (guest) -->
    <a href="../public/dashboard.jsp" class="nav-item">
      <i class="fas fa-home"></i>Dashboard
    </a>
    <a href="../about.jsp" class="nav-item">
      <i class="fas fa-info-circle"></i>About
    </a>
    <a href="../login.jsp" class="nav-item">
      <i class="fas fa-sign-in-alt"></i>Login
    </a>
    <% } %>
  </div>

  <% if (!"GUEST".equalsIgnoreCase(role)) { %>
  <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
    <i class="fas fa-sign-out-alt"></i>Logout
  </a>
  <% } %>
</aside>

<script>
  // Auto-collapse on small screens
  window.addEventListener("resize", function () {
    const sidebar = document.getElementById("sidebar");
    const main = document.querySelector("main");

    if (window.innerWidth < 768) {
      sidebar.classList.add("collapsed");
      main.style.marginLeft = "40px";
    } else {
      sidebar.classList.remove("collapsed");
      main.style.marginLeft = "280px";
    }
  });

  // Initial check for small screens
  if (window.innerWidth < 768) {
    const sidebar = document.getElementById("sidebar");
    const main = document.querySelector("main");
    sidebar.classList.add("collapsed");
    main.style.marginLeft = "40px";
  }
</script>
