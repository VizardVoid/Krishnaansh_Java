<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Server Error - Flight Reservation System</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <main class="main-content">
        <div class="container">
            <div class="error-container" style="text-align: center; padding: 3rem;">
                <h1 style="font-size: 6rem; color: #dc3545;">500</h1>
                <h2>Internal Server Error</h2>
                <p>Something went wrong on our end. Please try again later.</p>
                <a href="index.jsp" class="btn btn-primary" style="margin-top: 2rem;">Go to Home</a>
            </div>
        </div>
    </main>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>
