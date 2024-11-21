package com.fashion.weddingdressrental;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Email {
    private String subject;
    private String body;
    private String offer;

    public Email(String subject, String body, String offer) {
        this.subject = subject;
        this.body = body;
        this.offer = offer;
    }

    public Email() {}  // Default constructor for flexibility

    public void sendEmail(Customer customer) {
        // Assuming that the customer class has an email field.
        String customerEmail = customer.getEmail();  // Retrieve the email address

        if (customerEmail == null || customerEmail.isEmpty()) {
            System.out.println("Email address not found for customer: " + customer.getName());
            return;
        }

        // Simulate sending an email by printing the email details
        System.out.println("Sending email to: " + customerEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("Offer: " + offer);
        System.out.println("--- Email sent successfully ---");


    }

    private void saveEmailToFile(Customer customer) {
        String emailFile = "emails.txt";  // Specify the file where emails will be saved

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(emailFile, true))) {
            writer.write("Customer ID: " + customer.getCustomerId());
            writer.newLine();
            writer.write("Customer Name: " + customer.getName());
            writer.newLine();
            writer.write("Customer Email: " + customer.getEmail());
            writer.newLine();
            writer.write("Subject: " + subject);
            writer.newLine();
            writer.write("Body: " + body);
            writer.newLine();
            writer.write("Offer: " + offer);
            writer.newLine();
            writer.write("--- End of Email ---");
            writer.newLine();
            writer.newLine();  // Add a blank line between emails
        } catch (IOException e) {
            System.out.println("Error saving email to file: " + e.getMessage());
        }
    }
    
    

    // Getters and Setters for the fields
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
