package com.fashion.weddingdressrental;

import java.util.Date;

public class AlterationRequest {
    private String alterationId;
    private String details;
    private double cost;
    private String status; // "Requested", "In Progress", "Completed", "Cannot Complete"
    private Date completionDate;

    public AlterationRequest(String alterationId, String details, double cost, Date completionDate) {
        this.alterationId = alterationId;
        this.details = details;
        this.cost = cost;
        this.status = "Requested";
        this.completionDate = completionDate;
    }

    public void startAlteration() {
        updateStatus("In Progress");
        System.out.println("Alteration has started for ID: " + alterationId);
    }

    public void completeAlteration() {
        updateStatus("Completed");
        System.out.println("Alteration completed successfully for ID: " + alterationId);
    }

    public void cannotCompleteAlteration() {
        updateStatus("Cannot Complete");
        System.out.println("Alteration request cannot be completed for ID: " + alterationId);
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Alteration status updated to: " + newStatus);
    }

    public String getAlterationId() {
        return alterationId;
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

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AlterationRequest {" +
                "ID='" + alterationId + '\'' +
                ", Details='" + details + '\'' +
                ", Cost=" + cost +
                ", Status='" + status + '\'' +
                ", Completion Date=" + completionDate +
                '}';
    }
}
