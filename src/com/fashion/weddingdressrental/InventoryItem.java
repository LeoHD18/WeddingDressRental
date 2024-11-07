package com.fashion.weddingdressrental;

public class InventoryItem {
    private String dressId;
    private String status;
    private double price;
    private String tentativeAvailabilityDate;
    private boolean reserved;  // New field to track if the dress is reserved

    public InventoryItem(String dressId, String status, double price) {
        this.dressId = dressId;
        this.status = status;
        this.price = price;
        this.reserved = false; // Initially, the dress is not reserved
    }

    public String getDressId() { return dressId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getTentativeAvailabilityDate() { return tentativeAvailabilityDate; }
    public void setTentativeAvailabilityDate(String tentativeAvailabilityDate) {
        this.tentativeAvailabilityDate = tentativeAvailabilityDate;
    }

    public boolean isReserved() { return reserved; }  // New method to check reservation status
    public void setReserved(boolean reserved) { this.reserved = reserved; }  // New method to update reservation status

    public void markReserved() {
        this.reserved = true;
        this.status = "Reserved";
    }

    public void markAvailable() {
        this.reserved = false;
        this.status = "Available";
    }

    @Override
    public String toString() {
        return "Dress ID: " + dressId + ", Status: " + status + ", Price: $" + price +
                ", Reserved: " + reserved +
                (tentativeAvailabilityDate != null ? ", Tentative Availability: " + tentativeAvailabilityDate : "");
    }
}
