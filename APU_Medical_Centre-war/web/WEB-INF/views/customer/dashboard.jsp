<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>
<link
  href="https://fonts.googleapis.com/icon?family=Material+Icons"
  rel="stylesheet"
/>

<style>
  .mainbody {
    font-family: Arial, sans-serif;
    margin: 20px;
    background-color: #f8f9fa;
    padding: 25px;
  }

  .dashboard-header {
    color: #00bfff;
    margin-bottom: 30px;
    font-size: 2rem;
    font-weight: bold;
  }

  .stats-container {
    display: flex;
    gap: 20px;
    margin-bottom: 40px;
    flex-wrap: wrap;
  }

  .stat-card {
    background: linear-gradient(135deg, #00bfff 0%, #33c9e7 100%);
    color: white;
    padding: 25px;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 191, 255, 0.3);
    flex: 1;
    min-width: 250px;
    transition: transform 0.2s ease, box-shadow 0.2s ease;
  }

  .stat-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 20px rgba(0, 191, 255, 0.4);
  }

  .stat-card .icon {
    font-size: 2.5rem;
    margin-bottom: 10px;
    opacity: 0.9;
  }

  .stat-card .number {
    font-size: 2.5rem;
    font-weight: bold;
    margin-bottom: 5px;
  }

  .stat-card .label {
    font-size: 1rem;
    opacity: 0.9;
  }

  .content-sections {
    display: flex;
    gap: 30px;
    flex-wrap: wrap;
  }

  .section {
    background: white;
    border-radius: 12px;
    padding: 25px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    flex: 1;
    min-width: 400px;
  }

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    border-bottom: 2px solid #e9ecef;
    padding-bottom: 15px;
  }

  .section-title {
    color: #00bfff;
    font-size: 1.3rem;
    font-weight: bold;
    display: flex;
    align-items: center;
    gap: 10px;
  }

  .section-count {
    background: #00bfff;
    color: white;
    padding: 5px 12px;
    border-radius: 20px;
    font-size: 0.9rem;
    font-weight: bold;
  }

  .arrow-link {
    background: #00bfff;
    color: white;
    padding: 8px 15px;
    border-radius: 8px;
    text-decoration: none;
    display: flex;
    align-items: center;
    gap: 5px;
    transition: background-color 0.2s ease;
  }

  .arrow-link:hover {
    background: #00acc1;
    text-decoration: none;
    color: white;
  }

  .appointment-list {
    list-style: none;
    padding: 0;
    margin: 0;
  }

  .appointment-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
    margin-bottom: 10px;
    border: 1px solid #e9ecef;
    border-radius: 8px;
    transition: background-color 0.2s ease;
  }

  .appointment-item:hover {
    background-color: #f8f9fa;
  }

  .appointment-info {
    flex: 1;
  }

  .doctor-name {
    font-weight: bold;
    color: #333;
    margin-bottom: 5px;
  }

  .appointment-details {
    color: #666;
    font-size: 0.9rem;
  }

  .appointment-time {
    color: #00bfff;
    font-weight: bold;
    font-size: 0.9rem;
  }

  .status-badge {
    padding: 5px 10px;
    border-radius: 15px;
    font-size: 0.8rem;
    font-weight: bold;
  }

  .status-pending {
    background: #fff3cd;
    color: #856404;
  }

  .status-approved {
    background: #d1ecf1;
    color: #0c5460;
  }

  .status-paid {
    background: #d4edda;
    color: #155724;
  }

  .status-finished {
    background: #f8d7da;
    color: #721c24;
  }

  .no-items {
    text-align: center;
    padding: 40px 20px;
    color: #6c757d;
    font-style: italic;
  }

  .no-items .material-icons {
    font-size: 3rem;
    opacity: 0.3;
    margin-bottom: 10px;
  }

  @media (max-width: 768px) {
    .stats-container {
      flex-direction: column;
    }

    .content-sections {
      flex-direction: column;
    }

    .section {
      min-width: auto;
    }
  }
</style>

<div class="mainbody">
  <h1 class="dashboard-header">
    <span class="material-icons" style="font-size: 2rem; vertical-align: middle"
      >dashboard</span
    >
    My Dashboard
  </h1>

  <!-- Statistics Cards -->
  <div class="stats-container">
    <div class="stat-card">
      <div class="icon">
        <span class="material-icons">event</span>
      </div>
      <div class="number">${totalAppointments}</div>
      <div class="label">Total Appointments</div>
    </div>

    <div class="stat-card">
      <div class="icon">
        <span class="material-icons">pending</span>
      </div>
      <div class="number">${pendingAppointments}</div>
      <div class="label">Pending Appointments</div>
    </div>

    <div class="stat-card">
      <div class="icon">
        <span class="material-icons">comment</span>
      </div>
      <div class="number">${totalComments}</div>
      <div class="label">Total Comments</div>
    </div>
  </div>

  <!-- Content Sections -->
  <div class="content-sections">
    <!-- Latest 5 Appointments -->
    <div class="section">
      <div class="section-header">
        <h3 class="section-title">
          <span class="material-icons">history</span>
          Latest Appointments
          <span class="section-count">5</span>
        </h3>
        <a
          href="${pageContext.request.contextPath}/customer/appointment/calendar"
          class="arrow-link"
        >
          View All
          <span class="material-icons">arrow_forward</span>
        </a>
      </div>

      <c:choose>
        <c:when test="${not empty latestAppointments}">
          <ul class="appointment-list">
            <c:forEach
              var="appointment"
              items="${latestAppointments}"
              varStatus="status"
            >
              <li class="appointment-item">
                <div class="appointment-info">
                  <div class="doctor-name">Dr. ${appointment.doctor.name}</div>
                  <div class="appointment-details">
                    <fmt:formatDate
                      value="${appointment.appointmentStartDatetime}"
                      pattern="dd/MM/yyyy 'at' HH:mm"
                    />
                  </div>
                  <div class="appointment-details">
                    Status: ${appointment.status}
                  </div>
                </div>
                <div>
                  <c:choose>
                    <c:when test="${appointment.status == 'PENDING'}">
                      <span class="status-badge status-pending">PENDING</span>
                    </c:when>
                    <c:when test="${appointment.status == 'APPROVED'}">
                      <span class="status-badge status-approved">APPROVED</span>
                    </c:when>
                    <c:when test="${appointment.status == 'PAID'}">
                      <span class="status-badge status-paid">PAID</span>
                    </c:when>
                    <c:when test="${appointment.status == 'FINISHED'}">
                      <span class="status-badge status-finished">FINISHED</span>
                    </c:when>
                    <c:otherwise>
                      <span class="status-badge status-approved"
                        >${appointment.status}</span
                      >
                    </c:otherwise>
                  </c:choose>
                </div>
              </li>
            </c:forEach>
          </ul>
        </c:when>
        <c:otherwise>
          <div class="no-items">
            <div>
              <span class="material-icons">event_busy</span>
            </div>
            <p>No appointments found</p>
          </div>
        </c:otherwise>
      </c:choose>
    </div>

    <!-- Today's Appointments -->
    <div class="section">
      <div class="section-header">
        <h3 class="section-title">
          <span class="material-icons">today</span>
          Today's Appointments
          <span class="section-count">${todayAppointmentsCount}</span>
        </h3>
        <a
          href="${pageContext.request.contextPath}/customer/appointment/calendar"
          class="arrow-link"
        >
          View Calendar
          <span class="material-icons">calendar_month</span>
        </a>
      </div>

      <c:choose>
        <c:when test="${not empty todayAppointments}">
          <ul class="appointment-list">
            <c:forEach
              var="appointment"
              items="${todayAppointments}"
              varStatus="status"
            >
              <li class="appointment-item">
                <div class="appointment-info">
                  <div class="doctor-name">
                    ${status.index + 1}. Dr. ${appointment.doctor.name}
                  </div>
                  <div class="appointment-details">
                    Status: ${appointment.status}
                  </div>
                </div>
                <div class="appointment-time">
                  <fmt:formatDate
                    value="${appointment.appointmentStartDatetime}"
                    pattern="HH:mm"
                  />
                </div>
              </li>
            </c:forEach>
          </ul>
        </c:when>
        <c:otherwise>
          <div class="no-items">
            <div>
              <span class="material-icons">event_available</span>
            </div>
            <p>No appointments scheduled for today</p>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
