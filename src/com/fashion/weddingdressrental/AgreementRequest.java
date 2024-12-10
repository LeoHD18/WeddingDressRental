package com.fashion.weddingdressrental;

public class AgreementRequest {
    private final String requestId;
    private final String supplierId;
    private final String storeId;
    private String status; // Pending, Approved, or Rejected

    public AgreementRequest(String requestId, String supplierId, String storeId, String status) {
        this.requestId = requestId;
        this.supplierId = supplierId;
        this.storeId = storeId;
        this.status = status;
    }

    // Getters and Setters
    public String getRequestId() {
        return requestId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request ID: " + requestId +
               ", Supplier ID: " + supplierId +
               ", Store ID: " + storeId +
               ", Status: " + status;
    }
}
