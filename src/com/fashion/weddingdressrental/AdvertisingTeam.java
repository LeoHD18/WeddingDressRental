package com.fashion.weddingdressrental;

import java.util.ArrayList;
import java.util.List;

public class AdvertisingTeam {
    private String teamName;
    private String focus;
    private List<Promotion> promotions;  // Store the promotions created by the advertising team

    public AdvertisingTeam(String teamName, String focus) {
        this.teamName = teamName;
        this.focus = focus;
        this.promotions = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public String getFocus() {
        return focus;
    }

    // Method to create a promotion
    public void createPromotion(Promotion promotion) {
        promotions.add(promotion);  // Add the new promotion to the list of promotions
        System.out.println("Advertising team has created a promotion: " + promotion.getPromoDetails());
    }

    // Optionally, display all promotions created by the advertising team
    public void displayPromotions() {
        System.out.println("--- Promotions Created by " + teamName + " ---");
        for (Promotion promotion : promotions) {
            System.out.println("Promotion ID: " + promotion.getPromoId());
            System.out.println("Details: " + promotion.getPromoDetails());
            System.out.println("Discount: " + promotion.getDiscountPercentage() + "%");
            System.out.println("Target Audience: " + promotion.getTargetAudience());
            System.out.println("-------------");
        }
    }

	
}

