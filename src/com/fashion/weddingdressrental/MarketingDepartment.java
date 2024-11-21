package com.fashion.weddingdressrental;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MarketingDepartment {
    private final CustomerManager customerManager;
    private final Email emailSystem;
    private final CampaignMetrics campaignMetrics;
    private final CustomerReviewManager customerReviewManager;
    private final AdvertisingTeam advertisingTeam; 

    public MarketingDepartment(CustomerManager customerManager) {
        this.customerManager = customerManager;
        this.emailSystem = new Email();
        this.campaignMetrics = new CampaignMetrics();
        this.customerReviewManager = new CustomerReviewManager();
//        this.advertisingTeam = advertisingTeam;
		this.advertisingTeam = null;
        
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

        // Track the campaign metrics
        campaignMetrics.trackCampaign(email);
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

    // Method to analyze the effectiveness of a campaign
    public void analyzeCampaignEffectiveness() {
        System.out.println("Analyzing Campaign Effectiveness...");
        campaignMetrics.analyzeMetrics();
    }

    // Method to gather and save customer reviews
    public void gatherCustomerReviews() {
        System.out.println("Gathering Customer Reviews...");

        // Loop over customers to get their reviews
        for (Customer customer : customerManager.getAllCustomers().values()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter review for " + customer.getName() + ": ");
            String reviewContent = scanner.nextLine();
            System.out.print("Enter rating (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine();  // consume newline

            // Create a review and save it
            CustomerReview review = new CustomerReview(customer, reviewContent, rating);
            customerReviewManager.saveReview(review);
        }

        System.out.println("Customer reviews gathered successfully!");
    }
    
    public void collaborateWithAdvertisingTeam() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Collaborating to create a promotion...");

        // Generate random Promotion ID
        String promoId = generatePromotionId();
        System.out.println("Generated Promotion ID: " + promoId);

        // Get promotion details from the user
        System.out.print("Enter the Promotion Details (e.g., Summer Sale, Christmas Offer): ");
        String promoDetails = scanner.nextLine();
        System.out.print("Enter the Discount Percentage (e.g., 20 for 20%): ");
        double discountPercentage = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter the Target Audience (e.g., Young Adults, Bride-to-Be): ");
        String targetAudience = scanner.nextLine();

        // Create a promotion object (for internal processing)
        Promotion promotion = new Promotion(promoId, promoDetails, discountPercentage, targetAudience);

        // Save the promotion to a file
        savePromotionToFile(promotion);

        // Display success message
        System.out.println("Promotion created and saved successfully!");
    }

    // Helper method to generate random Promotion ID
    private String generatePromotionId() {
        return "PROMO-" + (int) (Math.random() * 10000);  // Generate a random 4-digit Promo ID
    }

    void loadPromotionsFromFile() {
        String promotionFile = "promotions.txt";

        File file = new File(promotionFile);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Promotions file created: " + promotionFile);
                }
            } catch (IOException e) {
                System.out.println("Error creating promotions file: " + e.getMessage());
            }
        }
    }

    private void savePromotionToFile(Promotion promotion) {
        String promotionFile = "promotions.txt";
        loadPromotionsFromFile(); // Ensure the file exists
        System.out.println("Attempting to save promotion to file: " + promotionFile);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(promotionFile, true))) {
            writer.write("Promo ID: " + promotion.getPromoId());
            writer.newLine();
            writer.write("Promo Details: " + promotion.getPromoDetails());
            writer.newLine();
            writer.write("Discount Percentage: " + promotion.getDiscountPercentage() + "%");
            writer.newLine();
            writer.write("Target Audience: " + promotion.getTargetAudience());
            writer.newLine();
            writer.write("--- End of Promotion ---");
            writer.newLine();
            writer.newLine();  // Add a blank line between promotions
            System.out.println("Promotion saved successfully to file: " + promotionFile);
        } catch (IOException e) {
            System.out.println("Error saving promotion to file: " + e.getMessage());
        }
    }

    public void displayPromotions() {
        String promotionFile = "promotions.txt";
        System.out.println("\n--- Promotions ---");

        try (BufferedReader reader = new BufferedReader(new FileReader(promotionFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading promotions file: " + e.getMessage());
        }
    }

}
