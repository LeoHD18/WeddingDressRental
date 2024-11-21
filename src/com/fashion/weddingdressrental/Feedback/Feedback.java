package com.fashion.weddingdressrental.Feedback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Feedback {
    private String feedbackId;
    private String customerId;
    private String reservationId;
    private String dressId; // Add this field
    private double rating;
    private String comment;
    private Date date;

    public Feedback(String feedbackId, String customerId, String reservationId, String dressId, double rating, String comment, Date date) {
        this.feedbackId = feedbackId;
        this.customerId = customerId;
        this.reservationId = reservationId;
        this.dressId = dressId;
        this.rating = rating; // Accept double here
        this.comment = comment;
        this.date = date;
    }

    // public Feedback(String feedbackId, String customerId, String reservationId, int rating, String comment, Date date) {
    //     this.feedbackId = feedbackId;
    //     this.customerId = customerId;
    //     this.reservationId = reservationId;
    //     this.rating = rating;
    //     this.comment = comment;
    //     this.date = date;
    // }

    // Add a getter for Dress ID
    public String getDressId() {
        return dressId;
    }

    public static Feedback fromCSV(String csv) {
        try {
            // Split while respecting quoted fields
            String[] parts = csv.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
            // Remove quotes around the comment if present
            String comment = parts[5].replaceAll("^\"|\"$", "");
    
            return new Feedback(
                parts[0], // feedbackId
                parts[1], // customerId
                parts[2], // reservationId
                parts[3], // dressId
                Double.parseDouble(parts[4]), // Parse rating as double
                comment,
                sdf.parse(parts[6]) // date
            );
        } catch (Exception e) {
            System.out.println("Error parsing feedback: " + e.getMessage());
            return null;
        }
    }

    // Modify the `toCSV` and `fromCSV` methods to include the dress ID
    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%s,%s,%s,%s,%.1f,\"%s\",%s", // %.1f for one decimal place
                feedbackId, customerId, reservationId, dressId, rating, comment, sdf.format(date));
    }


    // public static Feedback fromCSV(String csv) {
    //     try {
    //         // Split while respecting quoted fields
    //         String[] parts = csv.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    //         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    //         // Remove quotes around the comment if present
    //         String comment = parts[4].replaceAll("^\"|\"$", "");
    
    //         // Extract dressId from reservationId
    //         String dressId = parts[2].split("-")[1]; // Assuming reservationId has a format like RES-CUST-337-D005
    
    //         return new Feedback(
    //             parts[0], // feedbackId
    //             parts[1], // customerId
    //             parts[2], // reservationId
    //             dressId, // Extracted dressId
    //             Integer.parseInt(parts[3]), // rating
    //             comment, // Properly formatted comment
    //             sdf.parse(parts[5]) // date
    //         );
    //     } catch (Exception e) {
    //         System.out.println("Error parsing feedback: " + e.getMessage());
    //         return null;
    //     }
    // }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("[%.1f stars] %s - %s (%s)", rating, comment, customerId, reservationId);
    }
}