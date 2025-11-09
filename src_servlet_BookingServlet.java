package servlet;

import dao.BookingDAO;
import dao.FlightDAO;
import model.Booking;
import model.Flight;
import util.IDGenerator;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class BookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String passengerId = req.getParameter("passengerId");
        String flightId = req.getParameter("flightId");
        String seatNo = req.getParameter("seatNo");

        Booking booking = new Booking();
        booking.setBookingId(IDGenerator.generateBookingId());
        booking.setPassengerId(passengerId);
        booking.setFlightId(flightId);
        booking.setSeatNo(seatNo);
        booking.setBookingDate(java.time.LocalDate.now().toString());
        booking.setStatus("CONFIRMED");

        List<Booking> bookings = BookingDAO.getAllBookings();
        bookings.add(booking);
        BookingDAO.saveAllBookings(bookings);

        // Reduce available seats in flight (implement logic here)
        resp.sendRedirect("jsp/booking.jsp?success=true");
    }
}