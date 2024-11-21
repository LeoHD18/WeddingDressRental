package com.fashion.weddingdressrental;

import java.util.Date;

public class CustomerReview {
    private Customer customer;
    private String reviewContent;
    private int rating;
    private Date date;

    public CustomerReview(Customer customer, String reviewContent, int rating) {
        this.customer = customer;
        this.reviewContent = reviewContent;
        this.rating = rating;
        this.date = new Date();
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public int getRating() {
        return rating;
    }

    public Date getDate() {
        return date;
    }
}