package model;

public class Flight {
    private String flightId;
    private String source;
    private String destination;
    private String date;
    private String time;
    private int totalSeats;
    private int availableSeats;
    private double fare;
    private String flightClass; // Economy/Business
    private String status; // On-time/Delayed/Cancelled

    // Getters and Setters
    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }
    public String getFlightClass() { return flightClass; }
    public void setFlightClass(String flightClass) { this.flightClass = flightClass; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}