package com.fashion.weddingdressrental;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // // Create instances of required objects
        // WeddingDress dress = new WeddingDress("WD-001");
        // Customer customer = new Customer("Alice");
        // Payment payment = new Payment(200.0);
        
        // // Create a Transaction
        // Date startDate = new Date();
        // Date endDate = new Date(startDate.getTime() + (5 * 24 * 60 * 60 * 1000)); // 5 days later
        
        // Transaction transaction = new Transaction("TXN-1001", startDate, endDate, dress, customer, payment);
        
        // // Finalize the transaction
        // transaction.finalizeTransaction();
        
        // // Check the cleaning status after the transaction
        // WashAndPrep washAndPrep = new WashAndPrep();
        // System.out.println("Cleaning Status: " + washAndPrep.checkStatus());
        Dress dress = new Dress("haha");
        BankAccount checking = new BankAccount(200, 12354);
        Customer customer = new Customer("Alice");
        customer.setAccount(checking);
        Employee e = new Employee("Huy");
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + (5 * 24 * 60 * 60 * 1000)); // 5 days later
        Transaction trans = new Transaction("123", startDate, endDate, dress, customer, null);
        Payment pay = new Payment(100, customer, e, PaymentType.DEBIT_CARD, trans);
        pay.checkoutDebitCard(12354);
    }
}