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
    <title>Reports - Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <h2>Reports & Analytics</h2>

            <div class="reports-grid">
                <div class="report-card">
                    <h3>📊 Booking Summary</h3>
                    <div class="report-stats">
                        <p>Total Bookings: <strong>${totalBookings}</strong></p>
                        <p>Confirmed: <strong>${confirmedBookings}</strong></p>
                        <p>Cancelled: <strong>${cancelledBookings}</strong></p>
                        <p>Total Revenue: <strong>$<fmt:formatNumber value="${totalRevenue}" pattern="#,##0.00"/></strong></p>
                    </div>
                </div>

                <div class="report-card">
                    <h3>✈️ Flight Statistics</h3>
                    <div class="report-stats">
                        <p>Total Flights: <strong>${totalFlights}</strong></p>
                        <p>Active Flights: <strong>${activeFlights}</strong></p>
                        <p>Total Passengers: <strong>${totalPassengers}</strong></p>
                        <p>Occupancy Rate: <strong>${occupancyRate}%</strong></p>
                    </div>
                </div>

                <div class="report-card">
                    <h3>💰 Revenue Analysis</h3>
                    <div class="report-stats">
                        <p>Economy Class: <strong>$<fmt:formatNumber value="${economyRevenue}" pattern="#,##0.00"/></strong></p>
                        <p>Business Class: <strong>$<fmt:formatNumber value="${businessRevenue}" pattern="#,##0.00"/></strong></p>
                        <p>Average Fare: <strong>$<fmt:formatNumber value="${averageFare}" pattern="#,##0.00"/></strong></p>
                    </div>
                </div>

                <div class="report-card">
                    <h3>📈 Popular Routes</h3>
                    <div class="report-stats">
                        <c:forEach items="${popularRoutes}" var="route" begin="0" end="4">
                            <p>${route.route}: <strong>${route.count} bookings</strong></p>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div style="margin-top: 2rem;">
                <a href="admin?action=dashboard" class="btn btn-secondary">← Back to Dashboard</a>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>
