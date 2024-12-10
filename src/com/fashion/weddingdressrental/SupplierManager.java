package com.fashion.weddingdressrental;

import java.io.*;
import java.util.*;

public class SupplierManager {
    private static final String SUPPLIER_FILE = "suppliers.txt";
    private static final String PRODUCT_FILE = "submittedProducts.txt";
    private static final String AGREEMENT_FILE = "agreements.txt";

    private final Map<String, Supplier> suppliers;
    private final Set<String> agreements;
    private final InventoryManager inventoryManager;

    public SupplierManager(InventoryManager inventoryManager) {
        suppliers = new HashMap<>();
        agreements = new HashSet<>();
        this.inventoryManager = inventoryManager;
        loadSuppliersFromFile();
        loadAgreementsFromFile();
    }

    // Register Supplier
    public String registerSupplier(String companyName, String email, String phone, String categories) {
        String supplierId = "SUP-" + (int) (Math.random() * 1000);
        Supplier supplier = new Supplier(supplierId, companyName, email, phone, categories);
        suppliers.put(supplierId, supplier);
        saveSuppliersToFile();
        return supplierId;
    }

    // Submit Product
    public void submitProduct(String supplierId, String storeId, int quantity, double price, String deliveryEstimate) {
        if (!agreementExists(supplierId, storeId)) {
            System.out.println("Error: No agreement exists between Supplier " + supplierId + " and Store " + storeId);
            return;
        }

        String dressId = "D-" + (int)(Math.random() * 10000);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE, true))) {
            writer.write(String.join(",", supplierId, storeId, dressId, String.valueOf(quantity),
                    String.valueOf(price), deliveryEstimate));
            writer.newLine();
            System.out.println("Product submitted successfully with Dress ID: " + dressId);
        } catch (IOException e) {
            System.out.println("Error saving product: " + e.getMessage());
        }

        inventoryManager.addInventory(storeId, dressId, "Available", price, quantity);
        System.out.println("Product added to Store Inventory.");
    }

    // Check Agreement
    public boolean agreementExists(String supplierId, String storeId) {
        return agreements.contains(supplierId + "-" + storeId);
    }

    // Register Agreement
    public void registerAgreement(String supplierId, String storeId) {
        if (agreementExists(supplierId, storeId)) {
            System.out.println("Agreement already exists.");
            return;
        }

        agreements.add(supplierId + "-" + storeId);
        saveAgreementsToFile();
        System.out.println("Agreement registered successfully.");
    }

    // View Submitted Products
    public void viewSubmittedProducts(String supplierId) {
        boolean found = false;
        System.out.println("\n--- Submitted Products ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[0].equals(supplierId)) {
                    System.out.printf("Product: %s | Quantity: %s | Price: $%s | Delivery: %s%n",
                            parts[2], parts[3], parts[4], parts[5]);
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
        if (!found) {
            System.out.println("No products submitted.");
        }
    }

    // Load Files
    private void loadSuppliersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SUPPLIER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    suppliers.put(parts[0], new Supplier(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading suppliers: " + e.getMessage());
        }
    }

    private void loadAgreementsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(AGREEMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                agreements.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading agreements: " + e.getMessage());
        }
    }

    // Save Files
    private void saveSuppliersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SUPPLIER_FILE))) {
            for (Supplier supplier : suppliers.values()) {
                writer.write(String.join(",", supplier.getSupplierId(), supplier.getCompanyName(),
                        supplier.getEmail(), supplier.getPhone(), supplier.getCategories()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving suppliers: " + e.getMessage());
        }
    }

    private void saveAgreementsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AGREEMENT_FILE))) {
            for (String agreement : agreements) {
                writer.write(agreement);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving agreements: " + e.getMessage());
        }
    }

 // Check if Supplier Exists
    public boolean exists(String supplierId) {
        return suppliers.containsKey(supplierId);
    }

}
