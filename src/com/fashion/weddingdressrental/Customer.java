package com.fashion.weddingdressrental;

public class Customer {
    private String name;
    private double storeCredit;
    private int storePoints;
    private BankAccount account;
    private Size size;

    public Customer(String name) {
        this.name = name;
    }


    

    public String getName() {
        return name;
    }

    public double getStoreCredit() {
        return storeCredit;
    }

    public void setStoreCredit(double storeCredit) {
        this.storeCredit = storeCredit;
    }

    public int getStorePoints() {
        return storePoints;
    }

    public void setStorePoints(int storePoints) {
        this.storePoints = storePoints;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}