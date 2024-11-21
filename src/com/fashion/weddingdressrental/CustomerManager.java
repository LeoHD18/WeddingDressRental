package com.fashion.weddingdressrental;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerManager {
    private Map<String, Customer> customers;
    private static final String FILE_PATH = "customer.txt";
    private AccountManager accountManager;

    public CustomerManager(AccountManager accountManager) {
        customers = new HashMap<>();
        this.accountManager = accountManager;
        loadCustomersFromFile();
    }

    private void loadCustomersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {  // Updated length for email
                    String customerId = parts[0];
                    String name = parts[1];
                    double storeCredit = Double.parseDouble(parts[2]);
                    int storePoints = Integer.parseInt(parts[3]);
                    double accountBalance = Double.parseDouble(parts[4]);
                    String preferredSize = parts[5];
                    String email = parts[6];  // Email from the file

                    // Create the customer object
                    Customer customer = new Customer(customerId, name, storeCredit, storePoints, preferredSize, email);

                    // Load associated account
                    Account account = accountManager.findAccountByCustomerId(customerId);
                    if (account != null) {
                        customer.setAccount(account);
                    } else {
                        account = new Account(0.0, customerId);
                        customer.setAccount(account);
                        accountManager.addOrUpdateAccount(customerId, account);
                    }

                    // Add the customer to the customers map
                    customers.put(customerId, customer);

                    // Load emails for the customer (assuming this method is defined)
                    loadEmailsForCustomer(customer);

                    // Debug: Print the customer with the number of emails loaded
                    System.out.println("Loaded customer " + customer.getCustomerId() + " with " + customer.getSentEmails().size() + " sent emails.");
                }
            }
            System.out.println("Customers loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading customers from file: " + e.getMessage());
        }
    }

    public void saveCustomersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Customer customer : customers.values()) {
                writer.write(customer.getCustomerId() + "," +
                             customer.getName() + "," +
                             customer.getStoreCredit() + "," +
                             customer.getStorePoints() + "," +
                             (customer.getAccount() != null ? customer.getAccount().getBalance() : 0.0) + "," +
                             customer.getSize() + "," +
                             customer.getEmail()); // Save email
                writer.newLine();
            }
            System.out.println("Customers saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving customers to file: " + e.getMessage());
        }
    }

    public Customer findCustomerById(String customerId) {
        return customers.get(customerId);
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        saveCustomersToFile();
    }

    public void displayCustomers() {
        System.out.println("\n--- Customers ---");
        customers.forEach((id, customer) -> System.out.println(customer));
    }

    public Map<String, Customer> getAllCustomers() {
        return customers; // Return the map of all customers
    }
    
    private void loadEmailsForCustomer(Customer customer) {
        String emailFile = "emails.txt";  // Email file to read from

        try (BufferedReader reader = new BufferedReader(new FileReader(emailFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] emailParts = line.split(",");
                if (emailParts.length > 3 && emailParts[0].equals(customer.getCustomerId())) {
                    String subject = emailParts[1];
                    String body = emailParts[2];
                    String offer = emailParts[3];
                    Email email = new Email(subject, body, offer);
                    customer.addSentEmail(email); // Add the email to the customer's sentEmails list
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading emails: " + e.getMessage());
        }

        // Debug print statement to show the number of emails loaded for this customer
        System.out.println("Loaded customer " + customer.getCustomerId() + " with " + customer.getSentEmails().size() + " sent emails.");
    }


}
