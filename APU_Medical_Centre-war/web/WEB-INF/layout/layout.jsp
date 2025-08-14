<%-- Document : layout Created on : 11 Jun 2025, 1:45:38?am Author : khong --%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Dashboard - APU Medical Centre</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/style.css"
    />

    <!-- Placeholder for page-specific CSS/JS -->
    <c:if test="${not empty pageCss}">
      <link rel="stylesheet" href="${pageCss}" />
    </c:if>
    <c:if test="${not empty pageJs}">
      <script src="${pageJs}"></script>
    </c:if>
    <script>
      function toggleSidebar() {
        const sidebar = document.getElementById("sidebar");
        const main = document.querySelector("main");
        const toggleBtn = sidebar.querySelector(".toggle-btn");
        const icon = toggleBtn.querySelector("i");

        sidebar.classList.toggle("collapsed");

        // Adjust main content margin and icon when sidebar is toggled
        if (sidebar.classList.contains("collapsed")) {
          main.style.marginLeft = "40px"; // Space for just the toggle button
          icon.className = "fas fa-arrow-right";
        } else {
          main.style.marginLeft = "280px"; // Full sidebar width
          icon.className = "fas fa-bars";
        }
      }
    </script>
  </head>
  <body>
    <div class="layout">
      <%@ include file="sidebar.jsp" %>

      <main>
        <%@ include file="header.jsp" %>

        <div id="content-placeholder">
          <jsp:include page="${pageContent}" />
        </div>

      </main>
    </div>
  </body>
</html>
