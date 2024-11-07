package com.fashion.weddingdressrental;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private double storeCredit;
    private int storePoints;
    private String size;
    private List<AlterationRequest> alterationRequests;
    private List<Reservation> reservations;
    private Account account;

    public Customer(String customerId, String name, double storeCredit, int storePoints, String size) {
        this.customerId = customerId;
        this.name = name;
        this.storeCredit = storeCredit;
        this.storePoints = storePoints;
        this.size = size;
        this.alterationRequests = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<AlterationRequest> getAlterationRequests() {
        return alterationRequests;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AlterationRequest requestAlteration(String details, Date completionDate, Employee employee) {
        final double alterationCost = 10.0;

        if (account != null && account.hasSufficientFunds(alterationCost)) {
            account.deductBalance(alterationCost);
            AlterationRequest alterationRequest = new AlterationRequest(this, details, alterationCost, completionDate, employee);
            alterationRequests.add(alterationRequest);
            AlterationManager.saveAlterationsToFile(alterationRequests); // Save after creation
            System.out.println("Alteration request created for customer " + name + " with details: " + alterationRequest);
            return alterationRequest;
        } else {
            System.out.println("Insufficient funds for alteration request for customer " + name);
            return null;
        }
    }


    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + ", Store Credit: $" + storeCredit + 
               ", Store Points: " + storePoints + ", Preferred Size: " + size;
    }
}
