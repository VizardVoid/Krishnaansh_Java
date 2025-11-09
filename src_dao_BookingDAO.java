package dao;

import model.Booking;
import util.XMLUtils;
import java.util.List;

public class BookingDAO {
    private static final String BOOKING_XML = "data/bookings.xml";

    public static List<Booking> getAllBookings() {
        return XMLUtils.readBookings(BOOKING_XML);
    }
    public static void saveAllBookings(List<Booking> bookings) {
        XMLUtils.writeBookings(BOOKING_XML, bookings);
    }
    // Add getById, getByPid, addBooking etc as required
}