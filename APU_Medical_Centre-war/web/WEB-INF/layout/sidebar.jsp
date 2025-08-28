<%@ page import="model.manager.Manager" %> <%@ page
import="model.counterstaff.CounterStaff" %> <%@ page
import="model.customer.Customer" %> <%@ page import="model.doctor.Doctor" %> <%
Manager managerSession = (Manager) session.getAttribute("managerSession");
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
    <% String profileImg =
    "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/2048px-User-avatar.svg.png";
    if (managerSession != null && managerSession.getProfilePicture() != null &&
    !managerSession.getProfilePicture().isEmpty()) { profileImg =
    managerSession.getProfilePicture(); } else if (staffSession != null &&
    staffSession.getProfilePicture() != null &&
    !staffSession.getProfilePicture().isEmpty()) { profileImg =
    staffSession.getProfilePicture(); } else if (doctorSession != null &&
    doctorSession.getProfilePicture() != null &&
    !doctorSession.getProfilePicture().isEmpty()) { profileImg =
    doctorSession.getProfilePicture(); } else if (customerSession != null &&
    customerSession.getProfilePicture() != null &&
    !customerSession.getProfilePicture().isEmpty()) { profileImg =
    customerSession.getProfilePicture(); } %>
    <img src="<%= profileImg %>" alt="Profile" class="profile-img" />
    <h3><%= displayName %></h3>
    <span class="role-badge"><%= role.replace("_", " ") %></span>
  </div>

  <div class="nav-menu">
    <% if ("MANAGER".equalsIgnoreCase(role)) { %>
    <a
      href="${pageContext.request.contextPath}/manager/dashboard"
      class="nav-item"
    >
      <i class="fas fa-tachometer-alt"></i><span>Dashboard</span>
    </a>
    <a
      href="${pageContext.request.contextPath}/manager/edit-profile"
      class="nav-item"
    >
      <i class="fas fa-user-cog"></i><span>Edit Profile</span>
    </a>
    <a
      href="${pageContext.request.contextPath}/manager/employee/list"
      class="nav-item"
    >
      <i class="fas fa-id-badge"></i><span>Employee List</span>
    </a>
    <details class="nav-dropdown">
      <summary>
        <span class="summary-content">
          <i class="fas fa-calendar-check"></i><span>Appointments</span>
        </span>
      </summary>
      <div class="nav-dropdown-content">
        <a
          href="${pageContext.request.contextPath}/manager/appointment/list"
          class="nav-item"
        >
          <i class="fas fa-calendar"></i><span>Calendar</span>
        </a>
        <a
          href="${pageContext.request.contextPath}/manager/appointment/comment"
          class="nav-item"
        >
          <i class="fas fa-comments"></i><span>Comments</span>
        </a>
      </div>
    </details>
    <details class="nav-dropdown">
      <summary>
        <span class="summary-content">
          <i class="fas fa-chart-bar"></i><span>Reports</span>
        </span>
      </summary>
      <div class="nav-dropdown-content">
        <a
          href="${pageContext.request.contextPath}/manager/customer/report"
          class="nav-item"
        >
          <i class="fas fa-chart-line"></i><span>Customer Report</span>
        </a>
      </div>
    </details>

    <% } else if ("COUNTER_STAFF".equalsIgnoreCase(role)) { %>
    <a
      href="${pageContext.request.contextPath}/staff/dashboard"
      class="nav-item"
    >
      <i class="fas fa-tachometer-alt"></i><span>Dashboard</span>
    </a>
    <a
      href="${pageContext.request.contextPath}/staff/edit-profile"
      class="nav-item"
    >
      <i class="fas fa-user-cog"></i><span>Edit Profile</span>
    </a>
    <a
      href="${pageContext.request.contextPath}/staff/customer/list"
      class="nav-item"
    >
      <i class="fas fa-user-friends"></i><span>Customer</span>
    </a>
    <details class="nav-dropdown">
      <summary>
        <span class="summary-content">
          <i class="fas fa-calendar-check"></i><span>Appointments</span>
        </span>
      </summary>
      <div class="nav-dropdown-content">
        <a
          href="${pageContext.request.contextPath}/staff/appointment/new"
          class="nav-item"
        >
          <i class="fas fa-plus-circle"></i><span>Create Appointment</span>
        </a>
        <a
          href="${pageContext.request.contextPath}/staff/appointment/list"
          class="nav-item"
        >
          <i class="fas fa-calendar"></i><span>Appointment Calendar</span>
        </a>
        <a
          href="${pageContext.request.contextPath}/staff/appointment/request"
          class="nav-item"
        >
          <i class="fas fa-clock"></i><span>Appointment Request</span>
        </a>
        <a
          href="${pageContext.request.contextPath}/staff/appointment/payment"
          class="nav-item"
        >
          <i class="fas fa-credit-card"></i><span>Appointment Payment</span>
        </a>
        <a
          href="${pageContext.request.contextPath}/staff/appointment/comment"
          class="nav-item"
        >
          <i class="fas fa-comments"></i><span>Comments</span>
        </a>
      </div>
    </details>
    <a
      href="${pageContext.request.contextPath}/staff/medicine/list"
      class="nav-item"
    >
      <i class="fas fa-pills"></i><span>Medicine</span>
    </a>

    <% } else if ("DOCTOR".equalsIgnoreCase(role)) { %>
    <a
      href="${pageContext.request.contextPath}/doctor/dashboard"
      class="nav-item"
    >
      <i class="fas fa-tachometer-alt"></i><span>Dashboard</span>
    </a>
    <a
      href="${pageContext.request.contextPath}/doctor/edit-profile"
      class="nav-item"
    >
      <i class="fas fa-user-cog"></i><span>Edit Profile</span>
    </a>
    <details class="nav-dropdown">
      <summary>
        <span class="summary-content">
          <i class="fas fa-calendar-check"></i><span>Appointments</span>
        </span>
      </summary>
      <div class="nav-dropdown-content">
        <a
          href="${pageContext.request.contextPath}/doctor/appointment/list"
          class="nav-item"
        >
          <i class="fas fa-calendar"></i><span>Appointment Calendar</span>
        </a>
        <a
          href="${pageContext.request.contextPath}/doctor/appointment/comment"
          class="nav-item"
        >
          <i class="fas fa-clock"></i><span>Comment</span>
        </a>
      </div>
    </details>
    <a
      href="${pageContext.request.contextPath}/doctor/customer/list"
      class="nav-item"
    >
      <i class="fas fa-user-injured"></i><span>Patient</span>
    </a>

    <% } else if ("CUSTOMER".equalsIgnoreCase(role)) { %>
    <a
      href="${pageContext.request.contextPath}/customer/dashboard"
      class="nav-item"
    >
      <i class="fas fa-tachometer-alt"></i><span>Dashboard</span>
    </a>
    <a
      href="${pageContext.request.contextPath}/customer/edit-profile"
      class="nav-item"
    >
      <i class="fas fa-user-cog"></i><span>Edit Profile</span>
    </a>
    <details class="nav-dropdown">
      <summary>
        <span class="summary-content">
          <i class="fas fa-calendar-check"></i><span>My Appointments</span>
        </span>
      </summary>
      <div class="nav-dropdown-content">
        <a
          href="${pageContext.request.contextPath}/customer/appointment/request"
          class="nav-item"
        >
          <i class="fas fa-plus-circle"></i><span>Request Appointment</span>
        </a>
        <a
          href="${pageContext.request.contextPath}/customer/appointment/calendar"
          class="nav-item"
        >
          <i class="fas fa-calendar"></i><span>Calendar</span>
        </a>
        <a
          href="${pageContext.request.contextPath}/customer/appointment/list"
          class="nav-item"
        >
          <i class="fas fa-history"></i><span>Appointment History</span>
        </a>
      </div>
    </details>
    <a
      href="${pageContext.request.contextPath}/customer/comment/new"
      class="nav-item"
    >
      <i class="fas fa-comment-dots" style="margin-right: 10px"></i
      ><span>Comment to Staff/Doctor</span>
    </a>

    <% } else { %>
    <!-- No session (guest) -->
    <a href="../public/dashboard.jsp" class="nav-item">
      <i class="fas fa-home"></i><span>Dashboard</span>
    </a>
    <a href="../about.jsp" class="nav-item">
      <i class="fas fa-info-circle"></i><span>About</span>
    </a>
    <a href="../login.jsp" class="nav-item">
      <i class="fas fa-sign-in-alt"></i><span>Login</span>
    </a>
    <% } %>
  </div>

  <% if (!"GUEST".equalsIgnoreCase(role)) { %>
  <a href="${pageContext.request.contextPath}/logout" class="logout-btn">
    <i class="fas fa-sign-out-alt" style="margin-right: 10px"></i
    ><span>Logout</span>
  </a>
  <% } %>
</aside>

<script>
  function toggleSidebar() {
    const sidebar = document.getElementById("sidebar");
    const main = document.querySelector("main");

    if (sidebar.classList.contains("collapsed")) {
      // Open sidebar
      sidebar.classList.remove("collapsed");
      if (main) {
        main.style.marginLeft = "280px";
      }
    } else {
      // Close sidebar
      sidebar.classList.add("collapsed");
      if (main) {
        main.style.marginLeft = "60px"; // Match collapsed width
      }
    }
  }

  // Auto-collapse on small screens
  window.addEventListener("resize", function () {
    const sidebar = document.getElementById("sidebar");
    const main = document.querySelector("main");

    if (window.innerWidth < 768) {
      sidebar.classList.add("collapsed");
      if (main) {
        main.style.marginLeft = "60px"; // Match collapsed width
      }
    } else {
      sidebar.classList.remove("collapsed");
      if (main) {
        main.style.marginLeft = "280px";
      }
    }
  });

  // Initial check for small screens
  if (window.innerWidth < 768) {
    const sidebar = document.getElementById("sidebar");
    const main = document.querySelector("main");
    sidebar.classList.add("collapsed");
    if (main) {
      main.style.marginLeft = "60px"; // Match collapsed width
    }
  }
</script>
