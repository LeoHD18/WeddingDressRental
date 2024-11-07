package com.fashion.weddingdressrental;

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

    public void deductBalance(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Deducted $" + amount + " from account. New balance: $" + balance);
        } else if (amount <= 0) {
            System.out.println("Invalid deduction amount.");
        } else {
            System.out.println("Insufficient funds to complete this transaction.");
        }
    }

}
