package com.airline.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Utility class for sending email notifications
 */
public class EmailService {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_USERNAME = "airline@example.com"; // Configure your email
    private static final String EMAIL_PASSWORD = "your_password"; // Configure your password
    private static final String FROM_EMAIL = "airline@example.com";

    /**
     * Send booking confirmation email
     */
    public static boolean sendBookingConfirmation(String toEmail, String passengerName, 
                                                  String bookingId, String flightNumber, 
                                                  String departureTime, double fare) {
        String subject = "Booking Confirmation - " + bookingId;
        String body = buildBookingConfirmationEmail(passengerName, bookingId, flightNumber, departureTime, fare);
        return sendEmail(toEmail, subject, body);
    }

    /**
     * Send booking cancellation email
     */
    public static boolean sendCancellationConfirmation(String toEmail, String passengerName, 
                                                       String bookingId, String flightNumber) {
        String subject = "Booking Cancellation Confirmation - " + bookingId;
        String body = buildCancellationEmail(passengerName, bookingId, flightNumber);
        return sendEmail(toEmail, subject, body);
    }

    /**
     * Send flight schedule update email
     */
    public static boolean sendScheduleUpdate(String toEmail, String passengerName, 
                                             String flightNumber, String updateInfo) {
        String subject = "Flight Schedule Update - " + flightNumber;
        String body = buildScheduleUpdateEmail(passengerName, flightNumber, updateInfo);
        return sendEmail(toEmail, subject, body);
    }

    /**
     * Send generic email
     */
    public static boolean sendEmail(String toEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Uncomment to actually send emails (requires valid SMTP configuration)
            // Transport.send(message);
            
            System.out.println("Email sent to: " + toEmail);
            System.out.println("Subject: " + subject);
            System.out.println("Body: " + body);
            
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String buildBookingConfirmationEmail(String passengerName, String bookingId, 
                                                       String flightNumber, String departureTime, double fare) {
        return String.format(
            "Dear %s,\n\n" +
            "Your flight booking has been confirmed!\n\n" +
            "Booking Details:\n" +
            "Booking ID: %s\n" +
            "Flight Number: %s\n" +
            "Departure Time: %s\n" +
            "Total Fare: $%.2f\n\n" +
            "Please arrive at the airport 2 hours before departure.\n\n" +
            "Thank you for choosing our airline!\n\n" +
            "Best Regards,\n" +
            "Airline Reservation Team",
            passengerName, bookingId, flightNumber, departureTime, fare
        );
    }

    private static String buildCancellationEmail(String passengerName, String bookingId, String flightNumber) {
        return String.format(
            "Dear %s,\n\n" +
            "Your flight booking has been cancelled as requested.\n\n" +
            "Cancelled Booking Details:\n" +
            "Booking ID: %s\n" +
            "Flight Number: %s\n\n" +
            "Refund will be processed within 7-10 business days.\n\n" +
            "Thank you for your understanding.\n\n" +
            "Best Regards,\n" +
            "Airline Reservation Team",
            passengerName, bookingId, flightNumber
        );
    }

    private static String buildScheduleUpdateEmail(String passengerName, String flightNumber, String updateInfo) {
        return String.format(
            "Dear %s,\n\n" +
            "Important Update Regarding Your Flight\n\n" +
            "Flight Number: %s\n" +
            "Update: %s\n\n" +
            "We apologize for any inconvenience caused.\n" +
            "For rebooking or cancellation, please contact our customer service.\n\n" +
            "Best Regards,\n" +
            "Airline Reservation Team",
            passengerName, flightNumber, updateInfo
        );
    }
}
