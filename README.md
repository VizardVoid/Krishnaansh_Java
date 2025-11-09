# Flight Reservation System
**Project Based Learning in Java (End Sem Project)**

A web-based Flight Reservation System built using Java, Servlets, JSP, and XML. This project was developed as part of our B.Tech End Semester project to demonstrate core Java web development concepts.

## 📚 About The Project

This system allows users to:
- Search and book flights online
- View available seats and fares
- Manage bookings (view/cancel)
- Receive email confirmations

Admin users can:
- Add/Edit/Delete flights
- View all bookings and passengers
- Generate reports and analytics
- Manage flight schedules

## 🛠 Technologies Used

- **Language**: Java 11
- **Web Technologies**: Servlets, JSP
- **Build Tool**: Maven
- **Server**: Apache Tomcat 9.0
- **Data Storage**: XML files
- **Frontend**: HTML, CSS, JavaScript

## 📦 How to Setup and Run

### Prerequisites:
- JDK 11 or higher
- Apache Maven
- Apache Tomcat 9.0 or higher

### Super Easy Way (Recommended):

**Just run this one file:**
```bash
Double-click: RUN.bat
```

That's it! The script will:
1. ✅ Check if Java and Maven are installed
2. ✅ Build the project automatically
3. ✅ Find Tomcat and deploy automatically
4. ✅ Start the server
5. ✅ Open browser automatically

### Manual Way:

1. **Build the project**
```bash
cd "path\to\Java Project"
mvn clean package
```

2. **Deploy to Tomcat**
- Copy `target/flight-reservation-system.war` file
- Paste it in Tomcat's `webapps` folder
- Start Tomcat server

3. **Access the website**
```
http://localhost:8080/flight-reservation-system/
```

## 🔐 Login Details

**Admin:**
- Username: `admin`
- Password: `admin123`

**Customer:**
- Register new account from the website

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/airline/
│   │   ├── model/      # Data classes (Flight, Booking, etc.)
│   │   ├── dao/        # Database operations using XML
│   │   ├── servlet/    # Controllers for handling requests
│   │   └── util/       # Helper classes
│   └── webapp/
│       ├── *.jsp       # All web pages
│       ├── css/        # Stylesheets
│       └── WEB-INF/    # Configuration and data files
```

## ✨ Key Features Implemented

1. **User Authentication** - Login/Register with session management
2. **Flight Search** - Search by source, destination, and date
3. **Booking System** - Real-time seat availability and booking
4. **Payment Integration** - Simulated payment gateway
5. **Email Notifications** - Booking confirmations via email
6. **Admin Panel** - Complete flight and booking management
7. **Reports** - Revenue and booking analytics
8. **Responsive UI** - Premium modern design

## 📝 What We Learned

- MVC architecture in Java web applications
- Working with Servlets and JSP
- XML-based data storage and retrieval
- Session management and authentication
- Email integration using JavaMail API
- Deploying web applications on Tomcat

## 👥 Team Members

- [Your Name]
- [Team Member 2]
- [Team Member 3]

## 📄 Note

This is an academic project created for educational purposes as part of our B.Tech curriculum.

---
**Project Based Learning in Java (End Sem Project) - 2024-2025**
