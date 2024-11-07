package com.fashion.weddingdressrental;

public class Payment {
    private double amount;
    private String status;
    private PaymentType paymentMethod;
    private Employee employee;
    private Customer customer;
    private Transaction transaction;

    public Payment(double amount, Customer customer, Employee employee, PaymentType paymentMethod, Transaction transaction) {
        this.amount = amount;
        this.customer = customer;
        this.employee = employee;
        this.paymentMethod = paymentMethod;
        this.transaction = transaction;
        this.status = "Pending";
    }


    public boolean checkoutDebitCard(int CardId) {
        if(customer.getAccount().getId() != CardId ) {
            System.out.println("Invalid card");
            return false;
        } else {
            double balance = customer.getAccount().getBalance();
            if(balance < amount) {
                System.out.println("Card declined! Not enough balance");
                return false;
            } else {
                customer.getAccount().setBalance(balance -=  amount);
                System.out.println("Sucessfully paid");
                this.status = "Paid by Debit Card";
                transaction.getDress().setAvailable(false);
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