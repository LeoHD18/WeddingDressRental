package com.fashion.weddingdressrental;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, InventoryItem> inventory;

    public InventoryManager() {
        this.inventory = new HashMap<>();
        // Sample data
        inventory.put("DRESS-001", new InventoryItem("DRESS-001", "Available"));
        inventory.put("DRESS-002", new InventoryItem("DRESS-002", "Rented"));
    }

    public InventoryItem findDressById(String dressId) {
        return inventory.get(dressId);
    }

    public void updateDressStatus(String dressId, String newStatus) throws Exception {
        InventoryItem item = findDressById(dressId);
        if (item != null) {
            item.setStatus(newStatus);
            System.out.println("Updated status of dress " + dressId + " to " + newStatus);
            
            // Handle special cases
            if ("Undergoing Repair".equals(newStatus)) {
                System.out.println("Notify repair team. Set a tentative availability date.");
                item.setTentativeAvailabilityDate("2023-12-01"); // Example tentative date
            } else if ("Retired".equals(newStatus)) {
                System.out.println("Remove from future availability and notify inventory management.");
                inventory.remove(dressId); // Removes from available inventory
            }
        } else {
            throw new Exception("Dress not found in inventory.");
        }
    }

    public void displayInventory() {
        inventory.forEach((id, item) -> System.out.println(item));
    }
}

class InventoryItem {
    private String dressId;
    private String status;
    private String tentativeAvailabilityDate;

    public InventoryItem(String dressId, String status) {
        this.dressId = dressId;
        this.status = status;
    }

    public String getDressId() {
        return dressId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTentativeAvailabilityDate(String date) {
        this.tentativeAvailabilityDate = date;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "dressId='" + dressId + '\'' +
                ", status='" + status + '\'' +
                ", tentativeAvailabilityDate='" + tentativeAvailabilityDate + '\'' +
                '}';
    }
}
