package com.fashion.weddingdressrental;

import com.fashion.weddingdressrental.CandidateManager;
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
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AccountManager accountManager = new AccountManager();
    private static final CustomerManager customerManager = new CustomerManager(accountManager);
    private static final InventoryManager inventoryManager = new InventoryManager();
    private static final CandidateManager candidateManager = new CandidateManager();
    private static final EmployeeManager employeeManager = new EmployeeManager();
    private static final Employee employee = new Employee("123","John","Ames",65000,"Sale");
    private static final HR hr = new HR(candidateManager, employeeManager);
    private static final MarketingDepartment marketingDepartment = new MarketingDepartment(customerManager);


    public static void main(String[] args) {
        System.out.println("Welcome to the Wedding Dress Rental System!");

        while (true) {
            System.out.println("\n--- Select Role ---");
            System.out.println("1. Employee");
            System.out.println("2. Customer");
            System.out.println("3. HR");
            System.out.println("4. Marketing");
            System.out.println("0. Exit");
            System.out.print("Choose your role: ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (roleChoice) {
                case 1 -> employeeMenu();
                case 2 -> customerMenu();
                case 3 -> HRMenu();
                case 4 -> marketingMenu();
                case 0 -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void marketingMenu() {
        while (true) {
            System.out.println("\n--- Marketing Department Menu ---");
            System.out.println("1. Send Out Email Newsletter to Promote Special Offers");
            System.out.println("2. Collaborate with Advertising Team to Promote Brand");
            System.out.println("3. Gather and Curate Customer Reviews/Testimonials");
            System.out.println("4. Send Promotional Offers to New Customers");
            System.out.println("5. Analyze Marketing Campaign Effectiveness");
            System.out.println("6. Collaborate on Marketing Strategy with Store Manager");
            System.out.println("0. Go Back to Role Selection");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> marketingDepartment.sendEmailNewsletter();
                case 2 -> marketingDepartment.collaborateWithAdvertisingTeam();
                case 3 -> marketingDepartment.gatherCustomerReviews();
               // case 4 -> marketingDepartment.sendPromotionalOffersToNewCustomers();
                case 5 -> marketingDepartment.analyzeCampaignEffectiveness();
                //case 6 -> marketingDepartment.collaborateOnMarketingStrategyWithStoreManager();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
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
            System.out.println("10. Process Customization Requests"); // New option
            System.out.println("11. Handle Customer Feedback");
            System.out.println("0. Go Back to Role Selection");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> updateDressStatus();
                case 2 -> processDressAlterationRequest();
                case 3 -> washAndPrepDress();
                case 4 -> inventoryManager.displayInventory();
                case 5 -> addNewCustomer();
                case 6 -> checkoutRentalWithDebitCard();
                case 7 -> sellGiftCardWithDebitCard();
                case 8 -> customerManager.displayCustomers();
                case 9 -> confirmReservationMenu();
                case 10 -> processCustomizationRequests(); // Call the method for processing customizations
                case 11 -> viewFeedback();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    //ADDED
    private static void viewFeedback() {
        System.out.print("Enter Dress ID to view feedback (or press Enter to view all): ");
        String dressId = scanner.nextLine();
    
        // Load all feedback
        List<Feedback> feedbackList = FeedbackManager.loadFeedback();
    
        // Filter feedback by dress ID if provided
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

    //ADDED
    private static void processCustomizationRequests() {
        List<Customization> customizations = CustomizationManager.loadCustomizations();
        
        if (customizations.isEmpty()) {
            System.out.println("No pending customization requests.");
            return;
        }
    
        // Filter and display pending customizations
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

    private static void confirmReservationMenu() {
        List<Reservation> allReservations = ReservationManager.loadReservationsFromFile(customerManager, inventoryManager);
    
        if (allReservations.isEmpty()) {
            System.out.println("No pending reservations found.");
            return;
        }
    
        System.out.print("Enter the Reservation ID to confirm: ");
        String reservationId = scanner.nextLine();
    
        Reservation reservationToConfirm = allReservations.stream()
                .filter(reservation -> reservation.getReservationID().equals(reservationId))
                .findFirst()
                .orElse(null);
    
        if (reservationToConfirm == null) {
            System.out.println("Reservation ID not found.");
            return;
        }
    
        reservationToConfirm.setStatus("Confirmed"); // Update status in memory
        ReservationManager.updateReservationStatus(reservationId, "Confirmed", allReservations); // Save to file
    
        System.out.println("Reservation " + reservationId + " confirmed successfully.");
    }

    private static void customerMenu() {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Request Dress Alteration");
            System.out.println("2. Make a Dress Reservation");
            System.out.println("3. Customize a Dress"); // New Option
            System.out.println("4. View Account Details");
            System.out.println("5. Submit/View Feedback");
            System.out.println("6. View Emails");
            System.out.println("0. Go Back to Role Selection");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> requestDressAlteration();
                case 2 -> makeDressReservation();
                case 3 -> customizeDress(); // Call the new method
                case 4 -> viewAccountDetails();
                case 5 -> rateAndLeaveFeedback();
                case 6 -> viewSentEmails(); 
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    
    
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

    //ADDED
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
    
        // Validate reservation and retrieve Dress ID
        Reservation reservation = ReservationManager.findReservationById(reservationId, customerManager, inventoryManager);
        if (reservation == null) {
            System.out.println("Reservation not found. Please check your Reservation ID.");
            return;
        }
    
        // Check if the reservation is not "Completed" or "Confirmed"
        if (!"Completed".equals(reservation.getStatus()) && !"Confirmed".equals(reservation.getStatus())) {
            System.out.printf(
                "Feedback cannot be submitted. The reservation for Dress ID %s is currently '%s'. "
                + "Feedback can only be given for reservations that are 'Confirmed' or 'Completed'.%n",
                reservation.getDress().getDressId(),
                reservation.getStatus()
            );
            return;
        }
    
        String dressId = reservation.getDress().getDressId(); // Get Dress ID from the reservation
    
        System.out.print("Enter your rating (1-5): ");
        int rating;
        try {
            rating = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (rating < 1 || rating > 5) {
                System.out.println("Invalid rating. Please provide a rating between 1 and 5.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Rating must be a number between 1 and 5.");
            scanner.nextLine(); // Clear invalid input
            return;
        }
    
        System.out.print("Enter your feedback (optional): ");
        String feedback = scanner.nextLine();
    
        Feedback newFeedback = new Feedback(
            generateFeedbackId(),
            customerId,
            reservationId,
            dressId, // Pass Dress ID here
            rating,
            feedback,
            new Date()
        );
        FeedbackManager.saveFeedback(newFeedback);
    
        System.out.println("Thank you for your feedback!");
    }

    private static String generateFeedbackId() {
        return "FB-" + (int) (Math.random() * 10000);
    }

    //ADDED
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
        System.out.println("1. Add Embroidery (e.g., floral pattern, initials)");
        System.out.println("2. Add Lace (e.g., sleeves, hemline, neckline)");
        System.out.println("3. Add Accessories (e.g., beads, sequins, brooch)");
        System.out.println("4. Custom Color (e.g., pastel blue, ivory, blush pink)");
        System.out.println("5. Other (manual entry)");
        System.out.print("Enter your choice (1-5): ");

        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            scanner.nextLine(); // Clear invalid input
            continue;
        }

        String detail;
        switch (choice) {
            case 1 -> {
                System.out.print("Enter embroidery details (e.g., floral pattern): ");
                detail = "Embroidery: " + scanner.nextLine();
                customizations.add(detail);
            }
            case 2 -> {
                System.out.print("Enter lace details (e.g., hemline, neckline): ");
                detail = "Lace: " + scanner.nextLine();
                customizations.add(detail);
            }
            case 3 -> {
                System.out.print("Enter accessory details (e.g., beads, sequins): ");
                detail = "Accessories: " + scanner.nextLine();
                customizations.add(detail);
            }
            case 4 -> {
                System.out.print("Enter color details (e.g., pastel blue): ");
                detail = "Color: " + scanner.nextLine();
                customizations.add(detail);
            }
            case 5 -> {
                System.out.print("Enter your custom details: ");
                detail = "Custom: " + scanner.nextLine();
                customizations.add(detail);
            }
            default -> {
                System.out.println("Invalid option. Please choose a number between 1 and 5.");
                continue;
            }
        }

        System.out.print("Add another customization (Y/N)? ");
        String more = scanner.nextLine();
        if (!more.equalsIgnoreCase("Y")) {
            break;
        }
    }

    // Display customizations for confirmation
    System.out.println("\nYou have added the following customizations for Dress ID: " + dressId);
    customizations.forEach(c -> System.out.println("- " + c));
    System.out.print("Confirm these customizations (Y/N)? ");
    String confirm = scanner.nextLine();

    if (confirm.equalsIgnoreCase("Y")) {
        for (String customization : customizations) {
            // Save each customization to the file
            com.fashion.weddingdressrental.DressCustomization.Customization newCustomization =
            new com.fashion.weddingdressrental.DressCustomization.Customization(
                generateCustomizationId(), customerId, dressId, customization, 50.00, "Pending"
            );
            CustomizationManager.saveCustomization(newCustomization);
        }
        System.out.println("Customizations saved successfully!");
    } else {
        System.out.println("Customizations discarded.");
    }
}

private static String generateCustomizationId() {
    return "CUST-" + (int) (Math.random() * 10000);
}

    private static void updateDressStatus() {
        System.out.print("Enter Dress ID to update: ");
        String dressId = scanner.nextLine();
        InventoryItem dress = inventoryManager.findDressById(dressId);

        if (dress == null) {
            System.out.println("Dress not found in inventory.");
            return;
        }

        System.out.print("Enter new status (Available, Rented, Undergoing Repair, Retired): ");
        String newStatus = scanner.nextLine();

        try {
            inventoryManager.updateDressStatus(dressId, newStatus);
            System.out.println("Status updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating dress status: " + e.getMessage());
        }
    }

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
        String action = scanner.nextLine().trim().toLowerCase();

        if (action.equals("start")) {
            // Mark the alteration as "altered and ready"
            alterationRequest.setStatus("altered and ready");

            // Save the updated alteration request to file with new status
            saveAlterationAsReady(alterationRequest);

          

            System.out.println("Alteration request has been processed and marked as 'altered and ready'.");
        } else {
            System.out.println("Invalid action entered.");
        }
    }

    // Helper method to save the updated alteration with status "altered and ready"
    private static void saveAlterationAsReady(AlterationRequest alterationRequest) {
        String filePath = "alterations.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Append the alteration with the "altered and ready" status
            writer.write(alterationRequest.toCSV() + "," + alterationRequest.getStatus());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving updated alteration: " + e.getMessage());
        }
    }


    private static void washAndPrepDress() {
        System.out.print("Enter Dress ID for wash and prep: ");
        String dressId = scanner.nextLine();
        InventoryItem dress = inventoryManager.findDressById(dressId);

        if (dress == null) {
            System.out.println("Dress not found in inventory.");
            return;
        }
        

        if ("Available".equals(dress.getStatus())) {
            dress.setStatus("In Progress");
            System.out.println("Dress marked for cleaning.");

            dress.setStatus("Ready");
            System.out.println("Dress is now cleaned and ready for the next rental.");
        } else {
            System.out.println("Dress is not available for cleaning. Current status: " + dress.getStatus());
        }

        inventoryManager.saveInventoryToFile();
    }

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

        // Check if the customer has a reservation for the specified dress
        boolean hasReservation = customer.getReservations().stream()
                .anyMatch(reservation -> reservation.getDress().getDressId().equals(dressId) && reservation.getStatus().equals("Confirmed"));

        if (!hasReservation) {
            System.out.println("Customer does not have a reservation for this dress. Payment cannot be processed.");
            return;
        }

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        double dressPrice = dress.getPrice();

        // Ensure the customer has an account and sufficient funds
        if (customer.getAccount() != null && customer.getAccount().hasSufficientFunds(dressPrice)) {
            Payment payment = new Payment(dressPrice, customer, employee, PaymentType.DEBIT_CARD);
            if (payment.processPayment(accountNumber)) {
                customer.getAccount().deductBalance(dressPrice,customerId);
                customerManager.saveCustomersToFile();  // Save updated balance to file
                Account account = accountManager.findAccountByCustomerId(customerId);
                accountManager.addOrUpdateAccount(customerId, account);
                dress.setStatus("Rented");
                ReservationManager.saveReservationsToFile(allReservations);
                inventoryManager.saveInventoryToFile();
                System.out.println("Payment successful and transaction completed for dress: " + dressId);
            } else {
                System.out.println("Payment processing failed.");
            }
        } else {
            System.out.println("Insufficient funds or no account linked. Please add funds to your account.");
        }
    }


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
        scanner.nextLine(); // consume newline

        if (amount < 10) {
            System.out.println("Amount is below minimum requirement.");
            return;
        }

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        Payment payment = new Payment(amount, customer, employee, PaymentType.DEBIT_CARD);
        if (payment.processPayment(accountNumber) && customer.getAccount().hasSufficientFunds(amount)) {
            customer.getAccount().deductBalance(amount,customerId);
            customerManager.saveCustomersToFile();
            Account account = accountManager.findAccountByCustomerId(customerId);
            accountManager.addOrUpdateAccount(customerId, account);
            saveGiftCardToFile(customerId, amount);
            System.out.println("Gift card sold successfully.");
            System.out.println("Amount deducted from account: $" + amount);
            System.out.println("Remaining balance: $" + customer.getAccount().getBalance());
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    private static void saveGiftCardToFile(String customerId, double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("giftcards.txt", true))) {
            writer.write(customerId + "," + amount + "," + new Date().getTime());
            writer.newLine();
            System.out.println("Gift card purchase saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving gift card purchase to file: " + e.getMessage());
        }
    }

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
            System.out.println("Customer data has been updated and saved to file.");
        } else {
            System.out.println("Alteration request could not be submitted due to insufficient funds.");
        }
    }

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

    private static String generateUniqueReservationID() {
        return "RES-" + System.currentTimeMillis(); // Using the current timestamp
    }

    private static void makeDressReservation() {
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer ID not found.");
            return;
        }

        System.out.print("Enter Dress ID to reserve: ");
        String dressId = scanner.nextLine();
        InventoryItem dress = inventoryManager.findDressById(dressId);

        if (dress == null) {
            System.out.println("Dress not found in inventory.");
            return;
        }

        if (!"Available".equals(dress.getStatus())) {
            System.out.println("Dress is not available for reservation. Current status: " + dress.getStatus());
            return;
        }

        double dressPrice = 10.00;

        if (customer.getAccount() != null && customer.getAccount().hasSufficientFunds(dressPrice)) {
            customer.getAccount().deductBalance(dressPrice, customerId);

            accountManager.addOrUpdateAccount(customerId, customer.getAccount());
            String uniqueReservationId = generateUniqueReservationID(); // Generate a unique ID
            Reservation reservation = new Reservation(uniqueReservationId, customer, dress, new Date(), new Date());
            customer.addReservation(reservation);

            List<Reservation> allReservations = ReservationManager.loadReservationsFromFile(customerManager, inventoryManager);
            allReservations.add(reservation);
            ReservationManager.saveReservationsToFile(allReservations);

            customerManager.saveCustomersToFile();
            System.out.println("Reservation confirmed for customer " + customer.getName());
            System.out.println("Amount deducted from account: $" + dressPrice);
            System.out.println("Remaining balance: $" + customer.getAccount().getBalance());
        } else {
            System.out.println("Insufficient funds. Please add funds to your account.");
        }
    }

    private static void addNewCustomer() {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Customer Email (or press Enter to skip): ");
        String emailInput = scanner.nextLine();
        String email = emailInput.isEmpty() ? "Not specified" : emailInput;

        System.out.print("Enter initial Store Credit (or press Enter to skip): ");
        double storeCredit = scanner.hasNextDouble() ? scanner.nextDouble() : 0.0;
        scanner.nextLine(); // consume newline

        System.out.print("Enter initial Store Points (or press Enter to skip): ");
        int storePoints = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine(); // consume newline

        System.out.print("Enter Bank Account Balance (or press Enter to skip): ");
        double accountBalance = scanner.hasNextDouble() ? scanner.nextDouble() : 0.0;
        scanner.nextLine(); // consume newline

        System.out.print("Enter Preferred Size (S, M, L, etc., or press Enter to skip): ");
        String sizeInput = scanner.nextLine();
        String preferredSize = sizeInput.isEmpty() ? "Not specified" : sizeInput.toUpperCase();

        String customerId = "CUST-" + (int) (Math.random() * 1000);
        Customer customer = new Customer(customerId, name, storeCredit, storePoints, preferredSize, email);
        Account account = new Account(accountBalance);
        customer.setAccount(account);
        customerManager.addCustomer(customer);
        accountManager.addOrUpdateAccount(customerId, account);

        System.out.println("Customer added successfully with ID: " + customer.getCustomerId());
    }

    private static void viewAccountDetails() {
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer ID not found. Please contact an employee to register.");
            return;
        }

        System.out.println("\n--- Account Details ---");
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Store Credit: $" + customer.getStoreCredit());
        System.out.println("Store Points: " + customer.getStorePoints());
        if (customer.getAccount() != null) {
            System.out.println("Bank Account Balance: $" + customer.getAccount().getBalance());
        } else {
            System.out.println("No bank account linked.");
        }
        System.out.println("Preferred Size: " + (customer.getSize() != null ? customer.getSize() : "Not specified"));
    }

    private static void HRMenu() {
        while (true) {
            System.out.println("\n--- HR Menu ---");
            System.out.println("1. Add Candidate");
            System.out.println("2. View Candidates");
            System.out.println("3. Assign Interview");
            System.out.println("4. Hire Candidate");
            System.out.println("5. View Employees");
            System.out.println("0. Exit HR Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            switch (choice) {
                case 1 -> addCandidate();
                case 2 -> candidateManager.displayCandidates();
                case 3 -> assignInterview();
                case 4 -> hireCandidate();
                case 5 -> employeeManager.displayEmployees();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
    
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
    
    private static void assignInterview() {
        System.out.print("Enter Candidate ID: ");
        String candidateId = scanner.nextLine();
    
        System.out.print("Enter Interview Time: ");
        String time = scanner.nextLine();
    
        System.out.print("Enter Interview Location: ");
        String location = scanner.nextLine();
    
        hr.assignInterview(candidateId, time, location);
    }
}
