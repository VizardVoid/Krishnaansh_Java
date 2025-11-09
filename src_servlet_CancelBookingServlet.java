package servlet;

import dao.BookingDAO;
import model.Booking;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class CancelBookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookingId = req.getParameter("bookingId");
        List<Booking> bookings = BookingDAO.getAllBookings();
        for (Booking b : bookings) {
            if (b.getBookingId().equals(bookingId)) {
                b.setStatus("CANCELLED");
                break;
            }
        }
        BookingDAO.saveAllBookings(bookings);
        resp.sendRedirect("jsp/history.jsp?cancelled=true");
    }
}