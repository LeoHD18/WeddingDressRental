package com.fashion.weddingdressrental;

import java.util.Date;

//public class Main {
//    public static void main(String[] args) {
//        // // Create instances of required objects
//        // WeddingDress dress = new WeddingDress("WD-001");
//        // Customer customer = new Customer("Alice");
//        // Payment payment = new Payment(200.0);
//        
//        // // Create a Transaction
//        // Date startDate = new Date();
//        // Date endDate = new Date(startDate.getTime() + (5 * 24 * 60 * 60 * 1000)); // 5 days later
//        
//        // Transaction transaction = new Transaction("TXN-1001", startDate, endDate, dress, customer, payment);
//        
//        // // Finalize the transaction
//        // transaction.finalizeTransaction();
//        
//        // // Check the cleaning status after the transaction
//        // WashAndPrep washAndPrep = new WashAndPrep();
//        // System.out.println("Cleaning Status: " + washAndPrep.checkStatus());
////        Dress dress = new Dress("haha");
////        BankAccount checking = new BankAccount(200, 12354);
////        Customer customer = new Customer("Alice");
////        customer.setAccount(checking);
////        Employee e = new Employee("Huy");
////        Date startDate = new Date();
////        Date endDate = new Date(startDate.getTime() + (5 * 24 * 60 * 60 * 1000)); // 5 days later
////        Transaction trans = new Transaction("123", startDate, endDate, dress, customer, null);
////        Payment pay = new Payment(100, customer, e, PaymentType.DEBIT_CARD, trans);
////        pay.checkoutDebitCard(12354);
//    }


import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InventoryManager inventoryManager = new InventoryManager();
    private static final Employee employee = new Employee("John");
    private static final Customer customer = new Customer("Alice");

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
                case 1:
                    employeeMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void employeeMenu() {
        while (true) {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. Manage Inventory Status");
            System.out.println("2. Process Dress Alteration Request");
            System.out.println("0. Go Back to Role Selection");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    manageInventoryStatus();
                    break;
                case 2:
                    processDressAlterationRequest();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void customerMenu() {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Request Dress Alteration");
            System.out.println("2. View Account Details");
            System.out.println("0. Go Back to Role Selection");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    requestDressAlteration();
                    break;
                case 2:
                    viewAccountDetails();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void manageInventoryStatus() {
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
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter alteration details: ");
        String details = scanner.nextLine();
        System.out.print("Enter estimated cost: ");
        double cost = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        // Simulate completion date (you could take input here if needed)
        Date completionDate = new Date();

        // Process the alteration request for the customer
        AlterationRequest alterationRequest = new AlterationRequest("ALT-" + customerName, details, cost, completionDate);
        employee.processAlterationRequest(customer, alterationRequest);
    }

    private static void requestDressAlteration() {
        System.out.print("Enter details for alteration (e.g., shorten hem, adjust waist): ");
        String details = scanner.nextLine();
        System.out.print("Enter estimated cost of alteration: ");
        double cost = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        // Using today's date as an example completion date
        Date completionDate = new Date();

        // Customer requests an alteration
        AlterationRequest alterationRequest = customer.requestAlteration(details, cost, completionDate, employee);
        System.out.println("Alteration request submitted: " + alterationRequest);
    }

    private static void viewAccountDetails() {
        System.out.println("\n--- Account Details ---");
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
