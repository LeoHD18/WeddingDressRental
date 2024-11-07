package com.fashion.weddingdressrental;

public class Dress {
    private String dressID;
    private boolean isAvailable;
    private double price;           // Rental price per day
    private String condition;       // e.g., "New", "Good", "Under Repair"
    private String size;            // e.g., "S", "M", "L"
    private boolean isAltered;      // Indicates if any alterations have been applied

    // Constructor
    public Dress(String dressID, double price, String condition, String size) {
        this.dressID = dressID;
        this.isAvailable = true;
        this.price = price;
        this.condition = condition;
        this.size = size;
        this.isAltered = false;
    }

    // Getters and Setters
    public String getDressID() {
        return dressID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isAltered() {
        return isAltered;
    }

    public void setAltered(boolean isAltered) {
        this.isAltered = isAltered;
    }

    // Method to mark the dress as reserved
    public void reserveDress() {
        if (isAvailable) {
            setAvailable(false);
            System.out.println("Dress " + dressID + " is now reserved.");
        } else {
            System.out.println("Dress " + dressID + " is already reserved or unavailable.");
        }
    }

    // Method to mark the dress as returned
    public void returnDress() {
        setAvailable(true);
        System.out.println("Dress " + dressID + " is now available for rental.");
    }

    // Method to mark the dress as needing repair
    public void sendForRepair() {
        setCondition("Under Repair");
        setAvailable(false);
        System.out.println("Dress " + dressID + " is sent for repair and marked as unavailable.");
    }

    // Method to check if the dress is ready for rental
    public void prepareForRental() {
        setCondition("Good");
        setAvailable(true);
        setAltered(false);  // Reset alteration status
        System.out.println("Dress " + dressID + " is prepared and available for rental.");
    }

    @Override
    public String toString() {
        return "Dress ID: " + dressID + ", Price: $" + price + ", Condition: " + condition + ", Size: " + size + 
               ", Available: " + isAvailable + ", Altered: " + isAltered;
    }
}
