package com.fashion.weddingdressrental;

/**
 * Represents an inventory transfer request between stores in the wedding dress rental system.
 * This class manages the details of dress transfers between stores, including quantity,
 * pricing, and approval status. Each transfer has a unique identifier and tracks both
 * the requesting and providing stores.
 */
public class InventoryTransfer {

    private final String transferId;
    private final String fromStoreId; // Requesting Store
    private final String toStoreId;   // Providing Store
    private final String product;      // Dress ID
    private final double price;        // Product Price
    private final int quantity;        // Requested Quantity
    private String status;             // Pending/Approved/Rejected

    /**
     * Constructs a new InventoryTransfer with the specified details.
     *
     * @param transferId  The unique identifier for this transfer
     * @param fromStoreId The ID of the store requesting the transfer
     * @param toStoreId   The ID of the store providing the inventory
     * @param product     The ID of the dress being transferred
     * @param price       The price of the dress
     * @param quantity    The quantity of dresses being requested
     * @param status      The initial status of the transfer (Pending/Approved/Rejected)
     */
    public InventoryTransfer(String transferId, String fromStoreId, String toStoreId, String product, double price, int quantity, String status) {
        this.transferId = transferId;
        this.fromStoreId = fromStoreId;
        this.toStoreId = toStoreId;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * Returns the unique identifier of this transfer.
     *
     * @return The transfer ID
     */
    public String getTransferId() {
        return transferId;
    }

    /**
     * Returns the ID of the store requesting the transfer.
     *
     * @return The requesting store's ID
     */
    public String getFromStoreId() {
        return fromStoreId;
    }

    /**
     * Returns the ID of the store providing the inventory.
     *
     * @return The providing store's ID
     */
    public String getToStoreId() {
        return toStoreId;
    }

    /**
     * Returns the ID of the dress being transferred.
     *
     * @return The dress ID
     */
    public String getProduct() {
        return product;
    }

    /**
     * Returns the price of the dress.
     *
     * @return The dress price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the quantity of dresses requested in this transfer.
     *
     * @return The requested quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the current status of the transfer (Pending/Approved/Rejected).
     *
     * @return The current status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the status of the transfer.
     * Valid status values are: Pending, Approved, or Rejected.
     *
     * @param status The new status for the transfer
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the inventory transfer.
     * Includes all transfer details formatted for display.
     *
     * @return A formatted string containing all transfer details
     */
    @Override
    public String toString() {
        return "Transfer ID: " + transferId +
                ", From Store: " + fromStoreId +
                ", To Store: " + toStoreId +
                ", Product: " + product +
                ", Price: $" + price +
                ", Quantity: " + quantity +
                ", Status: " + status;
    }
}