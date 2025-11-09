package com.airline.util;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.UUID;

/**
 * Utility class for ID generation and security functions
 */
public class IDGenerator {

    /**
     * Generate unique Flight ID
     */
    public static String generateFlightId() {
        return "FLT" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    /**
     * Generate unique Passenger ID
     */
    public static String generatePassengerId() {
        return "PSG" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    /**
     * Generate unique Booking ID
     */
    public static String generateBookingId() {
        return "BKG" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    /**
     * Generate unique Payment ID
     */
    public static String generatePaymentId() {
        return "PAY" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    /**
     * Generate unique User ID
     */
    public static String generateUserId() {
        return "USR" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    /**
     * Generate seat number
     */
    public static String generateSeatNumber(String seatClass, int seatCount) {
        int row = (seatCount / 6) + 1;
        char seat = (char) ('A' + (seatCount % 6));
        return row + String.valueOf(seat);
    }

    /**
     * Hash password using SHA-256
     */
    public static String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

    /**
     * Verify password against hash
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return DigestUtils.sha256Hex(password).equals(hashedPassword);
    }

    /**
     * Generate random OTP
     */
    public static String generateOTP() {
        int otp = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(otp);
    }
}
