package com.fashion.weddingdressrental;

import java.io.*;
import java.util.*;

/**
 * Manages the inventory system for the wedding dress rental application.
 * This class handles all inventory-related operations including tracking dress quantities,
 * managing store inventories, and handling dress status updates.
 */
public class InventoryManager {
    private Map<String, InventoryItem> inventory;
    private Map<String, Map<String, InventoryItem>> storeInventories;
    private static final String FILE_PATH = "inventory.txt";

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

    public boolean deductInventory(String storeId, String dressId, int quantity) {
        if (storeInventories.containsKey(storeId)) {
            Map<String, InventoryItem> inventory = storeInventories.get(storeId);

            if (inventory.containsKey(dressId)) {
                InventoryItem item = inventory.get(dressId);
                if (item.getQuantity() >= quantity) {
                    item.setQuantity(item.getQuantity() - quantity);
                    
                    if (item.getQuantity() <= 0) {
                        inventory.remove(dressId);
                        System.out.println("Product " + dressId + " removed from store " + storeId + " due to zero quantity.");
                    }
                    
                    saveInventoryToFile();
                    System.out.println("Inventory updated successfully!");
                    return true;
                }
            }
        }
        System.out.println("Error: Insufficient inventory for dress ID " + dressId + " in store " + storeId);
        return false;
    }

    public void displayStoreInventory(String storeId) {
        if (storeInventories.containsKey(storeId)) {
            System.out.println("\n--- Inventory for Store " + storeId + " ---");
            storeInventories.get(storeId).forEach((id, item) -> System.out.println(item));
        } else {
            System.out.println("Store ID " + storeId + " not found.");
        }
    }

    public int getDressQuantity(String storeId, String dressId) {
        if (storeInventories.containsKey(storeId) && storeInventories.get(storeId).containsKey(dressId)) {
            return storeInventories.get(storeId).get(dressId).getQuantity();
        }
        return 0;
    }

    public double getProductPrice(String storeId, String dressId) {
        if (storeInventories.containsKey(storeId) && storeInventories.get(storeId).containsKey(dressId)) {
            return storeInventories.get(storeId).get(dressId).getPrice();
        }
        return -1.0;
    }

    private void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String storeId = parts[0];
                    String dressId = parts[1];
                    String status = parts[2];
                    try {
                        double price = Double.parseDouble(parts[3]);
                        int quantity = Integer.parseInt(parts[4]);
                        
                        storeInventories.putIfAbsent(storeId, new HashMap<>());
                        InventoryItem item = new InventoryItem(dressId, status, price, quantity, null);
                        storeInventories.get(storeId).put(dressId, item);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid data format in inventory file for dress ID: " + dressId);
                    }
                } else {
                    System.out.println("Skipping invalid line in file: " + line);
                }
            }
            System.out.println("Inventory loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading inventory from file: " + e.getMessage());
        }
    }

    public void saveInventoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Map<String, InventoryItem>> storeEntry : storeInventories.entrySet()) {
                String storeId = storeEntry.getKey();
                for (InventoryItem item : storeEntry.getValue().values()) {
                    writer.write(String.format("%s,%s,%s,%.1f,%d",
                        storeId,
                        item.getDressId(),
                        item.getStatus(),
                        item.getPrice(),
                        item.getQuantity()
                    ));
                    writer.newLine();
                }
            }
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory to file: " + e.getMessage());
        }
    }

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

    public InventoryItem findDressByIdInStore(String storeId, String dressId) {
        if (storeInventories.containsKey(storeId) && storeInventories.get(storeId).containsKey(dressId)) {
            return storeInventories.get(storeId).get(dressId);
        }
        System.out.println("Dress " + dressId + " not found in Store " + storeId);
        return null;
    }

    public void updateDressStatusInStore(String storeId, String dressId, String newStatus) {
        if (storeInventories.containsKey(storeId)) {
            Map<String, InventoryItem> storeInventory = storeInventories.get(storeId);
            if (storeInventory.containsKey(dressId)) {
                InventoryItem item = storeInventory.get(dressId);
                item.setStatus(newStatus);
                System.out.println("Status of dress " + dressId + " updated to " + newStatus);

                if ("Retired".equalsIgnoreCase(newStatus) || item.getQuantity() <= 0) {
                    storeInventory.remove(dressId);
                    System.out.println("Dress " + dressId + " removed from inventory due to status change or zero quantity.");
                }

                saveInventoryToFile();
            } else {
                System.out.println("Dress ID " + dressId + " not found in store " + storeId);
            }
        } else {
            System.out.println("Store ID " + storeId + " not found.");
        }
    }
    
    public InventoryItem findDressById(String dressId) {
        // Search through all store inventories for the dress
        for (Map<String, InventoryItem> storeInventory : storeInventories.values()) {
            if (storeInventory.containsKey(dressId)) {
                return storeInventory.get(dressId);
            }
        }
        // If dress not found in any store inventory, check the global inventory
        if (inventory.containsKey(dressId)) {
            return inventory.get(dressId);
        }
        return null;
    }

    /**
     * Updates the status of a dress in the global inventory.
     *
     * @param dressId   The ID of the dress to update
     * @param newStatus The new status to set
     * @throws Exception if the dress is not found
     */
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

    /**
     * Notifies the repair team about a dress needing repair.
     *
     * @param item The InventoryItem requiring repair
     */
    private void notifyRepairTeam(InventoryItem item) {
        System.out.println("Repair team notified for dress " + item.getDressId());
    }

    /**
     * Removes a dress from the global inventory.
     *
     * @param dressId The ID of the dress to remove
     */
    private void removeFromInventory(String dressId) {
        // Remove from global inventory
        inventory.remove(dressId);
        
        // Remove from all store inventories
        for (Map<String, InventoryItem> storeInventory : storeInventories.values()) {
            storeInventory.remove(dressId);
        }
        System.out.println("Dress " + dressId + " marked as retired and removed.");
        saveInventoryToFile();
    }
}