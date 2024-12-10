package com.fashion.weddingdressrental;

import java.io.*;
import java.util.*;

public class InventoryManager {
    private Map<String, InventoryItem> inventory;
    private Map<String, Map<String, InventoryItem>> storeInventories;
    private static final String FILE_PATH = "inventory.txt";

    // Constructor
    public InventoryManager() {
        inventory = new HashMap<>();
        storeInventories = new HashMap<>();
        loadInventoryFromFile();
    }

    public void addInventory(String storeId, String dressId, String status, double price, int quantity) {
        storeInventories.putIfAbsent(storeId, new HashMap<>());

        Map<String, InventoryItem> inventory = storeInventories.get(storeId);

        if (inventory.containsKey(dressId)) {
            InventoryItem existingItem = inventory.get(dressId);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            System.out.println("Inventory updated successfully! New quantity: " + existingItem.getQuantity());
        } else {
            InventoryItem newItem = new InventoryItem(dressId, status, price, quantity, null);
            inventory.put(dressId, newItem);
            System.out.println("New inventory item added successfully!");
        }
        saveInventoryToFile();
    }


    // Deduct Inventory
    public boolean deductInventory(String storeId, String dressId, int quantity) {
        if (storeInventories.containsKey(storeId)) {
            Map<String, InventoryItem> inventory = storeInventories.get(storeId);

            if (inventory.containsKey(dressId)) {
                InventoryItem item = inventory.get(dressId);
                if (item.getQuantity() >= quantity) {
                    item.setQuantity(item.getQuantity() - quantity);
                    System.out.println("Inventory deducted successfully!");
                    saveInventoryToFile();
                    return true;
                }
            }
        }
        System.out.println("Error: Insufficient inventory for dress ID " + dressId + " in store " + storeId);
        return false;
    }

//    public int getDressQuantity(String storeId, String dressId) {
//        if (storeInventories.containsKey(storeId) && storeInventories.get(storeId).containsKey(dressId)) {
//            return storeInventories.get(storeId).get(dressId).getQuantity();
//        }
//        return 0;
//    }

    
    public void displayStoreInventory(String storeId) {
        if (storeInventories.containsKey(storeId)) {
            System.out.println("\n--- Inventory for Store " + storeId + " ---");
            storeInventories.get(storeId).forEach((id, item) -> System.out.println(item));
        } else {
            System.out.println("Store ID " + storeId + " not found.");
        }
    }


    
 
 // Get Product Quantity from Store Inventory
    public int getDressQuantity(String storeId, String dressId) {
        if (storeInventories.containsKey(storeId) && storeInventories.get(storeId).containsKey(dressId)) {
            return storeInventories.get(storeId).get(dressId).getQuantity();
        }
        return 0;
    }

    // Get Product Price from Store Inventory
    public double getProductPrice(String storeId, String dressId) {
        if (storeInventories.containsKey(storeId) && storeInventories.get(storeId).containsKey(dressId)) {
            return storeInventories.get(storeId).get(dressId).getPrice();
        }
        return -1.0; // Indicate product not found
    }

    
 // Load Inventory Data from File
    private void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Validate expected format: storeId, dressId, status, price, quantity [, tentativeDate]
                if (parts.length >= 5) {
                    String storeId = parts[0];
                    String dressId = parts[1];
                    String status = parts[2];

                    // Ensure price and quantity are numeric
                    double price;
                    int quantity;
                    try {
                        price = Double.parseDouble(parts[3]);
                        quantity = Integer.parseInt(parts[4]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid data format in inventory file for dress ID: " + dressId);
                        continue;  // Skip this record if data is invalid
                    }

                    String tentativeDate = parts.length == 6 ? parts[5] : null;

                    // Add to store inventories
                    storeInventories.putIfAbsent(storeId, new HashMap<>());
                    InventoryItem item = new InventoryItem(dressId, status, price, quantity, tentativeDate);
                    storeInventories.get(storeId).put(dressId, item);
                } else {
                    System.out.println("Skipping invalid line in file: " + line);
                }
            }
            System.out.println("Inventory loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading inventory from file: " + e.getMessage());
        }
    }


 // Save Updated Inventory to File
    public void saveInventoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Map<String, InventoryItem>> storeEntry : storeInventories.entrySet()) {
                String storeId = storeEntry.getKey();
                for (InventoryItem item : storeEntry.getValue().values()) {
                    writer.write(storeId + "," + item.getDressId() + "," + item.getStatus() + "," 
                                 + item.getPrice() + "," + item.getQuantity() +
                                 (item.getTentativeAvailabilityDate() != null ? "," + item.getTentativeAvailabilityDate() : ""));
                    writer.newLine();
                }
            }
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory to file: " + e.getMessage());
        }
    }



    // Find Dress by ID
    public InventoryItem findDressById(String dressId) {
        return inventory.get(dressId);
    }

    // Update Dress Status
    public void updateDressStatus(String dressId, String newStatus) throws Exception {
        InventoryItem item = findDressById(dressId);
        if (item == null) {
            throw new Exception("Dress not found in inventory.");
        }

        item.setStatus(newStatus);
        System.out.println("Status of dress " + dressId + " updated to " + newStatus);

        if ("Undergoing Repair".equalsIgnoreCase(newStatus)) {
            notifyRepairTeam(item);
        } else if ("Retired".equalsIgnoreCase(newStatus)) {
            removeFromInventory(dressId);
        }
        saveInventoryToFile();
    }

    // Notify Repair Team
    private void notifyRepairTeam(InventoryItem item) {
        item.setTentativeAvailabilityDate("2024-01-01");
        System.out.println("Repair team notified for dress " + item.getDressId() +
                ". Tentative availability date set to " + item.getTentativeAvailabilityDate());
    }

    // Remove Dress from Inventory
    private void removeFromInventory(String dressId) {
        inventory.remove(dressId);
        System.out.println("Dress " + dressId + " marked as retired and removed.");
        saveInventoryToFile();
    }

    // Display All Items in Inventory
    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        inventory.forEach((id, item) -> System.out.println(item));
    }

    
 // Get Available Dresses from All Stores
    public List<InventoryItem> getAvailableDresses() {
        List<InventoryItem> availableDresses = new ArrayList<>();

        for (Map<String, InventoryItem> storeInventory : storeInventories.values()) {
            for (InventoryItem item : storeInventory.values()) {
                if ("Available".equalsIgnoreCase(item.getStatus()) && item.getQuantity() > 0) {
                    availableDresses.add(item);
                }
            }
        }
        return availableDresses;
    }


}
