package com.fashion.weddingdressrental;

public class InventoryItem {
    private String dressId;
    private String status;
    private String tentativeAvailabilityDate;

    public InventoryItem(String dressId, String status) {
        this.dressId = dressId;
        this.status = status;
        this.tentativeAvailabilityDate = null; // Default to no tentative availability date
    }

    public String getDressId() {
        return dressId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTentativeAvailabilityDate() {
        return tentativeAvailabilityDate;
    }

    public void setTentativeAvailabilityDate(String tentativeAvailabilityDate) {
        this.tentativeAvailabilityDate = tentativeAvailabilityDate;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "dressId='" + dressId + '\'' +
                ", status='" + status + '\'' +
                ", tentativeAvailabilityDate='" + tentativeAvailabilityDate + '\'' +
                '}';
    }
}
