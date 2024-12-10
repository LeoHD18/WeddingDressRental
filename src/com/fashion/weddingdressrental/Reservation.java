package com.fashion.weddingdressrental;

import java.util.Date;

public class Reservation {
    private String reservationID;
    private Customer customer;
    private InventoryItem dress;
    private Date startDate;
    private Date endDate;
    private String status;
    private String storeId;  // Store where reservation is made
    private int quantity;    // Reserved quantity

    // Updated Constructor
    public Reservation(String reservationID, Customer customer, InventoryItem dress, Date startDate, Date endDate, String storeId, int quantity) {
        this.reservationID = reservationID;
        this.customer = customer;
        this.dress = dress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.storeId = storeId;
        this.quantity = quantity;
        this.status = "Pending";
    }

    // Getters and Setters
    public String getReservationID() { return reservationID; }
    public Customer getCustomer() { return customer; }
    public InventoryItem getDress() { return dress; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getStoreId() { return storeId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // CSV Representation for Saving
    public String toCSV() {
        return String.join(",", reservationID, customer.getCustomerId(), dress.getDressId(), storeId,
                String.valueOf(quantity), String.valueOf(startDate.getTime()), String.valueOf(endDate.getTime()), status);
    }
}
