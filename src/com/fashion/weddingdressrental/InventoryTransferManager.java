package com.fashion.weddingdressrental;

import java.io.*;
import java.util.*;

/**
 * Manages inventory transfers between stores in the wedding dress rental system.
 * This class handles the creation, approval, rejection, and tracking of inventory
 * transfer requests between stores. It maintains persistent storage of transfer
 * records and coordinates with the InventoryManager for stock updates.
 */
public class InventoryTransferManager {
    private static final String FILE_PATH = "inventory_transfers.txt";
    private final List<InventoryTransfer> transfers;
    private final InventoryManager inventoryManager;

    /**
     * Constructs a new InventoryTransferManager with the specified InventoryManager.
     * Initializes the transfers list and loads existing transfer records from file.
     *
     * @param inventoryManager The InventoryManager instance to handle inventory operations
     */
    public InventoryTransferManager(InventoryManager inventoryManager) {
        transfers = new ArrayList<>();
        this.inventoryManager = inventoryManager;
        loadTransfersFromFile();
    }

    /**
     * Creates a new inventory transfer request between stores.
     * Validates availability in the providing store before creating the request.
     *
     * @param fromStoreId The ID of the store requesting the transfer
     * @param toStoreId   The ID of the store providing the inventory
     * @param product     The ID of the dress being transferred
     * @param price       The price of the dress
     * @param quantity    The quantity requested
     */
    public void requestTransfer(String fromStoreId, String toStoreId, String product, double price, int quantity) {
        if (inventoryManager.getDressQuantity(toStoreId, product) < quantity) {
            System.out.println("Error: Insufficient inventory in the providing store.");
            return;
        }

        String transferId = "INV-" + (int) (Math.random() * 1000);
        InventoryTransfer transfer = new InventoryTransfer(transferId, fromStoreId, toStoreId, product, price, quantity, "Pending");
        transfers.add(transfer);
        saveTransfersToFile();
        System.out.println("Inventory transfer request submitted!");
    }

    /**
     * Displays pending transfer requests for a specific store.
     * Shows all requests where the specified store is the provider and status is pending.
     *
     * @param storeId The ID of the store to check pending requests for
     */
    public void viewPendingRequestsForStore(String storeId) {
        boolean found = false;
        System.out.println("\n--- Pending Transfer Requests for Store: " + storeId + " ---");
        for (InventoryTransfer transfer : transfers) {
            if (transfer.getToStoreId().equals(storeId) && transfer.getStatus().equals("Pending")) {
                System.out.println(transfer);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No pending transfer requests.");
        }
    }

    /**
     * Approves a pending transfer request and updates inventory accordingly.
     * Verifies availability in the providing store before processing the transfer.
     *
     * @param toStoreId  The ID of the providing store
     * @param transferId The ID of the transfer request to approve
     */
    public void approveTransfer(String storeId, String transferId) {
        for (InventoryTransfer transfer : transfers) {
            if (transfer.getTransferId().equals(transferId)
                && transfer.getToStoreId().equals(storeId)
                && transfer.getStatus().equals("Pending")) {
                
                // Check if providing store (toStore) has enough inventory
                String providingStoreId = transfer.getToStoreId();
                String dressId = transfer.getProduct();
                int quantity = transfer.getQuantity();
                double price = transfer.getPrice();

                // Check and deduct from providing store
                if (!inventoryManager.deductInventory(providingStoreId, dressId, quantity)) {
                    System.out.println("Error: Insufficient inventory in the providing store.");
                    return;
                }

                // Add to requesting store
                inventoryManager.addInventory(
                    transfer.getFromStoreId(),  // Requesting store
                    dressId,
                    "Available",
                    price,
                    quantity
                );

                transfer.setStatus("Approved");
                saveTransfersToFile();
                System.out.println("Transfer approved and inventory updated!");
                return;
            }
        }
        System.out.println("Transfer not found or already processed.");
    }


    /**
     * Rejects a pending transfer request.
     * Only allows rejection if the request is pending and matches the specified store.
     *
     * @param storeId    The ID of the providing store
     * @param transferId The ID of the transfer request to reject
     */
    public void rejectTransfer(String storeId, String transferId) {
        for (InventoryTransfer transfer : transfers) {
            if (transfer.getTransferId().equals(transferId)
                && transfer.getToStoreId().equals(storeId)
                && transfer.getStatus().equals("Pending")) {

                transfer.setStatus("Rejected");
                saveTransfersToFile();
                System.out.println("Transfer rejected successfully!");
                return;
            }
        }
        System.out.println("Transfer not found or already processed.");
    }

    /**
     * Displays all transfer requests in the system.
     * For administrative use to view complete transfer history.
     */
    public void viewAllTransfers() {
        if (transfers.isEmpty()) {
            System.out.println("No inventory transfers found.");
        } else {
            System.out.println("\n--- All Inventory Transfers ---");
            for (InventoryTransfer transfer : transfers) {
                System.out.println(transfer);
            }
        }
    }

    /**
     * Loads transfer records from the file system.
     * Parses each line into an InventoryTransfer object and adds it to the transfers list.
     */
    private void loadTransfersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    transfers.add(new InventoryTransfer(
                        parts[0],                     // Transfer ID
                        parts[1],                     // From Store
                        parts[2],                     // To Store
                        parts[3],                     // Product Name
                        Double.parseDouble(parts[4]), // Price
                        Integer.parseInt(parts[5]),   // Quantity
                        parts[6]                      // Status
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading transfers: " + e.getMessage());
        }
    }

    /**
     * Saves all transfer records to the file system.
     * Writes each transfer as a comma-separated line of values.
     */
    private void saveTransfersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (InventoryTransfer transfer : transfers) {
                writer.write(String.join(",",
                    transfer.getTransferId(),
                    transfer.getFromStoreId(),
                    transfer.getToStoreId(),
                    transfer.getProduct(),
                    String.valueOf(transfer.getPrice()),
                    String.valueOf(transfer.getQuantity()),
                    transfer.getStatus()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving transfers: " + e.getMessage());
        }
    }
}