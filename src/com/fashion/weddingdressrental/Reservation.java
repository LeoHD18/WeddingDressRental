package com.fashion.weddingdressrental;

import java.util.Date;

public class Reservation {
    private String reservationID;
    private Customer customer;
    private InventoryItem dress;
    private Date startDate;
    private Date endDate;
    private String status;

    public Reservation(String reservationID, Customer customer, InventoryItem dress, Date startDate, Date endDate) {
        this.reservationID = reservationID;
        this.customer = customer;
        this.dress = dress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "Pending";
    }

    public String getReservationID() {
        return reservationID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public InventoryItem getDress() {
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

    public void confirmReservation() {
        if (isAvailable(startDate, endDate)) {
            dress.markReserved();
            this.status = "Confirmed";
            System.out.println("Reservation confirmed for " + customer.getName() + " from " + startDate + " to " + endDate);
        } else {
            System.out.println("The dress is not available for the selected dates.");
        }
    }

    public void cancelReservation() {
        this.status = "Cancelled";
        dress.markAvailable();  // Mark the dress as available again
        System.out.println("Reservation cancelled for " + customer.getName());
    }

    public boolean isAvailable(Date requestedStartDate, Date requestedEndDate) {
        return !dress.isReserved();
    }

    public int calculateDuration() {
        long differenceInMillis = endDate.getTime() - startDate.getTime();
        return (int) (differenceInMillis / (1000 * 60 * 60 * 24));
    }

    public String toCSV() {
        return reservationID + "," + customer.getCustomerId() + "," + dress.getDressId() + "," +
               startDate.getTime() + "," + endDate.getTime() + "," + status;
    }
}
