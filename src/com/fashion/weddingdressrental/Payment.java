package com.fashion.weddingdressrental;

public class Payment {
    private double amount;
    private String status;

    public Payment(double amount) {
        this.amount = amount;
        this.status = "Pending";
    }

    public void process() {
        this.status = "Paid";
        System.out.println("Payment of $" + amount + " has been processed.");
    }

    public String getStatus() {
        return status;
    }
}