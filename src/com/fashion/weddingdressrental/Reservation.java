package com.fashion.weddingdressrental;

import java.util.Date;

public class Reservation {
    // Attributes
    private String reservationID;
    private Customer customer;
    private WeddingDress dress;
    private Date startDate;
    private Date endDate;
    private String status;  // e.g., "Confirmed", "Cancelled"

    // Constructor
    public Reservation(String reservationID, Customer customer, WeddingDress dress, Date startDate, Date endDate) {
        this.reservationID = reservationID;
        this.customer = customer;
        this.dress = dress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "Pending";
    }

    // Getters and Setters
    public String getReservationID() {
        return reservationID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public WeddingDress getDress() {
        return dress;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Methods

    // Confirm the reservation and set status to Confirmed
    public void confirmReservation() {
        if (isAvailable(startDate, endDate)) {
            this.status = "Confirmed";
            System.out.println("Reservation confirmed for " + customer.getName() + " from " + startDate + " to " + endDate);
        } else {
            System.out.println("The dress is not available for the selected dates.");
        }
    }

    // Cancel the reservation and set status to Cancelled
    public void cancelReservation() {
        this.status = "Cancelled";
        System.out.println("Reservation cancelled for " + customer.getName());
    }

    // Check if the dress is available for the specified dates
    public boolean isAvailable(Date requestedStartDate, Date requestedEndDate) {
        // Here, you would normally check the availability against existing reservations.
        // For simplicity, we'll assume it's always available in this example.
        return true;
    }

    // Calculate the duration of the rental in days
    public int calculateDuration() {
        long differenceInMillis = endDate.getTime() - startDate.getTime();
        return (int) (differenceInMillis / (1000 * 60 * 60 * 24));  // Convert milliseconds to days
    }
}
