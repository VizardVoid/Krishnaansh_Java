<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session.getAttribute("role") == null || !"ADMIN".equals(session.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Passengers - Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <h2>All Passengers</h2>

            <c:choose>
                <c:when test="${empty passengers}">
                    <div class="alert-info">
                        <p>No passengers found in the system.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-container">
                        <table class="admin-table">
                            <thead>
                                <tr>
                                    <th>Passenger ID</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>ID Type</th>
                                    <th>ID Number</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${passengers}" var="passenger">
                                    <tr>
                                        <td><strong>${passenger.passengerId}</strong></td>
                                        <td>${passenger.fullName}</td>
                                        <td>${passenger.email}</td>
                                        <td>${passenger.phone}</td>
                                        <td>${passenger.idType}</td>
                                        <td>${passenger.idNumber}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>

            <div style="margin-top: 2rem;">
                <a href="admin?action=dashboard" class="btn btn-secondary">← Back to Dashboard</a>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>
