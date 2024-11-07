package com.fashion.weddingdressrental;

import java.util.Random;

public class Payment {
    private double amount;
    private String status;
    private PaymentType paymentMethod;
    private Employee employee;
    private Customer customer;
    private Transaction transaction;
    private Store store;

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
                if (transaction.getType() == TransactionType.RENTAL_SALE)
                {
                    transaction.getDress().setAvailable(false);
                    
                }
                else if (transaction.getType() == TransactionType.GIFT_CARD_SALE) 
                {
                    Random random = new Random();
                    int randomId = random.nextInt(1000) + 1;
                    GiftCard gift = new GiftCard(amount, randomId);
                    transaction.getStore().getListGift().add(gift);
                    System.out.println("Here is your gift card: ID: " + randomId + ", amount " + amount);
                   
                    
                }
                
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
