package com.fashion.weddingdressrental;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerManager {
    private Map<String, Customer> customers;
    private static final String FILE_PATH = "C:\\Users\\yvarun79\\Desktop\\WeddingDressRental\\src\\com\\fashion\\weddingdressrental\\customer.txt";

    public CustomerManager() {
        customers = new HashMap<>();
        loadCustomersFromFile();
    }

    // Load customer data from the file
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
                    Size preferredSize = parts[5].isEmpty() ? null : Size.valueOf(parts[5]);

                    Customer customer = new Customer(customerId, name, storeCredit, storePoints, preferredSize != null ? preferredSize.toString() : "");
                    customer.setAccount(new Account(accountBalance));

                    customers.put(customerId, customer);
                }
            }
            System.out.println("Customers loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading customers from file: " + e.getMessage());
        }
    }

    // Save customer data to the file
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
