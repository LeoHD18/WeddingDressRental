package com.fashion.weddingdressrental;

import java.util.Date;

public class AlterationRequest {
    private String alterationId;
    private String details;
    private double cost;
    private String status;  // Track status (e.g., "Requested", "altered and ready")
    private Date completionDate;
    private Customer customer;  // Keeping the customer reference
    private Employee employee;

    // Constructor for creating a new alteration request
    public AlterationRequest(Customer customer, String details, double cost, Date completionDate, Employee employee) {
        this.alterationId = "ALT-" + (int)(Math.random() * 1000);
        this.customer = customer;
        this.details = details;
        this.cost = cost;
        this.status = "Requested";  // Initial status
        this.completionDate = completionDate;
        this.employee = employee;
    }

    // Constructor for loading from file (assuming customer and employee are managed separately)
    public AlterationRequest(String alterationId, String customerId, String details, double cost, Date completionDate) {
        this.alterationId = alterationId;
        this.details = details;
        this.cost = cost;
        this.status = "Requested";  // Initial status
        this.completionDate = completionDate;
        // Note: Customer and Employee objects should be assigned after loading, using customerId or other identifiers
    }

    // Method to convert alteration request to CSV format for file storage, including status
    public String toCSV() {
        return alterationId + "," +
               (customer != null ? customer.getCustomerId() : "") + "," +
               details + "," + cost + "," +
               completionDate.getTime() + "," +
               status;  // Include the status in CSV output
    }

    // Getters for accessing various attributes
    public String getAlterationId() {
        return alterationId;
    }

    public String getDetails() {
        return details;
    }

    public double getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setStatus(String status) {
        this.status = status;  // Set the status to track alteration progress
    }

    // New getter for accessing the customer associated with the alteration request
    public Customer getCustomer() {
        return customer;
    }

    // Override toString for readable display of alteration request details, including status
    @Override
    public String toString() {
        return "AlterationRequest{" +
                "ID='" + alterationId + '\'' +
                ", Details='" + details + '\'' +
                ", Cost=" + cost +
                ", Status='" + status + '\'' +  // Display the status in the string representation
                ", Completion Date=" + completionDate +
                '}';
    }
}
