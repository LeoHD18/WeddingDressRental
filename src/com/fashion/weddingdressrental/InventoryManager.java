package com.fashion.weddingdressrental;


import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, InventoryItem> inventory;

    public InventoryManager() {
        inventory = new HashMap<>();
        // Adding sample inventory items
        inventory.put("DRESS-001", new InventoryItem("DRESS-001", "Available"));
        inventory.put("DRESS-002", new InventoryItem("DRESS-002", "Rented"));
    }

    public InventoryItem findDressById(String dressId) {
        return inventory.get(dressId);
    }

    public void updateDressStatus(String dressId, String newStatus) throws Exception {
        InventoryItem item = findDressById(dressId);
        if (item == null) {
            throw new Exception("Dress not found in inventory.");
        }

        item.setStatus(newStatus);
        System.out.println("Status of dress " + dressId + " updated to " + newStatus);

        // Handle specific cases
        if ("Undergoing Repair".equals(newStatus)) {
            notifyRepairTeam(item);
        } else if ("Retired".equals(newStatus)) {
            removeFromInventory(dressId);
        }
    }

    private void notifyRepairTeam(InventoryItem item) {
        item.setTentativeAvailabilityDate("2023-12-01"); // Example tentative date
        System.out.println("Repair team notified for dress " + item.getDressId() +
                ". Tentative availability date set to " + item.tentativeAvailabilityDate);
    }

    private void removeFromInventory(String dressId) {
        inventory.remove(dressId);
        System.out.println("Dress " + dressId + " marked as retired and removed from future availability.");
    }

    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        inventory.forEach((id, item) -> System.out.println(item));
    }
}
