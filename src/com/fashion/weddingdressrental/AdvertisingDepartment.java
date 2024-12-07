package com.fashion.weddingdressrental;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdvertisingDepartment {
    private final Scanner scanner = new Scanner(System.in);
    private final SocialMediaTeam socialMediaTeam = new SocialMediaTeam();

    // Display all promotions and allow selecting one for review
    public void reviewPromotionsInteractive() {
        List<Promotion> pendingPromotions = getPendingPromotions();

        if (pendingPromotions.isEmpty()) {
            System.out.println("No promotions available for review.");
            return;
        }

        System.out.println("\n--- Pending Promotions ---");
        for (int i = 0; i < pendingPromotions.size(); i++) {
            Promotion promotion = pendingPromotions.get(i);
            System.out.printf("%d. Promo ID: %s | Details: %s | Discount: %.2f%%\n",
                i + 1, promotion.getPromoId(), promotion.getPromoDetails(), promotion.getDiscountPercentage());
        }

        System.out.print("\nSelect a promotion to review (1-" + pendingPromotions.size() + "): ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number between 1 and " + pendingPromotions.size());
            scanner.nextLine(); // Clear invalid input
            return;
        }

        if (choice < 1 || choice > pendingPromotions.size()) {
            System.out.println("Invalid choice. Returning to menu.");
            return;
        }

        // Review the selected promotion
        Promotion selectedPromotion = pendingPromotions.get(choice - 1);
        String decision = reviewPromotion(selectedPromotion);

        saveDecision(selectedPromotion, decision);

        if ("Approved".equalsIgnoreCase(decision)) {
            socialMediaTeam.postToSocialMedia(selectedPromotion); // Save and post
            System.out.println("Promotion approved and posted on social media.");
        } else {
            System.out.println("Promotion rejected.");
        }
    }

    // Load promotions from file
    public List<Promotion> loadPromotionsFromFile() {
        String promotionFile = "promotions.txt";
        List<Promotion> promotions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(promotionFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    promotions.add(new Promotion(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading promotions: " + e.getMessage());
        }

        return promotions;
    }

    // Get promotions that haven't been reviewed yet
    public List<Promotion> getPendingPromotions() {
        List<Promotion> promotions = loadPromotionsFromFile();
        List<Promotion> pendingPromotions = new ArrayList<>();

        for (Promotion promotion : promotions) {
            if (!isReviewed(promotion)) {
                pendingPromotions.add(promotion);
            }
        }
        return pendingPromotions;
    }

    private String reviewPromotion(Promotion promotion) {
        System.out.println("\n--- Review Promotion ---");
        System.out.println("Promo ID: " + promotion.getPromoId());
        System.out.println("Details: " + promotion.getPromoDetails());
        System.out.println("Discount: " + promotion.getDiscountPercentage() + "%");

        while (true) {
            System.out.print("Do you approve this promotion? (yes/no): ");
            String decision = scanner.nextLine().trim().toLowerCase();

            if ("yes".equals(decision)) {
                return "Approved";
            } else if ("no".equals(decision)) {
                return "Rejected";
            } else {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        }
    }

    private void saveDecision(Promotion promotion, String decision) {
        String decisionFile = "promotion_decisions.txt";
        File file = new File(decisionFile);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Decision file created: " + decisionFile);
                }
            } catch (IOException e) {
                System.out.println("Error creating decision file: " + e.getMessage());
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(promotion.getPromoId() + "," + decision);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving decision: " + e.getMessage());
        }
    }

    private boolean isReviewed(Promotion promotion) {
        String decisionFile = "promotion_decisions.txt";
        File file = new File(decisionFile);

        // If the file doesn't exist, no promotions have been reviewed
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(promotion.getPromoId())) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking if promotion is reviewed: " + e.getMessage());
        }

        return false;
    }
}
