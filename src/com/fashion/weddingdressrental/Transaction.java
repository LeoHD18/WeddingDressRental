package com.fashion.weddingdressrental;

import java.util.Date;

public class Transaction {
    
    // Attributes
    private String transactionID;
    private Date startDate;
    private Date endDate;
    private int rentalDuration;
    private WeddingDress dress;
    private Customer customer;
    private Payment payment;

    // Constructor
    public Transaction(String transactionID, Date startDate, Date endDate, WeddingDress dress, Customer customer, Payment payment) {
        this.transactionID = transactionID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentalDuration = calculateDuration();
        this.dress = dress;
        this.customer = customer;
        this.payment = payment;
    }

    // Method to finalize the transaction
    public void finalizeTransaction() {
        // Process the payment (assuming process is a method in Payment class)
        payment.process();
        // Prepare the dress for the next rental
        WashAndPrep washAndPrep = new WashAndPrep();
        washAndPrep.prepareDress(dress);
        System.out.println("Transaction " + transactionID + " has been finalized.");
    }

    // Method to calculate rental duration
    public int calculateDuration() {
        // Example calculation (duration in days)
        long difference = endDate.getTime() - startDate.getTime();
        return (int) (difference / (1000 * 60 * 60 * 24));
    }

    // Getters
    public String getTransactionID() {
        return transactionID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getRentalDuration() {
        return rentalDuration;
    }

    public WeddingDress getDress() {
        return dress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Payment getPayment() {
        return payment;
    }
}