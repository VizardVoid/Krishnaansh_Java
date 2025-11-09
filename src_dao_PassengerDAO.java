package dao;

import model.Passenger;
import util.XMLUtils;
import java.util.List;

public class PassengerDAO {
    private static final String PASSENGER_XML = "data/passengers.xml";

    public static List<Passenger> getAllPassengers() {
        return XMLUtils.readPassengers(PASSENGER_XML);
    }
    public static void saveAllPassengers(List<Passenger> passengers) {
        XMLUtils.writePassengers(PASSENGER_XML, passengers);
    }
    // Add CRUD as required (getById, add, update, etc)
}