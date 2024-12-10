package com.fashion.weddingdressrental;

import com.fashion.weddingdressrental.DressCustomization.Customization;
import com.fashion.weddingdressrental.DressCustomization.CustomizationManager;
import com.fashion.weddingdressrental.Feedback.Feedback;
import com.fashion.weddingdressrental.Feedback.FeedbackManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Main class for the Dress Rental System.
 * Initializes key components and manages the application's entry point.
 */
public class Main {

    /**
     * Scanner used for user input throughout the application.
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Manages all store-related operations, including adding and viewing stores.
     */
    private static final StoreManager storeManager = new StoreManager();

    /**
     * Manages customer bank accounts, including deposits and withdrawals.
     */
    private static final AccountManager accountManager = new AccountManager();

    /**
     * Manages customer records, including registrations and account details.
     */
    private static final CustomerManager customerManager = new CustomerManager(accountManager);

    /**
     * Manages candidate applications, including hiring and interviewing.
     */
    private static final CandidateManager candidateManager = new CandidateManager();

    /**
     * Manages training sessions for employees.
     */
    private static final TrainingManager trainingManager = new TrainingManager();

    /**
     * Manages employees, including hiring, assigning tasks, and training.
     */
    private static final EmployeeManager employeeManager = new EmployeeManager(trainingManager);

    /**
     * Manages models for events such as photoshoots and fashion shows.
     */
    private static final ModelManager modelManager = new ModelManager();

    /**
     * Manages photoshoot events, including scheduling and assignment of models.
     */
    private static final PhotoshootManager photoshootManager = new PhotoshootManager();

    /**
     * Represents a generic employee for system operations.
     */
    private static final Employee employee = new Employee("123", "John", "Ames", 65000, "Sale");

    /**
     * Handles advertising tasks, including reviewing promotions and campaigns.
     */
    private static final AdvertisingDepartment advertisingDepartment = new AdvertisingDepartment();

    /**
     * Manages marketing operations such as promotions, newsletters, and email campaigns.
     */
    private static final MarketingDepartment marketingDepartment = new MarketingDepartment(customerManager, advertisingDepartment);

    /**
     * Handles human resources tasks, including candidate management, hiring, and employee training.
     */
    private static final HR hr = new HR(candidateManager, employeeManager, trainingManager, scanner);

    /**
     * Provides an interface for employees to access their assigned tasks and trainings.
     */
    private static final EmployeePortal employeePortal = new EmployeePortal(trainingManager, employeeManager);

    /**
     * Manages the inventory, including dresses and store supplies.
     */
    private static final InventoryManager inventoryManager = new InventoryManager();

    /**
     * Manages inventory transfers between different store locations.
     */
    private static final InventoryTransferManager inventoryTransferManager = new InventoryTransferManager(inventoryManager);

    /**
     * Manages supplier registrations, agreements, and product submissions.
     */
    private static final SupplierManager supplierManager = new SupplierManager(inventoryManager);
    
    
    /**
     * Main method serving as the entry point for the Wedding Dress Rental System.
     * Manages role selection and navigation to various system modules.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Wedding Dress Rental System!");

        while (true) {
            System.out.println("\n--- Select Role ---");
            System.out.println("1. Employee");
            System.out.println("2. Customer");
            System.out.println("3. HR");
            System.out.println("4. Marketing");
            System.out.println("5. Advertising");
            System.out.println("6. Models");
            System.out.println("7. Suppliers");
            System.out.println("8. Stores");
            System.out.println("9. Inventory Transfers");
            System.out.println("10. Inventory Menu");
            System.out.println("0. Exit");
            System.out.print("Choose your role: ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (roleChoice) {
                case 1 -> employeeMenu();
                case 2 -> customerMenu();
                case 3 -> HRMenu();
                case 4 -> marketingMenu();
                case 5 -> advertisingMenu();
                case 6 -> eventMenu();
                case 7 -> supplierMenu();
                case 8 -> storeMenu();
                case 9 -> inventoryTransferMenu();
                case 10 -> inventoryMenu();
                case 0 -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays the Advertising Department Menu.
     * Allows reviewing and managing promotions.
     */
    private static void advertisingMenu() {
        while (true) {
            System.out.println("\n--- Advertising Department Menu ---");
            System.out.println("1. Review Promotions");
            System.out.println("0. Go Back to Role Selection");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> advertisingDepartment.reviewPromotionsInteractive(); 
                case 0 -> { return; } // Exit the advertising menu
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays the Marketing Department Menu.
     * Manages promotional campaigns, newsletters, and advertising decisions.
     */
    private static void marketingMenu() {
        while (true) {
            System.out.println("\n--- Marketing Department Menu ---");
            System.out.println("1. Send Email Newsletter");
            System.out.println("2. Create a Promotion");
            System.out.println("3. View Pending Promotions");
            System.out.println("4. View Advertising Decisions");
            System.out.println("0. Go Back to Role Selection");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> marketingDepartment.sendEmailNewsletter();
                case 2 -> marketingDepartment.createPromotion();
                case 3 -> marketingDepartment.viewPendingPromotions();
                case 4 -> marketingDepartment.viewAdvertisingDecisions();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays the Employee Management Menu.
     * Allows employees to manage dresses, customers, and orders.
     */
    private static void employeeMenu() {
        while (true) {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. Update Dress Inventory Status");
            System.out.println("2. Process Dress Alteration Request");
            System.out.println("3. Wash and Prep Returned Dress");
            System.out.println("4. View Inventory");
            System.out.println("5. Add New Customer");
            System.out.println("6. Checkout Rental with Debit Card");
            System.out.println("7. Sell Gift Card with Debit Card");
            System.out.println("8. View All Customers");
            System.out.println("9. Confirm Reservation");
            System.out.println("10. Process Customization Requests");
            System.out.println("11. Handle Customer Feedback");
            System.out.println("12. View Assigned Trainings");
            System.out.println("13. Complete Training");
            System.out.println("0. Go Back to Role Selection");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> updateDressStatus1();
                case 2 -> processDressAlterationRequest();
                case 3 -> washAndPrepDress();
                case 4 -> viewInventoryMenu();
                case 5 -> addNewCustomer();
                case 6 -> checkoutRentalWithDebitCard();
                case 7 -> sellGiftCardWithDebitCard();
                case 8 -> customerManager.displayCustomers();
                case 9 -> confirmReservationMenu();
                case 10 -> processCustomizationRequests();
                case 11 -> viewFeedback();
                case 12 -> handleViewAssignedTrainings();
                case 13 -> handleCompleteTraining();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays the inventory for a specific store.
     * Requests the store ID from the user and shows the inventory details.
     */
    private static void viewInventoryMenu() {
        System.out.print("Enter Store ID to view inventory: ");
        String storeId = scanner.nextLine();

        if (!storeManager.exists(storeId)) {
            System.out.println("Store ID not found.");
            return;
        }

        System.out.println("\n--- Inventory for Store " + storeId + " ---");
        inventoryManager.displayStoreInventory(storeId);
    }



    /**
     * Displays the Supplier Management Menu.
     * Enables actions such as registering suppliers, submitting products, 
     * creating agreements, and viewing requests or product submissions.
     */
    private static void supplierMenu() {
        while (true) {
            System.out.println("\n--- Supplier Management ---");
            System.out.println("1. Register Supplier");
            System.out.println("2. Submit Product");
            System.out.println("3. View Submitted Products");
            System.out.println("4. Register Supplier-Store Agreement");
            System.out.println("5. View Agreement Status");
            System.out.println("0. Return to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> registerSupplier();
                case 2 -> submitProduct();
                case 3 -> viewSubmittedProducts();
                case 4 -> createAgreementRequest();
                case 5 -> viewAgreementRequests(); 
                case 0 -> {
                    System.out.println("Returning to Main Menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Registers a new supplier by collecting supplier details.
     * Saves the supplier record and assigns a unique supplier ID.
     */
    private static void registerSupplier() {
        System.out.print("Enter Company Name: ");
        String companyName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Product: ");
        String categories = scanner.nextLine();

        String supplierId = supplierManager.registerSupplier(companyName, email, phone, categories);
        System.out.println("Supplier registered successfully! Supplier ID: " + supplierId);
    }

    /**
     * Allows a supplier to submit a product to a specific store.
     * Collects necessary details such as quantity, price, and delivery estimate.
     */
    private static void submitProduct() {
        System.out.print("Enter Supplier ID: ");
        String supplierId = scanner.nextLine();

        if (!supplierManager.exists(supplierId)) {
            System.out.println("Supplier not found.");
            return;
        }

        System.out.print("Enter Store ID: ");
        String storeId = scanner.nextLine();

        System.out.print("Enter Product Quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter Price per Unit: ");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter Delivery Estimate: ");
        String deliveryEstimate = scanner.nextLine();

        supplierManager.submitProduct(supplierId, storeId, quantity, price, deliveryEstimate);
    }

    /**
     * Displays products submitted by a specific supplier.
     * Requires the supplier ID and shows all submitted products if available.
     */
    private static void viewSubmittedProducts() {
        System.out.print("Enter Supplier ID: ");
        String supplierId = scanner.nextLine();

        if (!supplierManager.exists(supplierId)) {
            System.out.println("Supplier not found.");
            return;
        }

        supplierManager.viewSubmittedProducts(supplierId);
    }

    /**
     * Creates a new agreement request between a supplier and a store.
     * Requires valid supplier and store IDs.
     */
    private static void createAgreementRequest() {
        System.out.print("Enter Supplier ID: ");
        String supplierId = scanner.nextLine();

        System.out.print("Enter Store ID: ");
        String storeId = scanner.nextLine();

        supplierManager.createAgreementRequest(supplierId, storeId);
    }

    /**
     * Approves or rejects a supplier-store agreement request based on the request ID.
     * Accepts user input ('Y' or 'N') to make a decision.
     */
    private static void approveOrRejectAgreementRequest() {
        System.out.print("Enter Request ID: ");
        String requestId = scanner.nextLine();

        System.out.print("Approve Request? (Y/N): ");
        String decision = scanner.nextLine().trim().toUpperCase();

        if ("Y".equals(decision)) {
            supplierManager.approveAgreementRequest(requestId);
        } else if ("N".equals(decision)) {
            supplierManager.rejectAgreementRequest(requestId);
        } else {
            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
        }
    }

    /**
     * Displays all pending supplier-store agreement requests.
     */
    private static void viewAgreementRequests() {
        supplierManager.viewAgreementRequests();
    }

    /**
     * Displays the Store Management Menu.
     * Enables actions such as registering new stores and viewing existing stores.
     */
    private static void storeMenu() {
        while (true) {
            System.out.println("\n--- Store Management ---");
            System.out.println("1. Register New Store");
            System.out.println("2. View All Stores");
            System.out.println("3. Approve Agreement Request");
            System.out.println("0. Return to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1 -> registerNewStore();
                case 2 -> storeManager.viewStores();
                case 3 -> approveOrRejectAgreementRequest();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Registers a new store by collecting relevant store information.
     * Assigns a unique store ID and saves the store record.
     */
    private static void registerNewStore() {
        System.out.print("Enter Store Name: ");
        String storeName = scanner.nextLine();
        System.out.print("Enter Store Location: ");
        String location = scanner.nextLine();
        System.out.print("Enter Store Contact Information: ");
        String contactInfo = scanner.nextLine();
        String storeId = "STORE-" + (int) (Math.random() * 1000);
        storeManager.registerStore(storeId, storeName, location, contactInfo);
    }

    /**
     * Displays the Inventory Management Menu.
     * Allows adding, viewing, and updating inventory records.
     */
    private static void inventoryMenu() {
        while (true) {
            System.out.println("\n--- Inventory Management ---");
            System.out.println("1. Add New Inventory");
            System.out.println("2. View Inventory");
            System.out.println("3. Update Dress Status");
            System.out.println("0. Return to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1 -> addNewInventory();
                case 2 -> inventoryManager.displayInventory();
                case 3 -> updateDressStatus1();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Adds a new inventory record to the specified store.
     * Collects dress ID, status, price, and quantity.
     */
    private static void addNewInventory() {
        System.out.print("Enter Store ID: ");
        String storeId = scanner.nextLine();

        System.out.print("Enter Dress ID: ");
        String dressId = scanner.nextLine();

        System.out.print("Enter Status (Available/Rented): ");
        String status = scanner.nextLine();

        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        inventoryManager.addInventory(storeId, dressId, status, price, quantity);
    }

    /**
     * Updates the status of a dress in a specific store's inventory.
     * Requires valid store and dress IDs, and a new dress status.
     */
    private static void updateDressStatus1() {
        System.out.print("Enter Store ID: ");
        String storeId = scanner.nextLine();

        System.out.print("Enter Dress ID to Update: ");
        String dressId = scanner.nextLine();

        InventoryItem dress = inventoryManager.findDressByIdInStore(storeId, dressId);

        if (dress == null) {
            System.out.println("Dress not found in the specified store.");
            return;
        }

        System.out.println("Current Status: " + dress.getStatus());
        System.out.print("Enter New Status (Available, Rented, Undergoing Repair, Retired): ");
        String newStatus = scanner.nextLine();

        try {
            inventoryManager.updateDressStatusInStore(storeId, dressId, newStatus);
            System.out.println("Dress status updated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    /**
     * Displays the Inventory Transfers Menu.
     * Allows managing inventory transfers, including requesting, viewing, 
     * and approving or rejecting transfers.
     */
    private static void inventoryTransferMenu() {
        while (true) {
            System.out.println("\n--- Inventory Transfers ---");
            System.out.println("1. Request Inventory Transfer");
            System.out.println("2. View Pending Requests for a Store");
            System.out.println("3. Approve or Reject Requests");
            System.out.println("4. View All Transfers");
            System.out.println("0. Return to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1 -> requestInventoryTransfer();
                case 2 -> viewPendingRequests();
                case 3 -> approveOrRejectRequest();
                case 4 -> inventoryTransferManager.viewAllTransfers();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Requests an inventory transfer from one store to another.
     * Validates product availability before placing the transfer request.
     */
    private static void requestInventoryTransfer() {
        System.out.print("Enter From Store ID (Requesting Store): ");
        String fromStoreId = scanner.nextLine();

        System.out.print("Enter To Store ID (Providing Store): ");
        String toStoreId = scanner.nextLine();

        System.out.print("Enter Product Name (Dress ID): ");
        String product = scanner.nextLine();

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); 

        int availableQuantity = inventoryManager.getDressQuantity(toStoreId, product);
        double price = inventoryManager.getProductPrice(toStoreId, product);

        if (availableQuantity >= quantity && price > 0) {
            inventoryTransferManager.requestTransfer(fromStoreId, toStoreId, product, price, quantity);
        } else {
            System.out.println("Error: Product not found or insufficient quantity in the providing store.");
        }
    }

    /**
     * Displays pending inventory transfer requests for a specific store.
     */
    private static void viewPendingRequests() {
        System.out.print("Enter Store ID to View Requests: ");
        String storeId = scanner.nextLine();
        inventoryTransferManager.viewPendingRequestsForStore(storeId);
    }

    /**
     * Approves or rejects an inventory transfer request based on the request ID.
     * Requires user confirmation ('Y' or 'N') to process the decision.
     */
    private static void approveOrRejectRequest() {
        System.out.print("Enter Store ID: ");
        String storeId = scanner.nextLine();
        System.out.print("Enter Request ID: ");
        String transferId = scanner.nextLine();
        System.out.print("Approve Request? (Y/N): ");
        String decision = scanner.nextLine().toUpperCase();
        
        if ("Y".equals(decision)) {
            inventoryTransferManager.approveTransfer(storeId, transferId);
        } else {
            inventoryTransferManager.rejectTransfer(storeId, transferId);
        }
    }

    /**
     * Displays assigned training sessions for an employee based on their ID.
     * Requires a valid employee ID and retrieves assigned trainings.
     */
    private static void handleViewAssignedTrainings() {
        System.out.print("Enter your Employee ID: ");
        String employeeId = scanner.nextLine();
        Employee employee = employeeManager.getEmployeeMap().get(employeeId);

        if (employee != null) {
            employeePortal.viewAssignedTrainings(employee);
        } else {
            System.out.println("Invalid Employee ID. Please try again.");
        }
    }

    /**
     * Marks a specific training session as completed for an employee.
     * Updates the training records and saves changes to the file.
     */
    private static void handleCompleteTraining() {
        System.out.print("Enter your Employee ID: ");
        String employeeId = scanner.nextLine();
        Employee employee = employeeManager.getEmployeeMap().get(employeeId);

        if (employee != null) {
            System.out.print("Enter Training ID to complete: ");
            String trainingId = scanner.nextLine();
            employeePortal.completeTraining(employee, trainingId);
            employeeManager.saveEmployeesToFile(); 
        } else {
            System.out.println("Invalid Employee ID. Please try again.");
        }
    }

    /**
     * Displays customer feedback based on a dress ID or shows all feedback if no ID is provided.
     * Loads feedback from the file and filters based on the dress ID.
     */
    private static void viewFeedback() {
        System.out.print("Enter Dress ID to view feedback (or press Enter to view all): ");
        String dressId = scanner.nextLine();

        List<Feedback> feedbackList = FeedbackManager.loadFeedback();
        List<Feedback> filteredFeedback = feedbackList.stream()
                .filter(fb -> dressId.isEmpty() || fb.getDressId().equals(dressId))
                .toList();

        if (filteredFeedback.isEmpty()) {
            System.out.println("No feedback found.");
        } else {
            System.out.println("--- Customer Feedback ---");
            filteredFeedback.forEach(System.out::println);
        }
    }

    /**
     * Processes pending dress customization requests.
     * Filters and approves or rejects specific requests based on user input.
     */
    private static void processCustomizationRequests() {
        List<Customization> customizations = CustomizationManager.loadCustomizations();

        if (customizations.isEmpty()) {
            System.out.println("No pending customization requests.");
            return;
        }

        System.out.println("--- Pending Customization Requests ---");
        customizations.stream()
            .filter(c -> c.getStatus().equals("Pending"))
            .forEach(c -> System.out.println(c.toCSV()));

        System.out.print("\nEnter the Customization ID to process: ");
        String customizationId = scanner.nextLine();

        Customization customization = customizations.stream()
            .filter(c -> c.getCustomizationId().equals(customizationId))
            .findFirst()
            .orElse(null);

        if (customization == null) {
            System.out.println("Invalid Customization ID.");
            return;
        }

        System.out.println("Customization Details: " + customization.toCSV());
        System.out.print("Approve customization? (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();

        String newStatus = response.equals("Y") ? "Approved" : "Rejected";
        CustomizationManager.updateCustomizationStatus(customizationId, newStatus);

        if (newStatus.equals("Approved")) {
            System.out.println("Customization approved. Ready for reservation.");
        } else {
            System.out.println("Customization rejected.");
        }
    }

    /**
     * Confirms a specific reservation based on its ID.
     * Updates the reservation status and saves the change to the file.
     */
    private static void confirmReservationMenu() {
        List<Reservation> allReservations = ReservationManager.loadReservationsFromFile(customerManager, inventoryManager);

        if (allReservations.isEmpty()) {
            System.out.println("No pending reservations found.");
            return;
        }

        System.out.println("\n--- Pending Reservations ---");
        allReservations.stream()
            .filter(reservation -> !"Confirmed".equalsIgnoreCase(reservation.getStatus()))
            .forEach(reservation -> System.out.println("ID: " + reservation.getReservationID() + 
                                                       ", Customer: " + reservation.getCustomer().getName() +
                                                       ", Dress: " + reservation.getDress().getDressId() +
                                                       ", Status: " + reservation.getStatus()));

        System.out.print("\nEnter the Reservation ID to confirm: ");
        String reservationId = scanner.nextLine();

        Reservation reservationToConfirm = allReservations.stream()
                .filter(reservation -> reservation.getReservationID().equals(reservationId))
                .findFirst()
                .orElse(null);

        if (reservationToConfirm == null) {
            System.out.println("Reservation ID not found.");
            return;
        }

        reservationToConfirm.setStatus("Confirmed");
        ReservationManager.updateReservationStatus(reservationId, "Confirmed", allReservations); 

        System.out.println("Reservation " + reservationId + " confirmed successfully.");
    }


    /**
     * Displays the Customer Menu.
     * Allows customers to manage their accounts, make reservations, 
     * request alterations, leave feedback, and customize dresses.
     */
    private static void customerMenu() {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Request Dress Alteration");
            System.out.println("2. Make a Dress Reservation");
            System.out.println("3. Customize a Dress");
            System.out.println("4. View Account Details");
            System.out.println("5. Submit/View Feedback");
            System.out.println("6. View Emails");
            System.out.println("0. Go Back to Role Selection");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1 -> requestDressAlteration();
                case 2 -> makeDressReservation();
                case 3 -> customizeDress();
                case 4 -> viewAccountDetails();
                case 5 -> rateAndLeaveFeedback();
                case 6 -> viewSentEmails(); 
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays emails sent to the customer.
     * Fetches and prints all promotional emails from the customer's inbox.
     */
    private static void viewSentEmails() {
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer ID not found. Please contact an employee to register.");
            return;
        }

        System.out.println("\n--- Inbox ---");
        if (customer.getSentEmails().isEmpty()) {
            System.out.println("No emails have been sent to you yet.");
        } else {
            for (Email email : customer.getSentEmails()) {
                System.out.println("Subject: " + email.getSubject());
                System.out.println("Body: " + email.getBody());
                System.out.println("Offer: " + email.getOffer());
                System.out.println("---");
            }
        }
    }

    /**
     * Displays current promotions from the promotions file.
     * Reads promotions and displays them if available.
     */
    public void displayPromotions() {
        String promotionFile = "promotions.txt";
        System.out.println("\n--- Promotions ---");

        try (BufferedReader reader = new BufferedReader(new FileReader(promotionFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading promotions file: " + e.getMessage());
        }
    }

    /**
     * Allows customers to submit feedback and rate their experience.
     * Requires a valid reservation ID and supports ratings from 1 to 5.
     */
    private static void rateAndLeaveFeedback() {
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer ID not found.");
            return;
        }

        System.out.print("Enter Reservation ID: ");
        String reservationId = scanner.nextLine();
        Reservation reservation = ReservationManager.findReservationById(reservationId, customerManager, inventoryManager);

        if (reservation == null || (!"Completed".equals(reservation.getStatus()) && 
                                    !"Confirmed".equals(reservation.getStatus()))) {
            System.out.println("Feedback can only be given for confirmed or completed reservations.");
            return;
        }

        String dressId = reservation.getDress().getDressId(); 
        System.out.print("Enter your rating (1-5): ");
        int rating;

        try {
            rating = scanner.nextInt();
            scanner.nextLine(); 
            if (rating < 1 || rating > 5) {
                System.out.println("Invalid rating. Please provide a rating between 1 and 5.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Rating must be a number between 1 and 5.");
            scanner.nextLine(); 
            return;
        }

        System.out.print("Enter your feedback (optional): ");
        String feedback = scanner.nextLine();

        Feedback newFeedback = new Feedback(
            generateFeedbackId(),
            customerId,
            reservationId,
            dressId,
            rating,
            feedback,
            new Date()
        );
        FeedbackManager.saveFeedback(newFeedback);
        System.out.println("Thank you for your feedback!");
    }

    /**
     * Generates a unique feedback ID.
     * @return A randomly generated feedback ID.
     */
    private static String generateFeedbackId() {
        return "FB-" + (int) (Math.random() * 10000);
    }

    /**
     * Allows customers to customize dresses with various options.
     * Includes custom embroidery, lace, accessories, and more.
     */
    private static void customizeDress() {
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer ID not found. Please contact an employee to register.");
            return;
        }

        System.out.print("Enter Dress ID to customize: ");
        String dressId = scanner.nextLine();
        InventoryItem dress = inventoryManager.findDressById(dressId);

        if (dress == null) {
            System.out.println("Dress not found in inventory.");
            return;
        }

        List<String> customizations = new ArrayList<>();
        while (true) {
            System.out.println("Choose customization options:");
            System.out.println("1. Add Embroidery");
            System.out.println("2. Add Lace");
            System.out.println("3. Add Accessories");
            System.out.println("4. Custom Color");
            System.out.println("5. Other");

            System.out.print("Enter your choice (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            String detail;
            switch (choice) {
                case 1 -> detail = "Embroidery: " + scanner.nextLine();
                case 2 -> detail = "Lace: " + scanner.nextLine();
                case 3 -> detail = "Accessories: " + scanner.nextLine();
                case 4 -> detail = "Color: " + scanner.nextLine();
                case 5 -> detail = "Custom: " + scanner.nextLine();
                default -> {
                    System.out.println("Invalid option.");
                    continue;
                }
            }
            customizations.add(detail);

            System.out.print("Add another customization (Y/N)? ");
            if (!scanner.nextLine().equalsIgnoreCase("Y")) {
                break;
            }
        }

        System.out.println("Customizations for Dress ID: " + dressId);
        customizations.forEach(System.out::println);
        System.out.print("Confirm these customizations (Y/N)? ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            customizations.forEach(detail -> CustomizationManager.saveCustomization(
                new Customization(
                    generateCustomizationId(),
                    customerId, 
                    dressId, 
                    detail, 
                    50.00, 
                    "Pending"
                )
            ));
            System.out.println("Customizations saved successfully!");
        } else {
            System.out.println("Customizations discarded.");
        }
    }

    /**
     * Generates a unique customization ID.
     * @return A randomly generated customization ID.
     */
    private static String generateCustomizationId() {
        return "CUST-" + (int) (Math.random() * 10000);
    }

    /**
     * Processes pending alteration requests.
     * Marks requests as "altered and ready" upon completion.
     */
    private static void processDressAlterationRequest() {
        System.out.println("--- Pending Alteration Requests ---");
        List<AlterationRequest> allAlterations = AlterationManager.loadAlterationsFromFile();

        if (allAlterations.isEmpty()) {
            System.out.println("No pending alteration requests found.");
            return;
        }

        allAlterations.forEach(alteration -> System.out.println(alteration));

        System.out.print("Enter the Alteration ID to process: ");
        String alterationId = scanner.nextLine();
        AlterationRequest alterationRequest = allAlterations.stream()
            .filter(alteration -> alteration.getAlterationId().equals(alterationId))
            .findFirst()
            .orElse(null);

        if (alterationRequest == null) {
            System.out.println("Alteration request not found.");
            return;
        }

        System.out.print("Enter 'start' to process the alteration: ");
        if ("start".equalsIgnoreCase(scanner.nextLine().trim())) {
            alterationRequest.setStatus("altered and ready");
            System.out.println("Alteration request processed.");
        } else {
            System.out.println("Invalid action entered.");
        }
    }

    // Helper method to save the updated alteration with status "altered and ready"
    /**
     * Saves an alteration request marked as "altered and ready" to a file.
     * 
     * @param alterationRequest The alteration request to be saved.
     */
    private static void saveAlterationAsReady(AlterationRequest alterationRequest) {
        String filePath = "alterations.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(alterationRequest.toCSV() + "," + alterationRequest.getStatus());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving updated alteration: " + e.getMessage());
        }
    }

    /**
     * Washes and prepares a returned dress for the next rental.
     * Updates the dress status and saves changes to the inventory file.
     */
    private static void washAndPrepDress() {
        System.out.print("Enter Store ID: ");
        String storeId = scanner.nextLine();

        System.out.print("Enter Dress ID for Wash and Prep: ");
        String dressId = scanner.nextLine();

        InventoryItem dress = inventoryManager.findDressById(dressId);

        if (dress == null) {
            System.out.println("Dress not found in inventory.");
            return;
        }

        if ("Available".equalsIgnoreCase(dress.getStatus())) {
            dress.setStatus("In Progress");
            System.out.println("Dress marked for cleaning.");
            
            dress.setStatus("Ready");
            System.out.println("Dress is now cleaned and ready for the next rental.");
        } else {
            System.out.println("Dress is not available for cleaning. Current status: " + dress.getStatus());
        }

        inventoryManager.saveInventoryToFile();
    }

    /**
     * Processes a rental checkout with a debit card.
     * Validates reservation, processes payment, updates the dress status, 
     * and saves the transaction to the file.
     */
    private static void checkoutRentalWithDebitCard() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.print("Enter Dress ID: ");
        String dressId = scanner.nextLine();
        InventoryItem dress = inventoryManager.findDressById(dressId);
        List<Reservation> allReservations = ReservationManager.loadReservationsFromFile(customerManager, inventoryManager);

        if (dress == null) {
            System.out.println("Dress not found in inventory.");
            return;
        }

        boolean hasReservation = customer.getReservations().stream()
                .anyMatch(reservation -> reservation.getDress().getDressId().equals(dressId) && 
                                          reservation.getStatus().equals("Confirmed"));

        if (!hasReservation) {
            System.out.println("Customer does not have a confirmed reservation for this dress. Payment cannot be processed.");
            return;
        }

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        double dressPrice = dress.getPrice();

        if (customer.getAccount() != null && customer.getAccount().hasSufficientFunds(dressPrice)) {
            Payment payment = new Payment(dressPrice, customer, employee, PaymentType.DEBIT_CARD);
            if (payment.processPayment(accountNumber)) {
                customer.getAccount().deductBalance(dressPrice, customerId);
                customerManager.saveCustomersToFile();
                Account account = accountManager.findAccountByCustomerId(customerId);
                accountManager.addOrUpdateAccount(customerId, account);

                dress.setStatus("Rented");
                ReservationManager.updateReservationStatus(dressId, "Rented", allReservations);
                inventoryManager.saveInventoryToFile();
                saveRentalToFile(customerId, dressId, dressPrice);

                System.out.println("Payment successful and transaction completed for dress: " + dressId);
            } else {
                System.out.println("Payment processing failed.");
            }
        } else {
            System.out.println("Insufficient funds or no account linked. Please add funds to your account.");
        }
    }

    /**
     * Saves a rental record to the file "rented.txt".
     * 
     * @param customerId The ID of the customer renting the dress.
     * @param dressId The ID of the rented dress.
     * @param price The rental price of the dress.
     */
    private static void saveRentalToFile(String customerId, String dressId, double price) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rented.txt", true))) {
            writer.write(String.join(",", customerId, dressId, String.valueOf(price), new Date().toString()));
            writer.newLine();
            System.out.println("Rental record saved.");
        } catch (IOException e) {
            System.out.println("Error saving rental record: " + e.getMessage());
        }
    }

    /**
     * Processes the sale of a gift card using a debit card.
     * Validates the customer's account and deducts funds if available.
     */
    private static void sellGiftCardWithDebitCard() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer ID not found.");
            return;
        }

        System.out.print("Enter gift card amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 

        if (amount < 10) {
            System.out.println("Amount is below minimum requirement.");
            return;
        }

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        Payment payment = new Payment(amount, customer, employee, PaymentType.DEBIT_CARD);
        if (payment.processPayment(accountNumber) && customer.getAccount().hasSufficientFunds(amount)) {
            customer.getAccount().deductBalance(amount, customerId);
            customerManager.saveCustomersToFile();
            Account account = accountManager.findAccountByCustomerId(customerId);
            accountManager.addOrUpdateAccount(customerId, account);
            saveGiftCardToFile(customerId, amount);
            System.out.println("Gift card sold successfully.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    /**
     * Saves a gift card purchase to the file "giftcards.txt".
     * 
     * @param customerId The ID of the customer buying the gift card.
     * @param amount The amount loaded on the gift card.
     */
    private static void saveGiftCardToFile(String customerId, double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("giftcards.txt", true))) {
            writer.write(customerId + "," + amount + "," + new Date().getTime());
            writer.newLine();
            System.out.println("Gift card purchase saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving gift card purchase to file: " + e.getMessage());
        }
    }

    /**
     * Allows customers to request a dress alteration.
     * Verifies customer details, active reservation, and alteration eligibility.
     */
    private static void requestDressAlteration() {
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer ID not found. Please contact an employee to register.");
            return;
        }

        System.out.print("Enter Dress ID for alteration: ");
        String dressId = scanner.nextLine();
        InventoryItem dress = inventoryManager.findDressById(dressId);

        if (dress == null) {
            System.out.println("Dress ID not found in inventory.");
            return;
        }

        if (!hasReservationForDress(customerId, dressId)) {
            System.out.println("No active reservation found for this dress. Alterations are only available for reserved dresses.");
            return;
        }

        System.out.print("Enter details for alteration (e.g., shorten hem, adjust waist): ");
        String details = scanner.nextLine();
        Date completionDate = new Date();
        AlterationRequest alterationRequest = customer.requestAlteration(details, completionDate, employee);

        if (alterationRequest != null) {
            System.out.println("Alteration request submitted: " + alterationRequest);
            customerManager.saveCustomersToFile();
        } else {
            System.out.println("Alteration request could not be submitted due to insufficient funds.");
        }
    }

    /**
     * Checks if a customer has a confirmed reservation for a specific dress.
     *
     * @param customerId The ID of the customer.
     * @param dressId    The ID of the dress.
     * @return true if the reservation exists and is confirmed, false otherwise.
     */
    private static boolean hasReservationForDress(String customerId, String dressId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("reservations.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String reservedCustomerId = parts[1];
                    String reservedDressId = parts[2];
                    String reservationStatus = parts[5];
                    if (reservedCustomerId.equals(customerId) && reservedDressId.equals(dressId) && reservationStatus.equals("Confirmed")) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading reservations file: " + e.getMessage());
        }
        return false;
    }

    /**
     * Generates a unique reservation ID using the current timestamp.
     *
     * @return A unique reservation ID string.
     */
    private static String generateUniqueReservationID() {
        return "RES-" + System.currentTimeMillis();
    }

    /**
     * Allows a customer to make a dress reservation.
     * Validates availability, deducts funds, confirms the reservation, and updates the inventory.
     */
    private static void makeDressReservation() {
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer ID not found.");
            return;
        }

        System.out.print("Enter Store ID: ");
        String storeId = scanner.nextLine();

        System.out.print("Enter Dress ID to reserve: ");
        String dressId = scanner.nextLine();

        InventoryItem dress = inventoryManager.findDressByIdInStore(storeId, dressId);

        if (dress == null) {
            System.out.println("Dress not found in the specified store.");
            return;
        }

        if (!"Available".equalsIgnoreCase(dress.getStatus()) || dress.getQuantity() <= 0) {
            System.out.println("Dress is not available for reservation.");
            return;
        }

        System.out.print("Enter Quantity to Reserve: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        if (quantity > dress.getQuantity()) {
            System.out.println("Requested quantity exceeds available stock.");
            return;
        }

        double dressPrice = dress.getPrice() * quantity;

        if (customer.getAccount() != null && customer.getAccount().hasSufficientFunds(dressPrice)) {
            customer.getAccount().deductBalance(dressPrice, customerId);
            inventoryManager.deductInventory(storeId, dressId, quantity);

            String uniqueReservationId = generateUniqueReservationID();
            Reservation reservation = new Reservation(
                    uniqueReservationId, customer, dress, new Date(),
                    new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 7)), storeId, quantity);

            ReservationManager.saveReservationToFile(reservation);
            customer.addReservation(reservation);
            customerManager.saveCustomersToFile();

            System.out.println("Reservation confirmed for " + customer.getName());
        } else {
            System.out.println("Insufficient funds. Please add funds to your account.");
        }
    }

    /**
     * Adds a new customer to the system.
     * Collects customer details, creates an account, and registers the customer in the system.
     */
    private static void addNewCustomer() {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Customer Email (or press Enter to skip): ");
        String email = scanner.nextLine().isEmpty() ? "Not specified" : scanner.nextLine();

        System.out.print("Enter initial Store Credit (or press Enter to skip): ");
        double storeCredit = scanner.hasNextDouble() ? scanner.nextDouble() : 0.0;
        scanner.nextLine();

        System.out.print("Enter Bank Account Balance (or press Enter to skip): ");
        double accountBalance = scanner.hasNextDouble() ? scanner.nextDouble() : 0.0;
        scanner.nextLine();

        System.out.print("Enter Preferred Size (S, M, L, etc., or press Enter to skip): ");
        String preferredSize = scanner.nextLine().isEmpty() ? "Not specified" : scanner.nextLine().toUpperCase();

        String customerId = "CUST-" + (int) (Math.random() * 1000);
        Customer customer = new Customer(customerId, name, storeCredit, 0, preferredSize, email);
        Account account = new Account(accountBalance);
        customer.setAccount(account);
        customerManager.addCustomer(customer);
        accountManager.addOrUpdateAccount(customerId, account);

        System.out.println("Customer added successfully with ID: " + customer.getCustomerId());
    }

    /**
     * Displays account details of a specified customer.
     * 
     * @param customerId The unique customer ID.
     */
    private static void viewAccountDetails() {
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer ID not found.");
            return;
        }

        System.out.println("\n--- Account Details ---");
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Store Credit: $" + customer.getStoreCredit());
        System.out.println("Bank Account Balance: $" + customer.getAccount().getBalance());
        System.out.println("Preferred Size: " + customer.getSize());
    }

    /**
     * Displays the HR management menu and processes HR-related actions.
     * Handles tasks such as hiring, managing interviews, and assigning training.
     */
    private static void HRMenu() {
        while (true) {
            System.out.println("\n--- HR Menu ---");
            System.out.println("1. Add Candidate");
            System.out.println("2. View Candidates");
            System.out.println("3. Assign Interview");
            System.out.println("4. Hire Candidate");
            System.out.println("5. View Employees");
            System.out.println("6. View All Interviews");
            System.out.println("7. Create Training Session");
            System.out.println("8. Assign Employees to Training");
            System.out.println("9. View All Training Sessions");
            System.out.println("10. Mark Training as Completed");
            System.out.println("0. Exit HR Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCandidate();
                case 2 -> candidateManager.displayCandidates();
                case 3 -> assignInterview();
                case 4 -> hireCandidate();
                case 5 -> employeeManager.displayEmployees();
                case 6 -> hr.displayAllInterviews();
                case 7 -> hr.createTrainingSessionMenu();
                case 8 -> assignEmployeesToTrainingMenu();
                case 9 -> hr.viewAllTrainingSessions();
                case 10 -> markTrainingAsCompletedMenu();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Handles the creation of a training session.
     * Prompts the HR representative for session details.
     */
    private static void createTrainingSessionMenu() {
        System.out.print("Enter topic: ");
        String topic = scanner.nextLine();
        System.out.print("Enter trainer: ");
        String trainer = scanner.nextLine();
        System.out.print("Enter date (MM/DD/YYYY): ");
        String date = scanner.nextLine();
        hr.createTrainingSession(topic, trainer, date);
    }

    /**
     * Assigns employees to a specific training session.
     */
    private static void assignEmployeesToTrainingMenu() {
        System.out.print("Enter Training ID: ");
        String trainingId = scanner.nextLine();

        System.out.println("Enter Employee IDs (comma-separated): ");
        String employeeIdsInput = scanner.nextLine();
        List<String> employeeIds = List.of(employeeIdsInput.split(","));

        hr.assignEmployeesToTraining(trainingId, employeeIds);
    }

    /**
     * Marks a training session as completed.
     */
    private static void markTrainingAsCompletedMenu() {
        System.out.print("Enter Training ID to mark as completed: ");
        String trainingId = scanner.nextLine();
        hr.markTrainingAsCompleted(trainingId);
    }

    /**
     * Hires a candidate by assigning them a salary, location, and role.
     * Prompts the user for the required details and processes the hiring request.
     */
    private static void hireCandidate() {
        System.out.print("Enter Candidate ID: ");
        String candidateId = scanner.nextLine();

        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); 

        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        System.out.print("Enter Role: ");
        String role = scanner.nextLine();

        hr.hireCandidate(candidateId, salary, location, role);
    }

    /**
     * Adds a new candidate to the system.
     * Collects candidate information such as name, email, phone number, and resume details.
     * Generates a unique candidate ID and saves the candidate record.
     */
    private static void addCandidate() {
        System.out.print("Enter Candidate Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Candidate Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Candidate Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Resume (File Path or Description): ");
        String resume = scanner.nextLine();

        String candidateId = "CAND-" + (int) (Math.random() * 10000);
        Candidate candidate = new Candidate(candidateId, name, email, phone, resume);
        candidateManager.addCandidate(candidate);
        System.out.println("Candidate added successfully.");
    }

    /**
     * Assigns an interview to a candidate by prompting for interview details.
     * Collects the candidate ID, interview time, and location, and schedules the interview.
     */
    private static void assignInterview() {
        System.out.print("Enter Candidate ID: ");
        String candidateId = scanner.nextLine();

        System.out.print("Enter Interview Time (Time,MM/DD/YY): ");
        String time = scanner.nextLine();

        System.out.print("Enter Interview Location: ");
        String location = scanner.nextLine();

        hr.assignInterview(candidateId, time, location);
    }


    /**
     * Method to display all Event Manager menu
     */
    private static void eventMenu() {
    while (true) {
        System.out.println("\n--- Models Management Menu ---");
        System.out.println("1. Hire Model");
        System.out.println("2. View Hired Models");
        System.out.println("3. Set Up Photoshoot");
        System.out.println("4. View all photoshoot sessions");
        System.out.println("0. Go Back to Role Selection");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 -> hireModel();
            case 2 -> modelManager.viewHiredModels(); // Call to view hired models
            case 3 -> setUpPhotoshoot(); // Call to set up a photoshoot
            case 4 -> photoshootManager.displayAllPhotoshoots(); // call to display all photoshoot
            case 0 -> { return; }
            default -> System.out.println("Invalid option. Please try again.");
        }
    }
}

/**
 * Method to hire models and store hired models info to file hiredModels.txt
 */
private static void hireModel() {
    while (true) {
        System.out.println("\n--- Available Models ---");
        ModelManager.displayAllModels();

        System.out.print("\nEnter Model ID to hire (or enter 0 to go back): ");
        String modelId = scanner.nextLine();
        // Check if the user wants to go back
        if (modelId.equals("0")) {
            System.out.println("Returning to the Event Management Menu...");
            return;
        }
        Model selectedModel = ModelManager.findModelById(modelId);

        if (selectedModel == null) {
            System.out.println("Invalid Model ID. Please try again.");
            continue;
        }

        if (ModelManager.isModelHired(modelId)) {
            System.out.println("This model has already been hired. Please select a different model.");
            continue;
        }

        System.out.print("How many days will the model be required?: ");
        int days = Integer.parseInt(scanner.nextLine());

        double totalCost = selectedModel.getPricePerDay() * days;
        System.out.printf("Total cost for hiring %s: $%.2f\n", selectedModel.getName(), totalCost);

        System.out.print("Enter the start hour of the event (24-hour format): ");
        String startHour = scanner.nextLine();

        System.out.print("Enter the end hour of the event (24-hour format): ");
        String endHour = scanner.nextLine();

        System.out.print("Please enter the event type (e.g., Wedding, Fashion Show, Photoshoot): ");
        String eventType = scanner.nextLine();

        System.out.print("Please provide the location of the event: ");
        String eventLocation = scanner.nextLine();

        System.out.print("Will you be providing transportation and accommodation for the model? (Y/N): ");
        String transportationAndAccommodation = scanner.nextLine();

        System.out.print("Do you agree to the payment terms of 50% advance and 50% upon completion? (Y/N): ");
        String paymentTermsAgreement = scanner.nextLine();

        System.out.print("How would you like to be updated? (Email/Phone/Other): ");
        String updatePreference = scanner.nextLine();

        System.out.print("Enter a note for the model's agent: ");
        String note = scanner.nextLine();

        // Random outcomes
        Random random = new Random();
        int outcome = random.nextInt(4);
        switch (outcome) {
            case 0 -> {
                System.out.println(selectedModel.getName() + "'s agent accepts the offer.");
                ModelManager.saveHiredModel(selectedModel, note, days, totalCost, startHour, endHour, eventType, eventLocation,
                transportationAndAccommodation, paymentTermsAgreement, updatePreference);
                return;
            }
            case 1 -> {
                System.out.println(selectedModel.getName() + "'s agent accepts the offer.");
                ModelManager.saveHiredModel(selectedModel, note, days, totalCost, startHour, endHour, eventType, eventLocation,
                transportationAndAccommodation, paymentTermsAgreement, updatePreference);
                return;
            }
            case 2 -> {
                System.out.println(selectedModel.getName() + " does not want to work with our company.");
                continue;
            }
            case 3 -> {
                double newPrice = selectedModel.getPricePerDay() * 1.1;
                System.out.printf("%s wants more money ($%.2f/day). Do you accept? (Y/N): ", selectedModel.getName(), newPrice);
                totalCost = newPrice * days;
                System.out.printf("New total cost will be: $%.2f",totalCost);
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("Y")) {
                    ModelManager.saveHiredModel(selectedModel, note, days, totalCost, startHour, endHour, eventType, eventLocation,
                    transportationAndAccommodation, paymentTermsAgreement, updatePreference);
                    System.out.println("Model hired at the new rate.");
                    return;
                } else {
                    System.out.println("Offer declined.");
                }
            }
        }
    }
}
/**
 * Method to set up a photoshoot session and store information to photoshoot.txt
 */
private static void setUpPhotoshoot() {
    // Load hired models
    Map<String, Model> hiredModels = modelManager.getHiredModels();

    if (hiredModels.isEmpty()) {
        System.out.println("No hired models available for photoshoot.");
        return;
    }

    List<Model> chosenModels = new ArrayList<>();
    boolean chooseAnotherModel;

    // Select models
    do {
        System.out.println("\n--- Hired Models ---");
        int index = 1;
        Map<Integer, String> indexToModelId = new HashMap<>();
        for (String modelId : hiredModels.keySet()) {
            System.out.printf("%d. %s\n", index, hiredModels.get(modelId));
            indexToModelId.put(index, modelId);
            index++;
        }

        System.out.print("\nChoose a model by number (or 0 to finish): ");
        int modelChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (modelChoice == 0) {
            break;
        }

        if (indexToModelId.containsKey(modelChoice)) {
            String chosenModelId = indexToModelId.get(modelChoice);
            Model chosenModel = hiredModels.get(chosenModelId);
            if (chosenModels.contains(chosenModel)) {
                System.out.println("This model has already been chosen.");
            } else {
                chosenModels.add(chosenModel);
                System.out.println("Model added to the photoshoot.");
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
        }

        System.out.print("Do you want to choose another model? (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();
        chooseAnotherModel = response.equals("Y");

    } while (chooseAnotherModel);

    if (chosenModels.isEmpty()) {
        System.out.println("No models selected. Returning to menu.");
        return;
    }

    // Select photographer
    List<Employee> photographers = new ArrayList<>();
    for (Employee employee : employeeManager.getEmployeeMap().values()) {
        if (employee.getRole().equalsIgnoreCase("Photographer")) {
            photographers.add(employee);
        }
    }

    if (photographers.isEmpty()) {
        System.out.println("No photographers available.");
        return;
    }

    System.out.println("\n--- Photographers ---");
    for (int i = 0; i < photographers.size(); i++) {
        System.out.printf("%d. %s\n", i + 1, photographers.get(i));
    }
    System.out.print("\nChoose a photographer by number: ");
    int photographerChoice = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    Employee chosenPhotographer = photographers.get(photographerChoice - 1);

    // Select dresses
    List<InventoryItem> availableDresses = inventoryManager.getAvailableDresses();
    List<InventoryItem> chosenDresses = new ArrayList<>();
    boolean chooseAnotherDress;

    do {
        System.out.println("\n--- Available Dresses ---");
        for (int i = 0; i < availableDresses.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, availableDresses.get(i));
        }
        System.out.print("\nChoose a dress by number: ");
        int dressChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (dressChoice > 0 && dressChoice <= availableDresses.size()) {
            InventoryItem chosenDress = availableDresses.get(dressChoice - 1);
            if (chosenDresses.contains(chosenDress)) {
                System.out.println("This dress has already been chosen.");
            } else {
                chosenDresses.add(chosenDress);
                System.out.println("Dress added to the photoshoot.");
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
        }

        System.out.print("Do you want to choose another dress? (Y/N): ");
        String response = scanner.nextLine().trim().toUpperCase();
        chooseAnotherDress = response.equals("Y");

    } while (chooseAnotherDress);

    if (chosenDresses.isEmpty()) {
        System.out.println("No dresses selected. Returning to menu.");
        return;
    }

    // Collect other details
    System.out.print("Enter location: ");
    String location = scanner.nextLine();
    System.out.print("Enter time: ");
    String time = scanner.nextLine();
    System.out.print("Enter style (e.g., portrait, landscape, candid): ");
    String style = scanner.nextLine();
    System.out.print("Enter mood (e.g., formal, casual, sport, romantic): ");
    String mood = scanner.nextLine();
    System.out.print("Enter output format (e.g., JPEG, RAW): ");
    String output = scanner.nextLine();
    System.out.print("Enter purpose: ");
    String purpose = scanner.nextLine();
    System.out.print("Enter lighting setup (e.g., natural, ring light, box light, studio light): ");
    String lightingSetup = scanner.nextLine();
    System.out.print("Enter camera settings (e.g. DSLR, Digital): ");
    String cameraSettings = scanner.nextLine();
    System.out.print("Enter resolution (e.g., 1080p, 2K, 4K): ");
    String resolution = scanner.nextLine();
    System.out.print("Enter cost: ");
    double cost = scanner.nextDouble();
    scanner.nextLine(); // Consume newline

    // Create photoshoot
    String photoshootId = photoshootManager.generatePhotoshootId();
    Photoshoot photoshoot = new Photoshoot(photoshootId, chosenModels, chosenPhotographer, chosenDresses,
            location, time, style, mood, output, purpose, lightingSetup, cameraSettings, resolution, cost);

    // Save photoshoot
    photoshootManager.savePhotoshoot(photoshoot);
    System.out.println("Photoshoot created and saved successfully.");
}
}
