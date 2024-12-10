package com.fashion.weddingdressrental;

import java.io.*;
import java.util.*;

public class StoreManager {

    private static final String FILE_PATH = "stores.txt";
    private final Map<String, Store> stores;

    public StoreManager() {
        stores = new HashMap<>();
        loadStoresFromFile();
    }

    // Register New Store
    public void registerStore(String storeId, String storeName, String location, String contactInfo) {
        Store store = new Store(storeId, storeName, location, contactInfo);
        stores.put(storeId, store);
        saveStoresToFile();
        System.out.println("Store registered successfully!");
    }

    // View All Stores
    public void viewStores() {
        if (stores.isEmpty()) {
            System.out.println("No stores found.");
        } else {
            stores.values().forEach(System.out::println);
        }
    }

    // Check if Store Exists
    public boolean exists(String storeId) {
        return stores.containsKey(storeId);
    }

    // Load Stores from File
    private void loadStoresFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String storeId = parts[0];
                    String storeName = parts[1];
                    String location = parts[2];
                    String contactInfo = parts[3];
                    stores.put(storeId, new Store(storeId, storeName, location, contactInfo));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading stores: " + e.getMessage());
        }
    }

    // Save Stores to File
    private void saveStoresToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Store store : stores.values()) {
                writer.write(String.join(",",
                        store.getStoreId(),
                        store.getStoreName(),
                        store.getLocation(),
                        store.getContactInfo()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving stores: " + e.getMessage());
        }
    }
}
