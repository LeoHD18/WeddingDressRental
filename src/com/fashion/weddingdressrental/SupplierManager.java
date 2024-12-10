package com.fashion.weddingdressrental;

import java.io.*;
import java.util.*;

/**
 * Manages suppliers, their products, and agreements with stores in the wedding dress rental system.
 * This class handles all supplier-related operations including registration, product submission,
 * agreement management, and data persistence.
 */
public class SupplierManager {
    private static final String SUPPLIER_FILE = "suppliers.txt";
    private static final String PRODUCT_FILE = "submittedProducts.txt";
    private static final String AGREEMENT_FILE = "agreements.txt";
    private static final String AGREEMENT_REQUEST_FILE = "agreements_requests.txt";

    private final Map<String, Supplier> suppliers;
    private final Map<String, Set<String>> supplierAgreements; // Supplier ID -> Set of Store IDs
    private final List<AgreementRequest> agreementRequests; // Pending Agreement Requests
    private final InventoryManager inventoryManager;

    /**
     * Constructs a new SupplierManager with the specified InventoryManager.
     * Initializes data structures and loads existing data from files.
     *
     * @param inventoryManager The InventoryManager instance to handle inventory-related operations
     */
    public SupplierManager(InventoryManager inventoryManager) {
        this.suppliers = new HashMap<>();
        this.supplierAgreements = new HashMap<>();
        this.agreementRequests = new ArrayList<>();
        this.inventoryManager = inventoryManager;
        loadSuppliersFromFile();
        loadAgreementsFromFile();
        loadAgreementRequestsFromFile();
    }

    /**
     * Registers a new supplier in the system.
     *
     * @param companyName The name of the supplier company
     * @param email The contact email of the supplier
     * @param phone The contact phone number of the supplier
     * @param categories The categories of products offered by the supplier
     * @return The generated supplier ID
     */
    public String registerSupplier(String companyName, String email, String phone, String categories) {
        String supplierId = "SUP-" + (int) (Math.random() * 1000);
        Supplier supplier = new Supplier(supplierId, companyName, email, phone, categories);
        suppliers.put(supplierId, supplier);
        saveSuppliersToFile();
        System.out.println("Supplier registered successfully with ID: " + supplierId);
        return supplierId;
    }

    /**
     * Submits a new product from a supplier to a store.
     * Validates the existence of an agreement between the supplier and store before submission.
     *
     * @param supplierId The ID of the supplier submitting the product
     * @param storeId The ID of the store receiving the product
     * @param quantity The quantity of the product being submitted
     * @param price The price per unit of the product
     * @param deliveryEstimate The estimated delivery time for the product
     */
    
    public void submitProduct(String supplierId, String storeId, int quantity, double price, String deliveryEstimate) {
        // Reload agreements before checking
        loadAgreementsFromFile();
        loadAgreementRequestsFromFile();

        if (!agreementExists(supplierId, storeId)) {
            System.out.println("Error: No agreement exists between Supplier " + supplierId + " and Store " + storeId);
            return;
        }

        String dressId = "D-" + (int) (Math.random() * 10000);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE, true))) {
            writer.write(String.join(",", supplierId, storeId, dressId, String.valueOf(quantity),
                    String.valueOf(price), deliveryEstimate));
            writer.newLine();
            System.out.println("Product submitted successfully with Dress ID: " + dressId);
        } catch (IOException e) {
            System.out.println("Error saving product: " + e.getMessage());
        }

        // Add to store inventory
        inventoryManager.addInventory(storeId, dressId, "Available", price, quantity);
        System.out.println("Product added to Store Inventory.");
    }

    /**
     * Creates a new agreement request between a supplier and a store.
     *
     * @param supplierId The ID of the supplier requesting the agreement
     * @param storeId The ID of the store for which the agreement is requested
     */
    public void createAgreementRequest(String supplierId, String storeId) {
        if (!suppliers.containsKey(supplierId)) {
            System.out.println("Error: Supplier not found.");
            return;
        }

        String requestId = "REQ-" + (int) (Math.random() * 1000);
        AgreementRequest request = new AgreementRequest(requestId, supplierId, storeId, "Pending");
        agreementRequests.add(request);
        saveAgreementRequestsToFile();
        System.out.println("Agreement request created successfully. Request ID: " + requestId);
    }

    /**
     * Approves a pending agreement request.
     * Creates the agreement between the supplier and store if the request exists and is pending.
     *
     * @param requestId The ID of the agreement request to approve
     */
    public void approveAgreementRequest(String requestId) {
        for (AgreementRequest request : agreementRequests) {
            if (request.getRequestId().equals(requestId) && request.getStatus().equals("Pending")) {
                // Update the request status
                request.setStatus("Approved");
                
                // Update in-memory agreement data
                String supplierId = request.getSupplierId();
                String storeId = request.getStoreId();
                supplierAgreements.putIfAbsent(supplierId, new HashSet<>());
                supplierAgreements.get(supplierId).add(storeId);
                
                // Save both changes to files
                saveAgreementRequestsToFile();
                saveAgreementsToFile();
                
                // Optional: Reload from files to ensure consistency
                loadAgreementsFromFile();
                loadAgreementRequestsFromFile();
                
                System.out.println("Agreement request approved and agreement saved.");
                return;
            }
        }
        System.out.println("Agreement request not found or already processed.");
    }

 

    /**
     * Rejects a pending agreement request.
     *
     * @param requestId The ID of the agreement request to reject
     */
    public void rejectAgreementRequest(String requestId) {
        for (AgreementRequest request : agreementRequests) {
            if (request.getRequestId().equals(requestId) && request.getStatus().equals("Pending")) {
                request.setStatus("Rejected");
                saveAgreementRequestsToFile();
                System.out.println("Agreement request rejected.");
                return;
            }
        }
        System.out.println("Agreement request not found or already processed.");
    }

    /**
     * Displays all agreement requests in the system.
     */
    public void viewAgreementRequests() {
        // Clear and reload requests
        loadAgreementRequestsFromFile();
        
        // Use a Set to store unique requests
        Set<String> seenRequests = new HashSet<>();
        List<AgreementRequest> uniqueRequests = new ArrayList<>();
        
        for (AgreementRequest request : agreementRequests) {
            // Create a unique key for each request
            String requestKey = request.getRequestId() + "-" + 
                              request.getSupplierId() + "-" + 
                              request.getStoreId();
                              
            // Only add if we haven't seen this request before
            if (!seenRequests.contains(requestKey)) {
                seenRequests.add(requestKey);
                uniqueRequests.add(request);
            }
        }
        
        // Replace the old list with deduplicated one
        agreementRequests.clear();
        agreementRequests.addAll(uniqueRequests);
        
        // Save the deduplicated list back to file
        saveAgreementRequestsToFile();
        
        if (agreementRequests.isEmpty()) {
            System.out.println("No agreement requests found.");
            return;
        }

        System.out.println("\n--- Agreement Requests ---");
        for (AgreementRequest request : agreementRequests) {
            System.out.println(request);
        }
    }
    
    
    /**
     * Checks if an agreement exists between a supplier and a store.
     *
     * @param supplierId The ID of the supplier
     * @param storeId The ID of the store
     * @return true if an agreement exists, false otherwise
     */
    // Also add debug to agreementExists method
    public boolean agreementExists(String supplierId, String storeId) {
        // First check in-memory data
        if (supplierAgreements.containsKey(supplierId) && 
            supplierAgreements.get(supplierId).contains(storeId)) {
            return true;
        }
        
        // Then check file data (in case memory is out of sync)
        try (BufferedReader reader = new BufferedReader(new FileReader(AGREEMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && 
                    parts[0].equals(supplierId) && 
                    parts[1].equals(storeId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking agreements: " + e.getMessage());
        }
        
        return false;
    }   

    /**
     * Displays all products submitted by a specific supplier.
     *
     * @param supplierId The ID of the supplier whose products to display
     */
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

    /**
     * Loads supplier data from the suppliers file.
     */
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

    /**
     * Loads agreement data from the agreements file.
     */
    private void loadAgreementsFromFile() {
        // First load from agreements file
        try (BufferedReader reader = new BufferedReader(new FileReader(AGREEMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                supplierAgreements.putIfAbsent(parts[0], new HashSet<>());
                supplierAgreements.get(parts[0]).add(parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Error loading agreements: " + e.getMessage());
        }

        // Then load approved agreements from agreement requests
        try (BufferedReader reader = new BufferedReader(new FileReader(AGREEMENT_REQUEST_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[3].equals("Approved")) {
                    String supplierId = parts[1];
                    String storeId = parts[2];
                    supplierAgreements.putIfAbsent(supplierId, new HashSet<>());
                    supplierAgreements.get(supplierId).add(storeId);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading agreement requests: " + e.getMessage());
        }
    }


    /**
     * Loads agreement request data from the agreement requests file.
     */

private void loadAgreementRequestsFromFile() {
    agreementRequests.clear(); // Clear existing data
    Set<String> seenRequests = new HashSet<>();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(AGREEMENT_REQUEST_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                String requestKey = parts[0] + "-" + parts[1] + "-" + parts[2];
                if (!seenRequests.contains(requestKey)) {
                    seenRequests.add(requestKey);
                    agreementRequests.add(new AgreementRequest(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Error loading agreement requests: " + e.getMessage());
    }
}

    /**
     * Saves supplier data to the suppliers file.
     */
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

    /**
     * Saves agreement data to the agreements file.
     */
    private void saveAgreementsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AGREEMENT_FILE))) {
            for (Map.Entry<String, Set<String>> entry : supplierAgreements.entrySet()) {
                for (String storeId : entry.getValue()) {
                    writer.write(entry.getKey() + "-" + storeId);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving agreements: " + e.getMessage());
        }
    }

    /**
     * Saves agreement request data to the agreement requests file.
     */
    private void saveAgreementRequestsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AGREEMENT_REQUEST_FILE))) {
            // Clear the file first
            writer.write("");
            writer.flush();
            
            // Write each unique request
            for (AgreementRequest request : agreementRequests) {
                writer.write(String.join(",",
                    request.getRequestId(),
                    request.getSupplierId(),
                    request.getStoreId(),
                    request.getStatus()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving agreement requests: " + e.getMessage());
        }
    }

    /**
     * Checks if a supplier exists in the system.
     *
     * @param supplierId The ID of the supplier to check
     * @return true if the supplier exists, false otherwise
     */
    public boolean exists(String supplierId) {
        return suppliers.containsKey(supplierId);
    }
}