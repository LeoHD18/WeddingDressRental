package com.fashion.weddingdressrental;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Account {
    private double balance;
    private String accountNumber;

    public Account(double initialBalance) {
        this.balance = initialBalance;
        this.accountNumber = generateAccountNumber();
    }

    public Account(double initialBalance, String accountNumber) {
        this.balance = initialBalance;
        this.accountNumber = accountNumber;
    }

    private String generateAccountNumber() {
        return "ACCT-" + (int) (Math.random() * 100000); // Generates a random 5-digit account number
    }

    // Getters
    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // Method to deposit money into the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Method to withdraw money from the account
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + ". New balance: $" + balance);
            return true;
        } else {
            System.out.println("Insufficient funds or invalid amount.");
            return false;
        }
    }

    // Method to check if there are sufficient funds for a transaction
    public boolean hasSufficientFunds(double amount) {
        return balance >= amount;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Balance: $" + balance;
    }

    // Deduct amount from the balance
    public void deductBalance(double amount, String customerId) {
        if (amount <= balance) {
            balance -= amount;  // Deduct the amount from the balance
            System.out.println("Deducted amount: $" + amount);
            System.out.println("New balance: $" + balance);  // Print new balance after deduction
             // Save the updated account balance to the file
        } else {
            System.out.println("Insufficient funds to deduct $" + amount);
        }
    }

 
}
