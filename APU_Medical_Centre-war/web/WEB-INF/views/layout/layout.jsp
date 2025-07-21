<%-- 
    Document   : layout
    Created on : 11 Jun 2025, 1:45:38?am
    Author     : khong
--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - APU Medical Centre</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css" />

    <!-- Placeholder for page-specific CSS/JS -->
    <c:if test="${not empty pageCss}">
        <link rel="stylesheet" href="${pageCss}" />
    </c:if>
    <c:if test="${not empty pageJs}">
        <script src="${pageJs}"></script>
    </c:if>
    <script>
        function toggleSidebar() {
          document.getElementById('sidebar').classList.toggle('collapsed');
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

        <%@ include file="footer.jsp" %>
    </main>
  </div>
</body>
</html>
