package com.fashion.weddingdressrental;

import java.io.*;
import java.util.*;

public class InventoryTransferManager {
    private static final String FILE_PATH = "inventory_transfers.txt";
    private final List<InventoryTransfer> transfers;
    private final InventoryManager inventoryManager;

    // Constructor
    public InventoryTransferManager(InventoryManager inventoryManager) {
        transfers = new ArrayList<>();
        this.inventoryManager = inventoryManager;
        loadTransfersFromFile();
    }

   
 // Request Inventory Transfer
    public void requestTransfer(String fromStoreId, String toStoreId, String product, double price, int quantity) {
        // Correct check: Validate if the `To Store` has enough inventory
        if (inventoryManager.getDressQuantity(toStoreId, product) < quantity) {
            System.out.println("Error: Insufficient inventory in the providing store.");
            return;
        }

        // Create transfer request
        String transferId = "INV-" + (int) (Math.random() * 1000);
        InventoryTransfer transfer = new InventoryTransfer(transferId, fromStoreId, toStoreId, product,price, quantity, "Pending");
        transfers.add(transfer);
        saveTransfersToFile();
        System.out.println("Inventory transfer request submitted!");
    }


    // View Pending Requests for a Specific Store
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

    // Approve Transfer
    public void approveTransfer(String toStoreId, String transferId) {
        for (InventoryTransfer transfer : transfers) {
            if (transfer.getTransferId().equals(transferId)
                && transfer.getToStoreId().equals(toStoreId)
                && transfer.getStatus().equals("Pending")) {

                String fromStoreId = transfer.getFromStoreId(); // Requesting Store
                String product = transfer.getProduct();
                int quantity = transfer.getQuantity();

                // Check if the providing store (toStoreId) has enough inventory
                if (!inventoryManager.deductInventory(toStoreId, product, quantity)) {
                    System.out.println("Error: Insufficient inventory for dress ID " + product + " in store " + toStoreId);
                    return;
                }

                // Add inventory to the requesting store
                double price = transfer.getPrice();
                inventoryManager.addInventory(fromStoreId, product, "Available", price, quantity);

                transfer.setStatus("Approved");
                saveTransfersToFile();
                System.out.println("Transfer approved and inventory updated!");
                return;
            }
        }
        System.out.println("Transfer not found or already processed.");
    }

    // Reject Transfer
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

    // View All Transfers (For Admin Use)
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

    // Load Transfers from File
    private void loadTransfersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {  // Corrected to expect 7 fields
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
    


    // Save Transfers to File
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
