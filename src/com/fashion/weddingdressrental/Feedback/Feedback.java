package com.fashion.weddingdressrental.Feedback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Feedback {
    private String feedbackId;
    private String customerId;
    private String reservationId;
    private int rating;
    private String comment;
    private Date date;

    public Feedback(String feedbackId, String customerId, String reservationId, int rating, String comment, Date date) {
        this.feedbackId = feedbackId;
        this.customerId = customerId;
        this.reservationId = reservationId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%s,%s,%s,%d,%s,%s",
                feedbackId, customerId, reservationId, rating, comment, sdf.format(date));
    }

    public static Feedback fromCSV(String csv) {
        try {
            String[] parts = csv.split(",");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return new Feedback(
                parts[0],
                parts[1],
                parts[2],
                Integer.parseInt(parts[3]),
                parts[4],
                sdf.parse(parts[5])
            );
        } catch (Exception e) {
            System.out.println("Error parsing feedback: " + e.getMessage());
            return null;
        }
    }

    public String getDressId() {
        return reservationId.split("-")[1]; // Assuming ReservationID includes DressID
    }

    @Override
    public String toString() {
        return String.format("[%d stars] %s - %s (%s)", rating, comment, customerId, reservationId);
    }
}
