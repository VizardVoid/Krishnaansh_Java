<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Flight Reservation System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <section class="hero">
            <div class="hero-content">
                <h1>Welcome to Flight Reservation System</h1>
                <p>Book your flights easily and securely</p>
            </div>
        </section>

        <section class="search-section">
            <div class="container">
                <h2>Search Flights</h2>
                <form action="flight" method="post" class="search-form">
                    <input type="hidden" name="action" value="search">
                    
                    <div class="form-group">
                        <label for="source">From</label>
                        <input type="text" id="source" name="source" required 
                               placeholder="Enter departure city">
                    </div>

                    <div class="form-group">
                        <label for="destination">To</label>
                        <input type="text" id="destination" name="destination" required 
                               placeholder="Enter destination city">
                    </div>

                    <div class="form-group">
                        <label for="date">Date</label>
                        <input type="date" id="date" name="date" required>
                    </div>

                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Search Flights</button>
                    </div>
                </form>
            </div>
        </section>

        <section class="features">
            <div class="container">
                <h2>Why Choose Us</h2>
                <div class="features-grid">
                    <div class="feature-card">
                        <h3>Easy Booking</h3>
                        <p>Simple and fast flight booking process</p>
                    </div>
                    <div class="feature-card">
                        <h3>Secure Payments</h3>
                        <p>Multiple secure payment options</p>
                    </div>
                    <div class="feature-card">
                        <h3>24/7 Support</h3>
                        <p>Round-the-clock customer support</p>
                    </div>
                    <div class="feature-card">
                        <h3>Real-time Updates</h3>
                        <p>Get instant flight status updates</p>
                    </div>
                </div>
            </div>
        </section>
    </main>

    <jsp:include page="includes/footer.jsp" />
    
    <script>
        // Set minimum date to today
        document.getElementById('date').min = new Date().toISOString().split('T')[0];
    </script>
</body>
</html>
