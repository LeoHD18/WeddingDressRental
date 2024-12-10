package com.fashion.weddingdressrental;

public class InventoryItem {
    private String dressId;
    private String status;
    private double price;
    private int quantity;  // New field for tracking dress quantity
    private String tentativeAvailabilityDate;
    private boolean reserved;  // Tracks if the dress is reserved

    // Constructor
    public InventoryItem(String dressId, String status, double price, int quantity, String tentativeAvailabilityDate) {
        this.dressId = dressId;
        this.status = status;
        this.price = price;
        this.quantity = quantity;
        this.tentativeAvailabilityDate = tentativeAvailabilityDate;
        this.reserved = "Reserved".equalsIgnoreCase(status);  // Automatically set reserved if status is "Reserved"
    }

    // Getters and Setters
    public String getDressId() {
        return dressId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.reserved = "Reserved".equalsIgnoreCase(status);  // Sync reserved status with dress status
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTentativeAvailabilityDate() {
        return tentativeAvailabilityDate;
    }

    public void setTentativeAvailabilityDate(String tentativeAvailabilityDate) {
        this.tentativeAvailabilityDate = tentativeAvailabilityDate;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
        this.status = reserved ? "Reserved" : "Available";  // Automatically update status
    }

    public void markReserved() {
        setReserved(true);
    }

    public void markAvailable() {
        setReserved(false);
    }

    @Override
    public String toString() {
        return "Dress ID: " + dressId +
                ", Status: " + status +
                ", Price: $" + price +
                ", Quantity: " + quantity +
                ", Reserved: " + reserved +
                (tentativeAvailabilityDate != null && !tentativeAvailabilityDate.isEmpty() ? 
                ", Tentative Availability: " + tentativeAvailabilityDate : "");
    }
}
