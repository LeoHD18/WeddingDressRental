package com.fashion.weddingdressrental;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, InventoryItem> inventory;
    private static final String FILE_PATH = "C:\\Users\\yvarun79\\Desktop\\WeddingDressRental\\src\\com\\fashion\\weddingdressrental\\inventory.txt";

    public InventoryManager() {
        inventory = new HashMap<>();
        loadInventoryFromFile();
    }

    // Load inventory data from the file
    private void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {  // Ensure the file includes price information
                    String dressId = parts[0];
                    String status = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    inventory.put(dressId, new InventoryItem(dressId, status, price));
                }
            }
            System.out.println("Inventory loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading inventory from file: " + e.getMessage());
        }
    }

    // Save updated inventory data to the file
    public void saveInventoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (InventoryItem item : inventory.values()) {
                writer.write(item.getDressId() + "," + item.getStatus() + "," + item.getPrice());
                writer.newLine();
            }
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory to file: " + e.getMessage());
        }
    }

    // Find a dress in the inventory by its ID
    public InventoryItem findDressById(String dressId) {
        return inventory.get(dressId);
    }

    // Update the status of a dress
    public void updateDressStatus(String dressId, String newStatus) throws Exception {
        InventoryItem item = findDressById(dressId);
        if (item == null) {
            throw new Exception("Dress not found in inventory.");
        }

        item.setStatus(newStatus);
        System.out.println("Status of dress " + dressId + " updated to " + newStatus);

        // Handle specific cases for status updates
        if ("Undergoing Repair".equals(newStatus)) {
            notifyRepairTeam(item);
        } else if ("Retired".equals(newStatus)) {
            removeFromInventory(dressId);
        }

        // Save changes to file after updating status
        saveInventoryToFile();
    }

    // Notify repair team when a dress is set to "Undergoing Repair"
    private void notifyRepairTeam(InventoryItem item) {
        item.setTentativeAvailabilityDate("2023-12-01"); // Example date
        System.out.println("Repair team notified for dress " + item.getDressId() +
                ". Tentative availability date set to " + item.getTentativeAvailabilityDate());
    }

    // Remove a dress from inventory if it's retired
    private void removeFromInventory(String dressId) {
        inventory.remove(dressId);
        System.out.println("Dress " + dressId + " marked as retired and removed from future availability.");
        saveInventoryToFile();
    }

    // Display all items in the inventory
    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        inventory.forEach((id, item) -> System.out.println(item));
    }
}
