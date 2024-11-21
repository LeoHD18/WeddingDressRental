package com.fashion.weddingdressrental;

public class CampaignMetrics {
    private double openRate;
    private double clickRate;
    private double conversionRate;

    // Tracks the campaign metrics (simulating tracking)
    public void trackCampaign(Email email) {
        // Simulate tracking metrics (usually involves actual email statistics)
        openRate = Math.random();
        clickRate = Math.random();
        conversionRate = Math.random();

        // Printing the email's details to simulate tracking
        System.out.println("Tracking campaign metrics for email: " + email);
    }

    // Analyze and display the tracked campaign metrics
    public void analyzeMetrics() {
        System.out.println("Campaign Metrics:");
        System.out.println("Open Rate: " + String.format("%.2f", openRate * 100) + "%");
        System.out.println("Click Rate: " + String.format("%.2f", clickRate * 100) + "%");
        System.out.println("Conversion Rate: " + String.format("%.2f", conversionRate * 100) + "%");
    }

    // Analyze a customer's response to a marketing campaign (simplified version)
    public CampaignMetrics analyzeCustomerResponses(Customer customer) {
        // Simulate customer campaign response analysis (this would normally be more complex)
        // For example, we might track if the customer opened an email or made a purchase
        double responseRate = Math.random();  // Random for now to simulate response rate

        // Print out analysis result (simulating response to campaign)
        System.out.println("Analyzing customer " + customer.getName() + "'s response:");
        System.out.println("Customer Response Rate: " + String.format("%.2f", responseRate * 100) + "%");

        // Return a new instance with simulated metrics for the customer
        CampaignMetrics customerMetrics = new CampaignMetrics();
        customerMetrics.openRate = responseRate;  // Just simulating for now
        customerMetrics.clickRate = responseRate; // Similar simulation
        customerMetrics.conversionRate = responseRate; // Similar simulation

        return customerMetrics; // Return a CampaignMetrics object specific to the customer
    }

    @Override
    public String toString() {
        return "CampaignMetrics{" +
                "openRate=" + openRate * 100 + "%" +
                ", clickRate=" + clickRate * 100 + "%" +
                ", conversionRate=" + conversionRate * 100 + "%" +
                '}';
    }
}
