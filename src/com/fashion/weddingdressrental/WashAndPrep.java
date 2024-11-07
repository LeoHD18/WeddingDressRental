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
        // Assuming that cleaning the dress involves setting it as "Cleaned"
        this.cleaningStatus = "Cleaned";
        this.prepDate = new Date();
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