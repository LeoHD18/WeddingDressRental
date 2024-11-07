package com.fashion.weddingdressrental;

import java.util.Date;

public class WashAndPrep {

    // Attributes
    private String cleaningStatus;
    private Date prepDate;

    // Constructor
    public WashAndPrep() {
        this.cleaningStatus = "Not Cleaned";
        this.prepDate = new Date();
    }

    // Method to prepare the dress by cleaning it
    public void prepareDress(Dress dress) {
        System.out.println("Preparing the dress...");
        this.cleaningStatus = "In Progress";
        
        // Simulate cleaning process
        System.out.println("Cleaning the dress...");
        this.cleaningStatus = "Ready";
        this.prepDate = new Date();
        
        dress.setAvailable(true); // Mark the dress as available for next rental
        System.out.println("Dress " + dress.getDressID() + " has been cleaned and prepped.");
    }

    // Method to check the cleaning status
    public String checkStatus() {
        return this.cleaningStatus;
    }

    // Getters
    public String getCleaningStatus() {
        return cleaningStatus;
    }

    public Date getPrepDate() {
        return prepDate;
    }
}