package com.fashion.weddingdressrental;

public class Payment {
    private double amount;
    private String status;
    private PaymentType paymentMethod;
    private Employee employee;
    private Customer customer;

    public Payment(double amount, Customer customer, Employee employee, PaymentType paymentMethod, String status) {
        this.amount = amount;
        this.customer = customer;
        this.employee = employee;
        this.paymentMethod = paymentMethod;
        this.status = "Pending";
    }

    

    public boolean authDebitCard(Customer c, double amount, int id) {
        if(c.getAccount().getId() != id ) {
            System.out.println("Invalid card");
            return false;
        } else {
            double balance = c.getAccount().getBalance();
            if(balance < amount) {
                System.out.println("Card decline! Not enough balance");
                return false;
            } else {
                c.getAccount().setBalance(balance -=  amount);
                System.out.println("Sucessfully paid");
                return true;
            }
        }
    }

    public void process() {
        this.status = "Paid";
        System.out.println("Payment of $" + amount + " has been processed.");
    }

    public String getStatus() {
        return status;
    }
}