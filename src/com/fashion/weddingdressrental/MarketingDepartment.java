package com.fashion.weddingdressrental;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class MarketingDepartment {
    private final CustomerManager customerManager;
    private final Email emailSystem;
    private final CampaignMetrics campaignMetrics;
    private final CustomerReviewManager customerReviewManager;
    private final AdvertisingDepartment advertisingDepartment;

    public MarketingDepartment(CustomerManager customerManager, AdvertisingDepartment advertisingDepartment) {
        this.customerManager = customerManager;
        this.emailSystem = new Email();
        this.campaignMetrics = new CampaignMetrics();
        this.customerReviewManager = new CustomerReviewManager();
        this.advertisingDepartment = advertisingDepartment;
    }

    // Method to send email newsletters
    public void sendEmailNewsletter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the subject of the newsletter: ");
        String subject = scanner.nextLine();
        System.out.print("Enter the body of the email: ");
        String body = scanner.nextLine();
        System.out.print("Enter the discount offer (e.g., 20% off): ");
        String offer = scanner.nextLine();

        // Create the email
        Email email = new Email(subject, body, offer);

        // Send the email to all customers
        for (Customer customer : customerManager.getAllCustomers().values()) {
            email.sendEmail(customer); // Send the email to customer
            customer.addSentEmail(email);  // Store the email in the customer's sent emails list
            saveEmailToFile(email, customer); // Save the email to file
        }

       
        System.out.println("Email newsletter sent successfully!");
    }

    private void saveEmailToFile(Email email, Customer customer) {
        String emailFile = "emails.txt";  // Specify the file where emails will be saved

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(emailFile, true))) {
            writer.write(customer.getCustomerId() + ",");
            writer.write(email.getSubject() + ",");
            writer.write(email.getBody() + ",");
            writer.write(email.getOffer());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving email to file: " + e.getMessage());
        }
    }
    
    public void viewAdvertisingDecisions() {
        String decisionFile = "promotion_decisions.txt";
        File file = new File(decisionFile);

        if (!file.exists()) {
            System.out.println("No decisions have been made by the Advertising Department yet.");
            return;
        }

        System.out.println("\n--- Advertising Department Decisions ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    System.out.println("Promotion ID: " + parts[0]);
                    System.out.println("Decision: " + parts[1]);
                    System.out.println("---");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading decisions file: " + e.getMessage());
        }
    }


    // Create a new promotion
    public void createPromotion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Create a New Promotion ---");
        String promoId = generatePromotionId();
        System.out.println("Generated Promotion ID: " + promoId);

        System.out.print("Enter Promotion Details (e.g., Summer Sale): ");
        String promoDetails = scanner.nextLine();
        System.out.print("Enter Discount Percentage (e.g., 20): ");
        double discountPercentage = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Target Audience (e.g., Young Adults): ");
        String targetAudience = scanner.nextLine();

        // Create and save the promotion
        Promotion promotion = new Promotion(promoId, promoDetails, discountPercentage, targetAudience);
        savePromotionToFile(promotion);

        System.out.println("Promotion created successfully and sent for review!");
    }

    // View pending promotions not yet reviewed by Advertising
    public void viewPendingPromotions() {
        List<Promotion> pendingPromotions = advertisingDepartment.getPendingPromotions();

        if (pendingPromotions.isEmpty()) {
            System.out.println("No pending promotions to review.");
        } else {
            System.out.println("\n--- Pending Promotions ---");
            for (Promotion promotion : pendingPromotions) {
                System.out.println("Promo ID: " + promotion.getPromoId());
                System.out.println("Details: " + promotion.getPromoDetails());
                System.out.println("Discount: " + promotion.getDiscountPercentage() + "%");
                System.out.println("Target Audience: " + promotion.getTargetAudience());
                System.out.println("---");
            }
        }
    }

    private String generatePromotionId() {
        return "PROMO-" + (int) (Math.random() * 10000);
    }

    private void savePromotionToFile(Promotion promotion) {
        String promotionFile = "promotions.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(promotionFile, true))) {
            writer.write(promotion.getPromoId() + "," + promotion.getPromoDetails() + "," +
                    promotion.getDiscountPercentage() + "," + promotion.getTargetAudience());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving promotion: " + e.getMessage());
        }
    }
}
