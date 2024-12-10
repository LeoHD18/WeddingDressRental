package com.fashion.weddingdressrental;

import java.io.*;
import java.util.*;

/**
 * Manages store operations in the wedding dress rental system.
 * This class handles store registration, data persistence, and store information retrieval.
 * Stores are saved to and loaded from a text file for persistent storage.
 */
public class StoreManager {

    private static final String FILE_PATH = "stores.txt";
    private final Map<String, Store> stores;

    /**
     * Constructs a new StoreManager.
     * Initializes the stores collection and loads existing store data from file.
     */
    public StoreManager() {
        stores = new HashMap<>();
        loadStoresFromFile();
    }

    /**
     * Registers a new store in the system.
     * Creates a new Store object with the provided details and saves it to persistent storage.
     *
     * @param storeId     The unique identifier for the store
     * @param storeName   The name of the store
     * @param location    The physical location of the store
     * @param contactInfo The contact information for the store
     */
    public void registerStore(String storeId, String storeName, String location, String contactInfo) {
        Store store = new Store(storeId, storeName, location, contactInfo);
        stores.put(storeId, store);
        saveStoresToFile();
        System.out.println("Store registered successfully!");
    }

    /**
     * Displays information about all registered stores.
     * If no stores are registered, displays an appropriate message.
     */
    public void viewStores() {
        if (stores.isEmpty()) {
            System.out.println("No stores found.");
        } else {
            stores.values().forEach(System.out::println);
        }
    }

    /**
     * Checks if a store with the specified ID exists in the system.
     *
     * @param storeId The ID of the store to check
     * @return true if the store exists, false otherwise
     */
    public boolean exists(String storeId) {
        return stores.containsKey(storeId);
    }

    /**
     * Loads store data from the persistent storage file.
     * Each line in the file represents a store with comma-separated values.
     * If an error occurs during loading, it will be logged to the console.
     */
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

    /**
     * Saves all store data to the persistent storage file.
     * Each store is written as a comma-separated line of values.
     * If an error occurs during saving, it will be logged to the console.
     */
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