package com.fashion.weddingdressrental;


import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, InventoryItem> inventory;

    public InventoryManager() {
        inventory = new HashMap<>();
        // Adding sample inventory items
//        inventory.put("DRESS-001", new InventoryItem("DRESS-001", "Available"));
//        inventory.put("DRESS-002", new InventoryItem("DRESS-002", "Rented"));
        
        // Adding sample inventory items as Dress objects
        inventory.put("DRESS-001", new Dress("DRESS-001"));
        inventory.put("DRESS-002", new Dress("DRESS-002"));
    }

//    public InventoryItem findDressById(String dressId) {
//        return inventory.get(dressId);
//    }
    
    public Dress findDressById(String dressId) {
        InventoryItem item = inventory.get(dressId);
        if (item instanceof Dress) {
            return (Dress) item;
        }
        return null;
    }
    
    public InventoryItem findItemById(String itemId) {
        return inventory.get(itemId); // Return the InventoryItem directly
    }
    
    public void updateDressStatus(String dressId, String newStatus) throws Exception {
        Dress dress = findDressById(dressId);
        if (dress == null) {
            throw new Exception("Dress not found in inventory.");
        }

        dress.setStatus(newStatus);
        System.out.println("Status of dress " + dressId + " updated to " + newStatus);

        // Handle specific cases
        if ("Undergoing Repair".equals(newStatus)) {
            notifyRepairTeam(dress);
        } else if ("Retired".equals(newStatus)) {
            removeFromInventory(dressId);
        }
    }

    private void notifyRepairTeam(Dress dress) {
        dress.setTentativeAvailabilityDate("2023-12-01"); // Example tentative date
        System.out.println("Repair team notified for dress " + dress.getDressID() +
                ". Tentative availability date set to " + dress.getTentativeAvailabilityDate());
    }

    private void removeFromInventory(String dressId) {
        inventory.remove(dressId);
        System.out.println("Dress " + dressId + " marked as retired and removed from future availability.");
    }

    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        for (InventoryItem item : inventory.values()) {
            if (item instanceof Dress) {
                System.out.println(item);
            }
        }
    }

}
