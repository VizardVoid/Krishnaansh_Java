package com.airline.util;

import java.util.UUID;

/**
 * Utility class for simulating payment gateway integration
 */
public class PaymentGateway {

    /**
     * Process payment and return transaction ID
     * In production, this would integrate with actual payment gateways like Stripe, PayPal, etc.
     */
    public static String processPayment(String paymentMethod, String cardNumber, 
                                       String cvv, String expiryDate, double amount) {
        // Simulate payment processing
        try {
            Thread.sleep(500); // Simulate API call delay (reduced for better UX)
            
            // Basic validation - very lenient for testing
            if (cardNumber == null || cardNumber.trim().isEmpty()) {
                throw new Exception("Card number is required");
            }
            
            if (cvv == null || cvv.trim().isEmpty()) {
                throw new Exception("CVV is required");
            }
            
            // For testing: Accept any card number with 13-19 digits
            String cleanCardNumber = cardNumber.replaceAll("[\\s-]", "");
            if (!cleanCardNumber.matches("\\d{13,19}")) {
                throw new Exception("Invalid card number format");
            }
            
            if (!isValidCVV(cvv)) {
                throw new Exception("Invalid CVV");
            }
            
            // Always succeed for testing (you can add specific test card numbers that fail if needed)
            return generateTransactionId();
            
        } catch (Exception e) {
            System.err.println("Payment failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Validate CVV
     */
    private static boolean isValidCVV(String cvv) {
        return cvv != null && cvv.matches("\\d{3,4}");
    }

    /**
     * Mask card number for display
     */
    public static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        String last4 = cardNumber.substring(cardNumber.length() - 4);
        return "**** **** **** " + last4;
    }

    /**
     * Generate unique transaction ID
     */
    private static String generateTransactionId() {
        return "TXN" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }

    /**
     * Process refund
     */
    public static boolean processRefund(String transactionId, double amount) {
        try {
            Thread.sleep(500); // Simulate API call
            // In production, this would call the payment gateway's refund API
            System.out.println("Refund processed: " + transactionId + " - $" + amount);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verify payment status
     */
    public static String verifyPayment(String transactionId) {
        // In production, this would verify with the payment gateway
        if (transactionId != null && transactionId.startsWith("TXN")) {
            return "SUCCESS";
        }
        return "FAILED";
    }
}
