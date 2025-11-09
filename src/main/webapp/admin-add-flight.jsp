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
    <title>Add Flight - Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <h2>Add New Flight</h2>

            <div class="form-container">
                <form action="admin" method="post" class="admin-form">
                    <input type="hidden" name="action" value="addFlight">

                    <div class="form-row">
                        <div class="form-group">
                            <label for="flightNumber">Flight Number *</label>
                            <input type="text" id="flightNumber" name="flightNumber" required 
                                   placeholder="e.g., AA101" pattern="[A-Z]{2}[0-9]{3,4}">
                        </div>

                        <div class="form-group">
                            <label for="airline">Airline *</label>
                            <input type="text" id="airline" name="airline" required 
                                   placeholder="e.g., American Airlines">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="source">Source City *</label>
                            <input type="text" id="source" name="source" required 
                                   placeholder="e.g., New York">
                        </div>

                        <div class="form-group">
                            <label for="destination">Destination City *</label>
                            <input type="text" id="destination" name="destination" required 
                                   placeholder="e.g., Los Angeles">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="departureTime">Departure Time *</label>
                            <input type="datetime-local" id="departureTime" name="departureTime" required>
                        </div>

                        <div class="form-group">
                            <label for="arrivalTime">Arrival Time *</label>
                            <input type="datetime-local" id="arrivalTime" name="arrivalTime" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="totalSeats">Total Seats *</label>
                            <input type="number" id="totalSeats" name="totalSeats" required 
                                   min="50" max="500" placeholder="e.g., 180">
                        </div>

                        <div class="form-group">
                            <label for="economyFare">Economy Fare ($) *</label>
                            <input type="number" id="economyFare" name="economyFare" required 
                                   step="0.01" min="50" placeholder="e.g., 299.00">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="businessFare">Business Fare ($) *</label>
                            <input type="number" id="businessFare" name="businessFare" required 
                                   step="0.01" min="100" placeholder="e.g., 899.00">
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Add Flight</button>
                        <a href="admin?action=flights" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>
