package com.fashion.weddingdressrental;


import java.util.Date;

public class AlterationRequest {
    private String alterationId;
    private String details;
    private double cost;
    private String status; // "Requested", "In Progress", "Completed"
    private Date completionDate;

    public AlterationRequest(String alterationId, String details, double cost, Date completionDate) {
        this.alterationId = alterationId;
        this.details = details;
        this.cost = cost;
        this.status = "Requested";
        this.completionDate = completionDate;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Alteration status updated to: " + newStatus);
    }

    public String getDetails() {
        return details;
    }

    public double getCost() {
        return cost;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    @Override
    public String toString() {
        return "AlterationRequest{" +
                "alterationId='" + alterationId + '\'' +
                ", details='" + details + '\'' +
                ", cost=" + cost +
                ", status='" + status + '\'' +
                ", completionDate=" + completionDate +
                '}';
    }
}
