package com.fashion.weddingdressrental;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerReviewManager {

    private static final String REVIEW_FILE_PATH = "customer_reviews.txt";
    private List<CustomerReview> reviews;

    public CustomerReviewManager() {
        reviews = new ArrayList<>();
    }

    // Method to collect review from a customer
    public void collectCustomerReview(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter review for " + customer.getName() + ": ");
        String reviewContent = scanner.nextLine();
        System.out.println("Enter rating (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        // Create a new review and add it to the list
        CustomerReview review = new CustomerReview(customer, reviewContent, rating);
        reviews.add(review);
    }

    // Method to save all collected reviews to a file
    public void saveReviewsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REVIEW_FILE_PATH, true))) {
            for (CustomerReview review : reviews) {
                writer.write(review.getCustomer().getCustomerId() + "," +
                             review.getReviewContent() + "," +
                             review.getRating() + "," +
                             review.getDate().getTime());
                writer.newLine();
            }
            System.out.println("Customer reviews saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving customer reviews: " + e.getMessage());
        }
    }

    

    // Save each review to a file
    public void saveReview(CustomerReview review) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REVIEW_FILE_PATH, true))) {
            writer.write(review.getCustomer().getCustomerId() + "," +
                         review.getReviewContent() + "," +
                         review.getRating() + "," +
                         review.getDate().getTime());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving customer review: " + e.getMessage());
        }
    }
}
