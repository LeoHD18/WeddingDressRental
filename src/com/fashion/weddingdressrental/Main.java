package com.fashion.weddingdressrental;

import java.util.Date;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InventoryManager inventoryManager = new InventoryManager();
    private static final CustomerManager customerManager = new CustomerManager();
    private static final Employee employee = new Employee("John");

    public static void main(String[] args) {
        System.out.println("Welcome to the Wedding Dress Rental System!");

        while (true) {
            System.out.println("\n--- Select Role ---");
            System.out.println("1. Employee");
            System.out.println("2. Customer");
            System.out.println("0. Exit");
            System.out.print("Choose your role: ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (roleChoice) {
                case 1 -> employeeMenu();
                case 2 -> customerMenu();
                case 0 -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
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
                case 0 -> { return; } // Return to role selection
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void customerMenu() {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Request Dress Alteration");
            System.out.println("2. Make a Dress Reservation");
            System.out.println("3. View Account Details");
            System.out.println("0. Go Back to Role Selection");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> requestDressAlteration();
                case 2 -> makeDressReservation();
                case 3 -> viewAccountDetails();
                case 0 -> { return; } // Return to role selection
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
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
        System.out.print("Enter Customer ID for alteration: ");
        String customerId = scanner.nextLine();
        Customer customer = customerManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer not found. Please add the customer first.");
            return;
        }

        System.out.print("Enter alteration details: ");
        String details = scanner.nextLine();

        Date completionDate = new Date(); // Set completion date as needed
        AlterationRequest alterationRequest = customer.requestAlteration(details, completionDate, employee);

        if (alterationRequest != null) {
            System.out.println("Alteration request recorded for customer " + customer.getName());
            customerManager.saveCustomersToFile(); // Save after successful alteration creation
        } else {
            System.out.println("Alteration request could not be recorded due to insufficient funds.");
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

        dress.setStatus("In Progress");
        System.out.println("Dress marked for cleaning.");
        
        // Simulate cleaning and ready status update
        dress.setStatus("Ready");
        System.out.println("Dress is now cleaned and ready for the next rental.");
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

        if (dress == null) {
            System.out.println("Dress not found in inventory.");
            return;
        }

        Payment payment = new Payment(100, customer, employee, PaymentType.DEBIT_CARD);
        if (payment.processPayment()) {
            System.out.println("Payment successful and transaction completed.");
        } else {
            System.out.println("Payment failed.");
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

        Account customerAccount = customer.getAccount();
        if (customerAccount == null || !customerAccount.hasSufficientFunds(amount)) {
            System.out.println("Insufficient funds in the customer's account.");
            return;
        }

        // Deduct amount from customer's account balance
        customerAccount.deductBalance(amount);

        // Process payment and confirm purchase
        Payment payment = new Payment(amount, customer, employee, PaymentType.DEBIT_CARD);
        if (payment.processPayment()) {
            System.out.println("Gift card sold successfully. Amount deducted: $" + amount);
            System.out.println("Remaining balance: $" + customerAccount.getBalance());

            // Optionally, save gift card details to file
            saveGiftCardToFile(customerId, amount);
        } else {
            System.out.println("Payment failed for gift card purchase.");
        }
    }

    // Optional method to save gift card purchase details
    private static void saveGiftCardToFile(String customerId, double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\yvarun79\\Desktop\\WeddingDressRental\\src\\com\\fashion\\weddingdressrental\\gitcards.txt", true))) {
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

        // Check if customer has a reservation for the specified dress
        if (!hasReservationForDress(customerId, dressId)) {
            System.out.println("No active reservation found for this dress. Alterations are only available for reserved dresses.");
            return;
        }

        System.out.print("Enter details for alteration (e.g., shorten hem, adjust waist): ");
        String details = scanner.nextLine();

        Date completionDate = new Date(); // Set completion date as needed
        AlterationRequest alterationRequest = customer.requestAlteration(details, completionDate, employee);

        if (alterationRequest != null) {
            System.out.println("Alteration request submitted: " + alterationRequest);
        } else {
            System.out.println("Alteration request could not be submitted due to insufficient funds.");
        }
    }

    // Helper method to check for an existing reservation in reservations.txt
    private static boolean hasReservationForDress(String customerId, String dressId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\yvarun79\\Desktop\\WeddingDressRental\\src\\com\\fashion\\weddingdressrental\\reservations.txt"))) {
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

        if (dress.isReserved()) {
            System.out.println("This dress is currently reserved and unavailable.");
            return;
        }

        double dressPrice = dress.getPrice();

        if (customer.getAccount().getBalance() < dressPrice) {
            System.out.println("Insufficient funds. Please add funds to your account or choose a different dress.");
            return;
        }

        System.out.print("Enter reservation start date (YYYY-MM-DD): ");
        String startDateStr = scanner.nextLine();
        System.out.print("Enter reservation end date (YYYY-MM-DD): ");
        String endDateStr = scanner.nextLine();

        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

            customer.getAccount().deductBalance(dressPrice);

            Reservation reservation = new Reservation("RES-" + customerId, customer, dress, startDate, endDate);
            customer.addReservation(reservation);
            reservation.confirmReservation();

            System.out.println("Reservation confirmed for customer " + customer.getName());
            System.out.println("Amount deducted from account: $" + dressPrice);
            System.out.println("Remaining balance: $" + customer.getAccount().getBalance());

        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter dates in YYYY-MM-DD format.");
        }
    }



    private static void addNewCustomer() {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();

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
        Size preferredSize = sizeInput.isEmpty() ? null : Size.valueOf(sizeInput.toUpperCase());

        String customerId = "CUST-" + (int) (Math.random() * 1000);
        Customer customer = new Customer(customerId, name, storeCredit, storePoints, preferredSize != null ? preferredSize.toString() : null);
        customer.setAccount(new Account(accountBalance));
        customerManager.addCustomer(customer);

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
        System.out.println("Store Credit: " + customer.getStoreCredit());
        System.out.println("Store Points: " + customer.getStorePoints());
        if (customer.getAccount() != null) {
            System.out.println("Bank Account Balance: $" + customer.getAccount().getBalance());
        } else {
            System.out.println("No bank account linked.");
        }
        System.out.println("Preferred Size: " + (customer.getSize() != null ? customer.getSize() : "Not specified"));
    }
}

