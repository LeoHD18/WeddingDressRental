package com.fashion.weddingdressrental;

public class InventoryItem {
    private String dressId;
    private String status;
    private double price;
    private String tentativeAvailabilityDate;

    public InventoryItem(String dressId, String status, double price) {
        this.dressId = dressId;
        this.status = status;
        this.price = price;
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

    @Override
    public String toString() {
        return "Dress ID: " + dressId + ", Status: " + status + ", Price: $" + price +
                (tentativeAvailabilityDate != null ? ", Tentative Availability: " + tentativeAvailabilityDate : "");
    }
}
