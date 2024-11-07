package com.fashion.weddingdressrental;

import java.util.Date;
public class AlterationRequest {
    private String alterationId;
    private String details;
    private double cost;
    private String status;
    private Date completionDate;
    private Customer customer;
    private Employee employee;

    // Existing constructor
    public AlterationRequest(Customer customer, String details, double cost, Date completionDate, Employee employee) {
        this.alterationId = "ALT-" + (int)(Math.random() * 1000);
        this.customer = customer;
        this.details = details;
        this.cost = cost;
        this.status = "Requested";
        this.completionDate = completionDate;
        this.employee = employee;
    }

    // Constructor for loading from file
    public AlterationRequest(String alterationId, String customerId, String details, double cost, Date completionDate) {
        this.alterationId = alterationId;
        this.details = details;
        this.cost = cost;
        this.status = "Requested";
        this.completionDate = completionDate;
        // customer and employee would be loaded elsewhere based on IDs
    }

    // Convert to CSV format
    public String toCSV() {
        return alterationId + "," + customer.getCustomerId() + "," + details + "," + cost + "," + completionDate.getTime();
    }

    @Override
    public String toString() {
        return "AlterationRequest{" +
                "ID='" + alterationId + '\'' +
                ", Details='" + details + '\'' +
                ", Cost=" + cost +
                ", Status='" + status + '\'' +
                ", Completion Date=" + completionDate +
                '}';
    }
}
