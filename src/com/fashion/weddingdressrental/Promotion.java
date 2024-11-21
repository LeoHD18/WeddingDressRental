package com.fashion.weddingdressrental;

public class Promotion {
    private String promoId;
    private String promoDetails;
    private double discountPercentage;
    private String targetAudience;

    public Promotion(String promoId, String promoDetails, double discountPercentage, String targetAudience) {
        this.promoId = promoId;
        this.promoDetails = promoDetails;
        this.discountPercentage = discountPercentage;
        this.targetAudience = targetAudience;
    }

    public String getPromoId() {
        return promoId;
    }

    public String getPromoDetails() {
        return promoDetails;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public String getTargetAudience() {
        return targetAudience;
    }
}
