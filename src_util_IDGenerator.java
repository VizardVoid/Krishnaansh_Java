package util;

import java.util.UUID;

public class IDGenerator {
    public static String generateBookingId() {
        return "B" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    public static String generatePassengerId() {
        return "P" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}