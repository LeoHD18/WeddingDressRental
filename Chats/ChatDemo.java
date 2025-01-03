package Chats;

import java.util.Scanner;

/**
 * Demonstrates a chat interface simulation for customer and support interactions.
 * This class simulates a customer starting a chat, sending messages, and receiving automated responses.
 */
public class ChatDemo {

    /**
     * The main method that starts the chat simulation.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Create chat directory if it doesn't exist
            new java.io.File("data/chats").mkdirs();

            // Simulate a customer starting a chat
            String customerId = "CUST001";
            CustomerChatUI customerChat = new CustomerChatUI(customerId);

            // Create a scanner for user input
            Scanner scanner = new Scanner(System.in);
            System.out.println("Chat started! Type your messages (type 'exit' to quit)");

            // Simple chat loop
            String message;
            while (true) {
                System.out.print("You: ");
                message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                if (!message.trim().isEmpty()) {
                    customerChat.sendMessage(message);
                    System.out.println("Notification: Message sent to support.");
                    simulateSupportResponse(customerId, message);
                }
            }

            // Clean up
            customerChat.closeChat();
            scanner.close();
            System.out.println("Chat ended!");

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Simulates a response from support after a customer message.
     * 
     * @param customerId      The ID of the customer.
     * @param customerMessage The customer's message that support is responding to.
     */
    private static void simulateSupportResponse(String customerId, String customerMessage) {
        new Thread(() -> {
            try {
                Thread.sleep(1500); // Wait 1.5 seconds
                ChatService supportChat = new ChatService();
                String responseText = generateSupportResponse(customerMessage);
                ChatMessage response = new ChatMessage(
                    "Support",
                    responseText,
                    true
                );
                supportChat.sendMessage(customerId, response);
                System.out.println("Notification: New message from support.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Support response interrupted");
            } catch (Exception e) {
                System.out.println("Error in support response: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Generates a response message for support based on the customer's input.
     *
     * @param customerMessage The message sent by the customer.
     * @return A response message generated by support.
     */
    private static String generateSupportResponse(String customerMessage) {
        // Simple logic to generate varied responses
        if (customerMessage.toLowerCase().contains("dress")) {
            return "We have a wide selection of dresses. What style are you looking for?";
        } else if (customerMessage.toLowerCase().contains("help")) {
            return "I'm here to help! What do you need assistance with?";
        } else {
            return "Thank you for your message. How can I assist you further?";
        }
    }
}