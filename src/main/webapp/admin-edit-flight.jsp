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
    <title>Edit Flight - Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <div class="page-header">
                <h2><i class="icon">✈️</i> Edit Flight</h2>
                <p>Update flight information and schedule</p>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    <i class="icon">⚠️</i> ${error}
                </div>
            </c:if>

            <div class="form-card premium-card">
                <form action="admin" method="post" class="premium-form">
                    <input type="hidden" name="action" value="updateFlight">
                    <input type="hidden" name="flightId" value="${flight.flightId}">

                    <div class="form-grid">
                        <div class="form-group">
                            <label for="flightNumber">Flight Number *</label>
                            <input type="text" id="flightNumber" name="flightNumber" 
                                   value="${flight.flightNumber}" required>
                        </div>

                        <div class="form-group">
                            <label for="airline">Airline *</label>
                            <input type="text" id="airline" name="airline" 
                                   value="${flight.airline}" required>
                        </div>

                        <div class="form-group">
                            <label for="source">From *</label>
                            <input type="text" id="source" name="source" 
                                   value="${flight.source}" required>
                        </div>

                        <div class="form-group">
                            <label for="destination">To *</label>
                            <input type="text" id="destination" name="destination" 
                                   value="${flight.destination}" required>
                        </div>

                        <div class="form-group">
                            <label for="departureTime">Departure Time *</label>
                            <input type="datetime-local" id="departureTime" name="departureTime" 
                                   value="${flight.departureTime}" required>
                        </div>

                        <div class="form-group">
                            <label for="arrivalTime">Arrival Time *</label>
                            <input type="datetime-local" id="arrivalTime" name="arrivalTime" 
                                   value="${flight.arrivalTime}" required>
                        </div>

                        <div class="form-group">
                            <label for="totalSeats">Total Seats *</label>
                            <input type="number" id="totalSeats" name="totalSeats" 
                                   value="${flight.totalSeats}" min="1" required>
                        </div>

                        <div class="form-group">
                            <label for="availableSeats">Available Seats *</label>
                            <input type="number" id="availableSeats" name="availableSeats" 
                                   value="${flight.availableSeats}" min="0" required>
                        </div>

                        <div class="form-group">
                            <label for="economyFare">Economy Fare ($) *</label>
                            <input type="number" id="economyFare" name="economyFare" 
                                   value="${flight.economyFare}" step="0.01" min="0" required>
                        </div>

                        <div class="form-group">
                            <label for="businessFare">Business Fare ($) *</label>
                            <input type="number" id="businessFare" name="businessFare" 
                                   value="${flight.businessFare}" step="0.01" min="0" required>
                        </div>

                        <div class="form-group">
                            <label for="status">Status *</label>
                            <select id="status" name="status" required>
                                <option value="SCHEDULED" ${flight.status == 'SCHEDULED' ? 'selected' : ''}>Scheduled</option>
                                <option value="DELAYED" ${flight.status == 'DELAYED' ? 'selected' : ''}>Delayed</option>
                                <option value="CANCELLED" ${flight.status == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                                <option value="COMPLETED" ${flight.status == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary btn-large">
                            <i class="icon">💾</i> Update Flight
                        </button>
                        <a href="admin?action=flights" class="btn btn-secondary btn-large">
                            <i class="icon">✖️</i> Cancel
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>
