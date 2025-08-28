<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>
<link
  href="https://fonts.googleapis.com/icon?family=Material+Icons"
  rel="stylesheet"
/>

<style>
  .mainbody {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 20px;
  }

  h2 {
    color: #00bfff;
    margin-bottom: 25px;
    text-align: center;
  }

  .calendar-header {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 20px;
    background: white;
    padding: 15px 20px;
    border-radius: 12px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  }

  .month-navigation {
    display: flex;
    align-items: center;
    gap: 15px;
  }

  .nav-btn {
    background-color: #00bfff;
    color: white;
    border: none;
    padding: 8px 12px;
    border-radius: 8px;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.2s ease;
  }

  .nav-btn:hover {
    background-color: #00acc1;
  }

  .current-month {
    font-size: 20px;
    font-weight: bold;
    color: #00bfff;
    margin: 0 20px;
  }

  .calendar-container {
    background: white;
    border-radius: 12px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    overflow: hidden;
  }

  .calendar {
    width: 100%;
    border-collapse: collapse;
  }

  .calendar-day-header {
    background-color: #e8fafd;
    color: #1c1c1c;
    padding: 15px 8px;
    text-align: center;
    font-weight: bold;
    border-bottom: 1px solid #ddd;
  }

  .calendar-day {
    width: 14.28%;
    height: 120px;
    padding: 8px;
    border: 1px solid #eee;
    vertical-align: top;
    cursor: pointer;
    transition: background-color 0.2s ease;
    position: relative;
  }

  .calendar-day:hover {
    background-color: #f0f8ff;
  }

  .calendar-day.other-month {
    color: #ccc;
    background-color: #f8f9fa;
  }

  .calendar-day.today {
    background-color: #e8fafd;
    border: 2px solid #00bfff;
  }

  .day-number {
    font-weight: bold;
    margin-bottom: 4px;
    color: #1c1c1c;
  }

  .appointment-item {
    font-size: 10px;
    padding: 2px 4px;
    margin: 1px 0;
    border-radius: 3px;
    color: white;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    cursor: pointer;
  }

  .status-pending {
    background-color: #ffc107;
    color: #856404;
  }
  .status-approved {
    background-color: #28a745;
    color: white;
  }
  .status-rejected {
    background-color: #dc3545;
    color: white;
  }
  .status-completed {
    background-color: #007bff;
    color: white;
  }
  .status-finished {
    background-color: #007bff;
    color: white;
  }
  .status-cancelled {
    background-color: #6c757d;
    color: white;
  }
  .status-waiting-payment {
    background-color: #fd7e14;
    color: white;
  }
  .status-paid {
    background-color: #20c997;
    color: white;
  }

  .appointment-count {
    position: absolute;
    top: 5px;
    right: 5px;
    background: #00bfff;
    color: white;
    border-radius: 50%;
    width: 20px;
    height: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 11px;
    font-weight: bold;
  }

  /* Day Details Modal */
  .modal {
    display: none;
    position: fixed;
    z-index: 10000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
  }

  .modal-content {
    background-color: #f2fcfd;
    margin: 5% auto;
    padding: 0;
    width: 90%;
    max-width: 1000px;
    border-radius: 12px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    max-height: 80vh;
    overflow-y: auto;
  }

  .modal-header {
    background-color: #00bfff;
    color: white;
    padding: 20px;
    border-radius: 12px 12px 0 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .modal-close {
    background: none;
    border: none;
    color: white;
    font-size: 24px;
    cursor: pointer;
  }

  .modal-body {
    padding: 20px;
  }

  .day-appointments-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
  }

  .day-appointments-table th {
    background-color: #e8fafd;
    color: #1c1c1c;
    padding: 12px;
    text-align: left;
    border-bottom: 1px solid #ddd;
  }

  .day-appointments-table td {
    padding: 12px;
    border-bottom: 1px solid #eee;
    color: #1c1c1c;
  }

  .day-appointments-table tr:hover {
    background-color: #f0f8ff;
  }

  .status-badge {
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 12px;
    font-weight: bold;
  }

  .legend {
    display: flex;
    flex-wrap: wrap;
    gap: 15px;
    margin-bottom: 20px;
    padding: 15px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  }

  .legend-item {
    display: flex;
    align-items: center;
    gap: 5px;
    font-size: 12px;
  }

  .legend-color {
    width: 15px;
    height: 15px;
    border-radius: 3px;
  }
</style>

<div class="mainbody">
  <h2>Appointment Calendar</h2>

  <!-- Calendar Header -->
  <div class="calendar-header">
    <div class="month-navigation">
      <button class="nav-btn" onclick="changeMonth(-1)">
        <span class="material-icons" style="font-size: 16px">chevron_left</span>
      </button>
      <div class="current-month" id="currentMonth">
        <fmt:formatDate value="${currentDate}" pattern="MMMM yyyy" />
      </div>
      <button class="nav-btn" onclick="changeMonth(1)">
        <span class="material-icons" style="font-size: 16px"
          >chevron_right</span
        >
      </button>
    </div>
  </div>

  <!-- Status Legend -->
  <div class="legend">
    <div class="legend-item">
      <div class="legend-color status-pending"></div>
      <span>Pending</span>
    </div>
    <div class="legend-item">
      <div class="legend-color status-approved"></div>
      <span>Approved</span>
    </div>
    <div class="legend-item">
      <div class="legend-color status-waiting-payment"></div>
      <span>Waiting Payment</span>
    </div>
    <div class="legend-item">
      <div class="legend-color status-paid"></div>
      <span>Paid</span>
    </div>
    <div class="legend-item">
      <div class="legend-color status-completed"></div>
      <span>Finished</span>
    </div>
    <div class="legend-item">
      <div class="legend-color status-rejected"></div>
      <span>Rejected</span>
    </div>
    <div class="legend-item">
      <div class="legend-color status-cancelled"></div>
      <span>Cancelled</span>
    </div>
  </div>

  <!-- Calendar View -->
  <div id="calendarView" class="calendar-container">
    <table class="calendar">
      <thead>
        <tr>
          <th class="calendar-day-header">Sun</th>
          <th class="calendar-day-header">Mon</th>
          <th class="calendar-day-header">Tue</th>
          <th class="calendar-day-header">Wed</th>
          <th class="calendar-day-header">Thu</th>
          <th class="calendar-day-header">Fri</th>
          <th class="calendar-day-header">Sat</th>
        </tr>
      </thead>
      <tbody id="calendarBody">
        <!-- Calendar days will be generated by JavaScript -->
      </tbody>
    </table>
  </div>
</div>

<!-- Day Details Modal -->
<div id="dayModal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <h3 id="modalDate">Appointments for Selected Date</h3>
      <button class="modal-close" onclick="closeDayModal()">&times;</button>
    </div>
    <div class="modal-body">
      <div id="dayAppointments">
        <!-- Day appointments will be loaded here -->
      </div>
    </div>
  </div>
</div>

<script>
  // Get URL parameters from server
  var serverYear = (
    <c:out value="${not empty targetYear ? targetYear : (not empty param.year ? param.year : 2025)}" />
  );
  var serverMonth = (
    <c:out value="${not empty targetMonth ? targetMonth : (not empty param.month ? param.month : 8)}" />
  );
</script>

<script>
  // Calendar data from server
  const appointments = [
      <c:forEach var="appt" items="${appointmentList}" varStatus="status">
      {
          id: ${appt.id},
          date: '<fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="yyyy-MM-dd" />',
          timeStart: '<fmt:formatDate value="${appt.appointmentStartDatetime}" pattern="HH:mm" />',
          timeEnd: '<fmt:formatDate value="${appt.appointmentEndDatetime}" pattern="HH:mm" />',
          doctor: '<c:choose><c:when test="${not empty appt.doctor and not empty appt.doctor.name}">${appt.doctor.name}</c:when><c:otherwise>N/A</c:otherwise></c:choose>',
          customer: '<c:choose><c:when test="${not empty appt.customer and not empty appt.customer.name}">${appt.customer.name}</c:when><c:otherwise>N/A</c:otherwise></c:choose>',
          status: '<c:choose><c:when test="${not empty appt.status}">${appt.status}</c:when><c:otherwise>N/A</c:otherwise></c:choose>',
          charge: '<c:choose><c:when test="${not empty appt.charge}">${appt.charge}</c:when><c:otherwise>0</c:otherwise></c:choose>'
      }<c:if test="${!status.last}">,</c:if>
      </c:forEach>
  ];

  // Get the current date from server URL parameters
  let currentYear = serverYear;
  let currentMonth = serverMonth - 1; // JavaScript months are 0-based

  function generateCalendar(year, month) {
      const firstDay = new Date(year, month, 1);
      const lastDay = new Date(year, month + 1, 0);
      const daysInMonth = lastDay.getDate();
      const startingDayOfWeek = firstDay.getDay();

      const calendarBody = document.getElementById('calendarBody');
      calendarBody.innerHTML = '';

      let date = 1;

      // Generate calendar weeks
      for (let week = 0; week < 6; week++) {
          const row = document.createElement('tr');

          for (let day = 0; day < 7; day++) {
              const cell = document.createElement('td');
              cell.className = 'calendar-day';

              if (week === 0 && day < startingDayOfWeek) {
                  // Previous month days
                  const prevMonthDate = new Date(year, month, 0).getDate() - (startingDayOfWeek - day - 1);
                  cell.innerHTML = '<div class="day-number">' + prevMonthDate + '</div>';
                  cell.classList.add('other-month');
              } else if (date > daysInMonth) {
                  // Next month days
                  const nextMonthDate = date - daysInMonth;
                  cell.innerHTML = '<div class="day-number">' + nextMonthDate + '</div>';
                  cell.classList.add('other-month');
                  date++;
              } else {
                  // Current month days
                  const cellDate = new Date(year, month, date);
                  const dateStr = formatDate(cellDate);
                  const dayAppointments = appointments.filter(appt => appt.date === dateStr);

                  cell.innerHTML = '<div class="day-number">' + date + '</div>';

                  // Add today class
                  const today = new Date();
                  if (year === today.getFullYear() && month === today.getMonth() && date === today.getDate()) {
                      cell.classList.add('today');
                  }

                  // Add appointments
                  if (dayAppointments.length > 0) {
                      cell.innerHTML += '<div class="appointment-count">' + dayAppointments.length + '</div>';

                      // Show first 3 appointments
                      dayAppointments.slice(0, 3).forEach(appt => {
                          const statusClass = 'status-' + appt.status.toLowerCase().replace(' ', '-');
                          const appointmentDiv = document.createElement('div');
                          appointmentDiv.className = 'appointment-item ' + statusClass;
                          appointmentDiv.innerHTML = appt.timeStart + ' - ' + appt.customer.substring(0, 8) + (appt.customer.length > 8 ? '...' : '');
                          cell.appendChild(appointmentDiv);
                      });
                  }

                  // Add click handler
                  cell.onclick = () => showDayDetails(dateStr, dayAppointments);

                  date++;
              }

              row.appendChild(cell);
          }

          calendarBody.appendChild(row);

          if (date > daysInMonth && week > 3) break;
      }

      // Update month display
      const monthNames = ['January', 'February', 'March', 'April', 'May', 'June',
                         'July', 'August', 'September', 'October', 'November', 'December'];
      document.getElementById('currentMonth').textContent = monthNames[month] + ' ' + year;
  }

  function formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return year + '-' + month + '-' + day;
  }

  function changeMonth(delta) {
      currentMonth += delta;
      if (currentMonth > 11) {
          currentMonth = 0;
          currentYear++;
      } else if (currentMonth < 0) {
          currentMonth = 11;
          currentYear--;
      }

      // Reload page with new month
      const newDate = new Date(currentYear, currentMonth, 1);
      const year = newDate.getFullYear();
      const month = String(newDate.getMonth() + 1).padStart(2, '0');
      window.location.href = '${pageContext.request.contextPath}/staff/appointment/list?year=' + year + '&month=' + month;
  }

  function showDayDetails(dateStr, dayAppointments) {
      const modal = document.getElementById('dayModal');
      const modalDate = document.getElementById('modalDate');
      const dayAppointmentsDiv = document.getElementById('dayAppointments');

      // Format date for display
      const date = new Date(dateStr + 'T00:00:00');
      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
      modalDate.textContent = 'Appointments for ' + date.toLocaleDateString('en-US', options);

      // Generate appointments table
      if (dayAppointments.length === 0) {
          dayAppointmentsDiv.innerHTML = '<p style="text-align: center; color: #666; padding: 20px;">No appointments scheduled for this day.</p>';
      } else {
          let tableHTML = '<table class="day-appointments-table">' +
              '<thead>' +
                  '<tr>' +
                      '<th>Time</th>' +
                      '<th>Doctor</th>' +
                      '<th>Customer</th>' +
                      '<th>Status</th>' +
                      '<th>Charge</th>' +
                      '<th>Action</th>' +
                  '</tr>' +
              '</thead>' +
              '<tbody>';

          dayAppointments.forEach(appt => {
              const statusClass = 'status-' + (appt.status ? appt.status.toLowerCase().replace(' ', '-') : 'unknown');
              const showCharge = appt.status === 'WAITING PAYMENT' || appt.status === 'PAID' || appt.status === 'FINISHED';

              tableHTML += '<tr>' +
                  '<td>' + (appt.timeStart && appt.timeEnd ? appt.timeStart + ' - ' + appt.timeEnd : 'N/A') + '</td>' +
                  '<td>' + (appt.doctor === false ? 'N/A' : (appt.doctor || 'N/A')) + '</td>' +
                  '<td>' + (appt.customer === false ? 'N/A' : (appt.customer || 'N/A')) + '</td>' +
                  '<td><span class="appointment-item ' + statusClass + '">' + (appt.status === false ? 'N/A' : (appt.status || 'N/A')) + '</span></td>' +
                  '<td>' + (showCharge ? '$' + (appt.charge === false ? '0' : (appt.charge || '0')) : '-') + '</td>' +
                  '<td><a href="${pageContext.request.contextPath}/staff/appointment/detail?id=' + appt.id + '" style="color: #00BFFF; text-decoration: none;">View Details</a></td>' +
                  '</tr>';
          });          tableHTML += '</tbody></table>';
          dayAppointmentsDiv.innerHTML = tableHTML;
      }

      modal.style.display = 'block';
  }

  function closeDayModal() {
      document.getElementById('dayModal').style.display = 'none';
  }

  // Close modal when clicking outside
  window.onclick = function(event) {
      const modal = document.getElementById('dayModal');
      if (event.target === modal) {
          closeDayModal();
      }
  }

  // Initialize calendar
  document.addEventListener('DOMContentLoaded', function() {
      generateCalendar(currentYear, currentMonth);
  });
</script>
