package com.fashion.weddingdressrental;

public class InventoryTransfer {

    private final String transferId;
    private final String fromStoreId;   // Requesting Store
    private final String toStoreId;     // Providing Store
    private final String product;       // Dress ID
    private final double price;         // Product Price
    private final int quantity;         // Requested Quantity
    private String status;              // Pending/Approved/Rejected

    // Correct Constructor
    public InventoryTransfer(String transferId, String fromStoreId, String toStoreId, String product, double price, int quantity, String status) {
        this.transferId = transferId;
        this.fromStoreId = fromStoreId;
        this.toStoreId = toStoreId;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    // Getters
    public String getTransferId() {
        return transferId;
    }

    public String getFromStoreId() {
        return fromStoreId;
    }

    public String getToStoreId() {
        return toStoreId;
    }

    public String getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    // Override toString for Display
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
