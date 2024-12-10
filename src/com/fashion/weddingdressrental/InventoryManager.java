package com.fashion.weddingdressrental;

import java.io.*;
import java.util.*;

/**
 * Manages the inventory system for the wedding dress rental application.
 * This class handles all inventory-related operations including tracking dress quantities,
 * managing store inventories, and handling dress status updates.
 * It provides functionality for adding, removing, and updating inventory items
 * across multiple store locations.
 */
public class InventoryManager {
    private Map<String, InventoryItem> inventory;
    private Map<String, Map<String, InventoryItem>> storeInventories;
    private static final String FILE_PATH = "inventory.txt";

    /**
     * Constructs a new InventoryManager.
     * Initializes the inventory maps and loads existing inventory data from file.
     */
    public InventoryManager() {
        inventory = new HashMap<>();
        storeInventories = new HashMap<>();
        loadInventoryFromFile();
    }

    /**
     * Adds or updates inventory items for a specific store.
     * If the item already exists, the quantity is updated. If it's new, a new item is created.
     *
     * @param storeId  The ID of the store
     * @param dressId  The ID of the dress
     * @param status   The current status of the dress
     * @param price    The price of the dress
     * @param quantity The quantity to add
     */
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

    /**
     * Reduces the quantity of a dress in a store's inventory.
     *
     * @param storeId  The ID of the store
     * @param dressId  The ID of the dress
     * @param quantity The quantity to deduct
     * @return true if deduction was successful, false if insufficient inventory
     */
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

    /**
     * Displays the inventory for a specific store.
     *
     * @param storeId The ID of the store whose inventory to display
     */
    public void displayStoreInventory(String storeId) {
        if (storeInventories.containsKey(storeId)) {
            System.out.println("\n--- Inventory for Store " + storeId + " ---");
            storeInventories.get(storeId).forEach((id, item) -> System.out.println(item));
        } else {
            System.out.println("Store ID " + storeId + " not found.");
        }
    }

    /**
     * Gets the quantity of a specific dress in a store.
     *
     * @param storeId The ID of the store
     * @param dressId The ID of the dress
     * @return The quantity available, or 0 if not found
     */
    public int getDressQuantity(String storeId, String dressId) {
        if (storeInventories.containsKey(storeId) && storeInventories.get(storeId).containsKey(dressId)) {
            return storeInventories.get(storeId).get(dressId).getQuantity();
        }
        return 0;
    }

    /**
     * Gets the price of a specific dress in a store.
     *
     * @param storeId The ID of the store
     * @param dressId The ID of the dress
     * @return The price of the dress, or -1.0 if not found
     */
    public double getProductPrice(String storeId, String dressId) {
        if (storeInventories.containsKey(storeId) && storeInventories.get(storeId).containsKey(dressId)) {
            return storeInventories.get(storeId).get(dressId).getPrice();
        }
        return -1.0; // Indicate product not found
    }

    /**
     * Loads inventory data from the file system.
     * Handles file reading and data parsing, including error checking.
     */
    private void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 5) {
                    String storeId = parts[0];
                    String dressId = parts[1];
                    String status = parts[2];

                    try {
                        double price = Double.parseDouble(parts[3]);
                        int quantity = Integer.parseInt(parts[4]);

                        String tentativeDate = parts.length == 6 ? parts[5] : null;

                        storeInventories.putIfAbsent(storeId, new HashMap<>());
                        InventoryItem item = new InventoryItem(dressId, status, price, quantity, tentativeDate);
                        storeInventories.get(storeId).put(dressId, item);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid data format in inventory file for dress ID: " + dressId);
                        continue;
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

    /**
     * Saves the current inventory state to the file system.
     */
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

    /**
     * Finds a dress in the global inventory.
     *
     * @param dressId The ID of the dress to find
     * @return The InventoryItem if found, null otherwise
     */
    public InventoryItem findDressById(String dressId) {
        return inventory.get(dressId);
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
     * Sets a tentative availability date for the dress.
     *
     * @param item The InventoryItem requiring repair
     */
    private void notifyRepairTeam(InventoryItem item) {
        item.setTentativeAvailabilityDate("2024-01-01");
        System.out.println("Repair team notified for dress " + item.getDressId() +
                ". Tentative availability date set to " + item.getTentativeAvailabilityDate());
    }

    /**
     * Removes a dress from the global inventory.
     *
     * @param dressId The ID of the dress to remove
     */
    private void removeFromInventory(String dressId) {
        inventory.remove(dressId);
        System.out.println("Dress " + dressId + " marked as retired and removed.");
        saveInventoryToFile();
    }

    /**
     * Displays all items in the global inventory.
     */
    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        inventory.forEach((id, item) -> System.out.println(item));
    }

    /**
     * Retrieves a list of all available dresses across all stores.
     *
     * @return List of available InventoryItems
     */
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

    /**
     * Finds a specific dress in a specific store's inventory.
     *
     * @param storeId The ID of the store
     * @param dressId The ID of the dress
     * @return The InventoryItem if found, null otherwise
     */
    public InventoryItem findDressByIdInStore(String storeId, String dressId) {
        if (storeInventories.containsKey(storeId) && storeInventories.get(storeId).containsKey(dressId)) {
            return storeInventories.get(storeId).get(dressId);
        }
        System.out.println("Dress " + dressId + " not found in Store " + storeId);
        return null;
    }

    /**
     * Removes a dress from a specific store's inventory.
     *
     * @param storeId The ID of the store
     * @param dressId The ID of the dress to remove
     */
    private void removeFromStoreInventory(String storeId, String dressId) {
        if (storeInventories.containsKey(storeId)) {
            storeInventories.get(storeId).remove(dressId);
            System.out.println("Dress " + dressId + " removed from store " + storeId + ".");
        } else {
            System.out.println("Store ID " + storeId + " not found.");
        }
    }

    /**
     * Updates the status of a dress in a specific store's inventory.
     *
     * @param storeId   The ID of the store
     * @param dressId   The ID of the dress
     * @param newStatus The new status to set
     * @throws Exception if the dress is not found in the specified store
     */
    public void updateDressStatusInStore(String storeId, String dressId, String newStatus) throws Exception {
        InventoryItem item = findDressByIdInStore(storeId, dressId);

        if (item == null) {
            throw new Exception("Dress not found in the specified store.");
        }

        item.setStatus(newStatus);
        System.out.println("Status of dress " + dressId + " updated to " + newStatus);

        if ("Undergoing Repair".equalsIgnoreCase(newStatus)) {
            notifyRepairTeam(item);
        } else if ("Retired".equalsIgnoreCase(newStatus)) {
            removeFromStoreInventory(storeId, dressId);
        }

        saveInventoryToFile();
    }
}