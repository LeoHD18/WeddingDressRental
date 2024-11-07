package com.fashion.weddingdressrental;

import java.util.Date;

public class Customer {
    private String name;
    private double storeCredit;
    private int storePoints;
    private BankAccount account;
    private Size size;

    public Customer(String name) {
        this.name = name;
    }

    // Method to request an alteration, processed by an Employee
    public AlterationRequest requestAlteration(String details, double cost, Date completionDate, Employee employee) {
        // Generate a unique alteration ID
        String alterationId = "ALT-" + name.substring(0, 3).toUpperCase() + "-" + (int)(Math.random() * 1000);
        AlterationRequest alterationRequest = new AlterationRequest(alterationId, details, cost, completionDate);

        System.out.println("Customer " + name + " has requested an alteration: " + alterationRequest);

        // Employee processes the alteration request
        employee.processAlterationRequest(this, alterationRequest);

        return alterationRequest;
    }

    // Getter and Setter methods
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
