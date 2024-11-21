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
    private String email;  // New field for customer email
    private List<AlterationRequest> alterationRequests;
    private List<Reservation> reservations;
    private Account account;
    private List<Email> sentEmails;  // New list to store sent emails

    public Customer(String customerId, String name, double storeCredit, int storePoints, String size, String email) {
        this.customerId = customerId;
        this.name = name;
        this.storeCredit = storeCredit;
        this.storePoints = storePoints;
        this.size = size;
        this.email = email;  // Initialize email
        this.alterationRequests = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.sentEmails = new ArrayList<>();  // Initialize sent emails list
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    // Method to add sent email to the customer's sentEmails list
    public void addSentEmail(Email email) {
        sentEmails.add(email);
    }

    // Getter for sent emails list
    public List<Email> getSentEmails() {
        return sentEmails;
    }

    public boolean deductFromAccount(double amount) {
        if (account != null && account.hasSufficientFunds(amount)) {
            account.deductBalance(amount, customerId);
            return true;
        } else if (account == null) {
            System.out.println("No account found for customer " + name);
        } else {
            System.out.println("Insufficient funds in account for customer " + name);
        }
        return false;
    }

    public AlterationRequest requestAlteration(String details, Date completionDate, Employee employee) {
        final double alterationCost = 10.0;
        if (account == null) {
            System.out.println("Alteration request failed: No account found for customer " + name);
            return null;
        }
        if (deductFromAccount(alterationCost)) {
            AlterationRequest alterationRequest = new AlterationRequest(this, details, alterationCost, completionDate, employee);
            alterationRequests.add(alterationRequest);
            AlterationManager.saveAlterationsToFile(alterationRequests);
            System.out.println("Alteration request created for customer " + name + " with details: " + alterationRequest);
            return alterationRequest;
        } else {
            System.out.println("Alteration request could not be processed due to insufficient funds.");
            return null;
        }
    }

    public boolean purchaseGiftCard(double amount) {
        if (amount < 10) {
            System.out.println("Gift card purchase failed: Minimum amount is $10.");
            return false;
        }
        if (account == null) {
            System.out.println("Gift card purchase failed: No account found for customer " + name);
            return false;
        }
        if (deductFromAccount(amount)) {
            System.out.println("Gift card purchased successfully for customer " + name + " with amount $" + amount);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + ", Store Credit: $" + storeCredit + 
               ", Store Points: " + storePoints + ", Preferred Size: " + size + ", Email: " + email;
    }
}
