package com.fashion.weddingdressrental.DressCustomization;

public class Customization {
    private String customizationId;
    private String customerId;
    private String dressId;
    private String details;
    private double cost;
    private String status; // Pending, Approved, Rejected

    public Customization(String customizationId, String customerId, String dressId, String details, double cost, String status) {
        this.customizationId = customizationId;
        this.customerId = customerId;
        this.dressId = dressId;
        this.details = details;
        this.cost = cost;
        this.status = status;
    }

    public String toCSV() {
        return customizationId + "," + customerId + "," + dressId + "," + details + "," + cost + "," + status;
    }

    public static Customization fromCSV(String csv) {
        String[] parts = csv.split(",");
        return new Customization(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), parts[5]);
    }

    // Getters and setters
    public String getCustomizationId() {
        return customizationId;
    }

    public void setCustomizationId(String customizationId) {
        this.customizationId = customizationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDressId() {
        return dressId;
    }

    public void setDressId(String dressId) {
        this.dressId = dressId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}