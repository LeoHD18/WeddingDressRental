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
                if (parts.length == 6) {
                    String customerId = parts[0];
                    String name = parts[1];
                    double storeCredit = Double.parseDouble(parts[2]);
                    int storePoints = Integer.parseInt(parts[3]);
                    double accountBalance = Double.parseDouble(parts[4]);
                    String preferredSize = parts[5];

                    Customer customer = new Customer(customerId, name, storeCredit, storePoints, preferredSize);
                    // Attempt to find the account associated with the customerId
                    Account account = accountManager.findAccountByCustomerId(customerId);
                    if (account != null) {
                        customer.setAccount(account);
                    } else {
                        // If no account is found, initialize an account with zero balance
                        account = new Account(0.0, customerId);
                        customer.setAccount(account);
                        accountManager.addOrUpdateAccount(customerId, account); // Save new account to file
                    }
                    customers.put(customerId, customer);
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
                             (customer.getSize() != null ? customer.getSize() : ""));
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
}
