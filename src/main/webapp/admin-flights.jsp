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
    <title>Manage Flights - Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem;">
                <h2>Manage Flights</h2>
                <a href="admin?action=addFlight" class="btn btn-primary">Add New Flight</a>
            </div>

            <c:if test="${not empty message}">
                <div class="alert alert-success">${message}</div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <div class="table-container">
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>Flight Number</th>
                            <th>Airline</th>
                            <th>Route</th>
                            <th>Departure</th>
                            <th>Arrival</th>
                            <th>Seats</th>
                            <th>Economy</th>
                            <th>Business</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${flights}" var="flight">
                            <tr>
                                <td><strong>${flight.flightNumber}</strong></td>
                                <td>${flight.airline}</td>
                                <td>${flight.source} → ${flight.destination}</td>
                                <td>${flight.departureTime}</td>
                                <td>${flight.arrivalTime}</td>
                                <td>${flight.availableSeats}/${flight.totalSeats}</td>
                                <td>$<fmt:formatNumber value="${flight.economyFare}" pattern="#,##0.00"/></td>
                                <td>$<fmt:formatNumber value="${flight.businessFare}" pattern="#,##0.00"/></td>
                                <td><span class="status-badge status-${flight.status}">${flight.status}</span></td>
                                <td class="action-buttons">
                                    <a href="admin?action=editFlight&id=${flight.flightId}" class="btn btn-small btn-secondary">Edit</a>
                                    <a href="admin?action=deleteFlight&id=${flight.flightId}" 
                                       class="btn btn-small btn-danger" 
                                       onclick="return confirm('Are you sure you want to delete this flight?');">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div style="margin-top: 2rem;">
                <a href="admin?action=dashboard" class="btn btn-secondary">← Back to Dashboard</a>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>
