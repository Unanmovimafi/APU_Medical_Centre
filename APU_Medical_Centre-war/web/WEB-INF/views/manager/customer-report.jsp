<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <title>Customer Analytics</title>
  <style>
    body { font-family: system-ui, -apple-system, "Segoe UI", Roboto, Arial, sans-serif; color:#111827; }
    .grid { display:grid; gap:16px; grid-template-columns: repeat(auto-fit,minmax(260px,1fr)); padding:16px; }
    .card { border:1px solid #e5e7eb; border-radius:12px; box-shadow:0 2px 10px rgba(0,0,0,.04); padding:12px 14px; background:#fff; }
    .card h3 { font-size:14px; margin:4px 0 10px; font-weight:600; color:#374151; text-align:center; }
    .chart-wrap { width: 100%; display:flex; justify-content:center; }
    canvas { width:240px !important; height:240px !important; }
  </style>
</head>
<body>
  <div class="grid">
    <!-- Gender (Pie) -->
    <div class="card" role="group" aria-label="Gender chart for customers">
      <h3>Gender - Customers</h3>
      <div class="chart-wrap"><canvas id="chartGender"></canvas></div>
    </div>

    <!-- New Customers per Month (Line) -->
    <div class="card">
      <h3>New Customers - Last 12 Months</h3>
      <div class="chart-wrap"><canvas id="chartMonthly"></canvas></div>
    </div>

    <!-- Age Groups (Bar) -->
    <div class="card">
      <h3>Age Groups - Customers</h3>
      <div class="chart-wrap"><canvas id="chartAgeGroups"></canvas></div>
    </div>

    <!-- Blood Types (Doughnut) -->
    <div class="card">
      <h3>Blood Types - Customers</h3>
      <div class="chart-wrap"><canvas id="chartBlood"></canvas></div>
    </div>

    <!-- Status (Horizontal Bar) -->
    <div class="card">
      <h3>Status - Customers</h3>
      <div class="chart-wrap"><canvas id="chartStatus"></canvas></div>
    </div>
  </div>

  <script>
    // ---------- Gender ----------
    const male   = <c:out value="${maleCount}"   default="0" />;
    const female = <c:out value="${femaleCount}" default="0" />;
    const totalGender = Math.max(1, male + female);

    new Chart(document.getElementById('chartGender').getContext('2d'), {
      type: 'pie',
      data: {
        labels: ['Male', 'Female'],
        datasets: [{
          label: 'Customers by Gender',
          data: [male, female],
          backgroundColor: ['#36A2EB', '#FF6384']
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { position: 'bottom', labels: { usePointStyle:true, pointStyle:'circle', boxWidth:10, boxHeight:10, font:{size:12} } },
          tooltip: { callbacks: {
            label: (ctx) => {
              const val = ctx.parsed;
              const pct = (val / totalGender * 100).toFixed(1);
              return `${ctx.label}: ${val} (${pct}%)`;
            }
          }}
        }
      }
    });

    // ---------- Monthly ----------
    const monthlyLabels = [
      <c:forEach var="l" items="${monthlyLabels}" varStatus="s">${s.first ? "" : ","}'${fn:escapeXml(l)}'</c:forEach>
    ];
    const monthlyCounts = [
      <c:forEach var="n" items="${monthlyCounts}" varStatus="s">${s.first ? "" : ","}${n}</c:forEach>
    ];
    new Chart(document.getElementById('chartMonthly').getContext('2d'), {
      type: 'line',
      data: { labels: monthlyLabels, datasets: [{ label: 'New Customers', data: monthlyCounts, fill:false, tension:0.3 }] },
      options: { responsive:true, plugins:{ legend:{display:false} }, scales:{ y:{ beginAtZero:true, ticks:{ precision:0 } } } }
    });

    // ---------- Age Groups ----------
    const ageLabels = [
      <c:forEach var="l" items="${ageGroupLabels}" varStatus="s">${s.first ? "" : ","}'${fn:escapeXml(l)}'</c:forEach>
    ];
    const ageCounts = [
      <c:forEach var="n" items="${ageCounts}" varStatus="s">${s.first ? "" : ","}${n}</c:forEach>
    ];
    new Chart(document.getElementById('chartAgeGroups').getContext('2d'), {
      type: 'bar',
      data: { labels: ageLabels, datasets: [{ label: 'Customers', data: ageCounts }] },
      options: { responsive:true, plugins:{ legend:{display:false} }, scales:{ y:{ beginAtZero:true, ticks:{ precision:0 } } } }
    });

    // ---------- Blood Types ----------
    const bloodLabels = [
      <c:forEach var="l" items="${bloodLabels}" varStatus="s">${s.first ? "" : ","}'${fn:escapeXml(l)}'</c:forEach>
    ];
    const bloodCounts = [
      <c:forEach var="n" items="${bloodCounts}" varStatus="s">${s.first ? "" : ","}${n}</c:forEach>
    ];
    new Chart(document.getElementById('chartBlood').getContext('2d'), {
      type: 'doughnut',
      data: { labels: bloodLabels, datasets: [{ data: bloodCounts }] },
      options: {
        responsive:true,
        plugins:{
          legend:{ position:'bottom' },
          tooltip:{ callbacks:{ label:(ctx)=>{
            const total = bloodCounts.reduce((a,b)=>a+b,0) || 1;
            const val = ctx.parsed;
            const pct = (val/total*100).toFixed(1);
            return `${ctx.label}: ${val} (${pct}%)`;
          }}}
        },
        cutout:'55%'
      }
    });

    // ---------- Status ----------
    const statusLabels = [
      <c:forEach var="l" items="${statusLabels}" varStatus="s">${s.first ? "" : ","}'${fn:escapeXml(l)}'</c:forEach>
    ];
    const statusCounts = [
      <c:forEach var="n" items="${statusCounts}" varStatus="s">${s.first ? "" : ","}${n}</c:forEach>
    ];
    new Chart(document.getElementById('chartStatus').getContext('2d'), {
      type: 'bar',
      data: { labels: statusLabels, datasets: [{ label: 'Customers', data: statusCounts }] },
      options: {
        indexAxis: 'y',
        responsive: true,
        plugins: { legend:{ display:false } },
        scales: { x:{ beginAtZero:true, ticks:{ precision:0 } } }
      }
    });
  </script>
</body>
</html>
