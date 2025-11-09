<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head><title>Booking Confirmation</title></head>
<body>
    <h1>Booking ${param.success eq 'true' ? 'Successful!' : 'Unsuccessful'}</h1>
    <a href="search">Back to Search</a>
</body>
</html>