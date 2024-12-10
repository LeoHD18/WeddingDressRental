package com.fashion.weddingdressrental;

/**
 * Represents a request for an agreement between a supplier and a store in the wedding dress rental system.
 * This class manages the lifecycle of agreement requests, tracking their status as they move through
 * the approval process. An agreement request can be in one of three states: Pending, Approved, or Rejected.
 */
public class AgreementRequest {
    private final String requestId;
    private final String supplierId;
    private final String storeId;
    private String status; // Pending, Approved, or Rejected

    /**
     * Constructs a new AgreementRequest with the specified details.
     *
     * @param requestId  The unique identifier for the agreement request
     * @param supplierId The ID of the supplier requesting the agreement
     * @param storeId    The ID of the store with which the agreement is requested
     * @param status     The initial status of the request (Pending, Approved, or Rejected)
     */
    public AgreementRequest(String requestId, String supplierId, String storeId, String status) {
        this.requestId = requestId;
        this.supplierId = supplierId;
        this.storeId = storeId;
        this.status = status;
    }

    /**
     * Returns the unique identifier of the agreement request.
     *
     * @return The request's ID
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Returns the ID of the supplier who initiated the agreement request.
     *
     * @return The supplier's ID
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * Returns the ID of the store involved in the agreement request.
     *
     * @return The store's ID
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * Returns the current status of the agreement request.
     * Status can be one of: Pending, Approved, or Rejected.
     *
     * @return The current status of the request
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the status of the agreement request.
     * Valid status values are: Pending, Approved, or Rejected.
     *
     * @param status The new status for the agreement request
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the agreement request.
     * Includes the request ID, supplier ID, store ID, and current status.
     *
     * @return A formatted string containing all agreement request details
     */
    @Override
    public String toString() {
        return "Request ID: " + requestId +
                ", Supplier ID: " + supplierId +
                ", Store ID: " + storeId +
                ", Status: " + status;
    }
}