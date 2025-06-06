<aside id="sidebar">
  <button class="toggle-btn" onclick="toggleSidebar()">?</button>
  <img src="../assets/img/default-profile.png" alt="Profile" class="profile-img" />
  <h3>John Doe</h3>
  <a href="../profile/editProfile.jsp">Edit Profile</a>
  <a href="../dashboard/dashboard.jsp">Dashboard</a>

  <details>
    <summary>Medical Records</summary>
    <div style="margin-left: 10px;">
      <a href="../records/studentRecords.jsp">Student Records</a>
      <a href="../records/immunizations.jsp">Immunizations</a>
    </div>
  </details>

  <details>
    <summary>Appointments</summary>
    <div style="margin-left: 10px;">
      <a href="../appointments/bookAppointment.jsp">Book Appointment</a>
      <a href="../appointments/viewAppointments.jsp">My Appointments</a>
    </div>
  </details>
</aside>
