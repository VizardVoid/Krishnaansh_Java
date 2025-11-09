<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <title>Admin Dashboard - Flight Reservation System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <h2>Admin Dashboard</h2>
            
            <div class="dashboard-grid">
                <div class="dashboard-card">
                    <div class="card-icon">✈️</div>
                    <h3>Total Flights</h3>
                    <p class="card-value">${totalFlights}</p>
                    <a href="admin?action=flights" class="card-link">Manage Flights →</a>
                </div>

                <div class="dashboard-card">
                    <div class="card-icon">📋</div>
                    <h3>Total Bookings</h3>
                    <p class="card-value">${totalBookings}</p>
                    <a href="admin?action=bookings" class="card-link">View Bookings →</a>
                </div>

                <div class="dashboard-card">
                    <div class="card-icon">👥</div>
                    <h3>Total Passengers</h3>
                    <p class="card-value">${totalPassengers}</p>
                    <a href="admin?action=passengers" class="card-link">View Passengers →</a>
                </div>

                <div class="dashboard-card">
                    <div class="card-icon">💰</div>
                    <h3>Total Revenue</h3>
                    <p class="card-value">$<fmt:formatNumber value="${totalRevenue}" pattern="#,##0.00"/></p>
                    <a href="admin?action=reports" class="card-link">View Reports →</a>
                </div>
            </div>

            <div class="admin-actions">
                <h3>Quick Actions</h3>
                <div class="action-buttons">
                    <a href="admin?action=addFlight" class="btn btn-primary">Add New Flight</a>
                    <a href="admin?action=flights" class="btn btn-secondary">Manage Flights</a>
                    <a href="admin?action=bookings" class="btn btn-secondary">View All Bookings</a>
                    <a href="admin?action=reports" class="btn btn-secondary">Generate Reports</a>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>
