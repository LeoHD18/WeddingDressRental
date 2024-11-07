package com.fashion.weddingdressrental;

public class BankAccount {
    private int id;
    private double balance;

    public BankAccount(double balance, int id) {
        this.balance = balance;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
}
