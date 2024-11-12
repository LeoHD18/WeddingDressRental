package com.fashion.weddingdressrental;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private static final String FILE_PATH = "bank.txt";
    private Map<String, Account> accounts; // Map customerID to Account

    public AccountManager() {
        accounts = new HashMap<>();
        loadAccountsFromFile();
    }

    // Load accounts from file
    private void loadAccountsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String customerId = parts[0]; // Customer ID
                    String accountNumber = parts[1]; // Account Number
                    double balance = Double.parseDouble(parts[2]); // Account Balance

                    Account account = new Account(balance, accountNumber);
                    accounts.put(customerId, account); // Map customerID to Account
                }
            }
            System.out.println("Accounts loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading accounts from file: " + e.getMessage());
        }
    }

    // Save all accounts to file
    public void saveAccountsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Account> entry : accounts.entrySet()) {
                String customerId = entry.getKey();
                Account account = entry.getValue();
                writer.write(customerId + "," + account.getAccountNumber() + "," + account.getBalance());
                writer.newLine();
            }
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving accounts to file: " + e.getMessage());
        }
    }

    // Find account by customer ID
    public Account findAccountByCustomerId(String customerId) {
        return accounts.get(customerId);
    }

    // Add or update an account
    public void addOrUpdateAccount(String customerId, Account account) {
        accounts.put(customerId, account);
        saveAccountsToFile();
    }
}
