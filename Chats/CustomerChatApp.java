package Chats;

import java.util.Scanner;

/**
 * A simple console application for simulating customer chats with support.
 * Customers can send messages to support, and the chat session is managed through a `CustomerChatUI` instance.
 */
public class CustomerChatApp {

    /**
     * The entry point for the customer chat application.
     * 
     * @param args Command-line arguments. The first argument should be the customer's name.
     *             If no argument is provided, the application will display usage instructions and terminate.
     */
    public static void main(String[] args) {
        // Validate command-line arguments
        if (args.length < 1) {
            System.out.println("Usage: java Chats.CustomerChatApp <customerName>");
            return;
        }

        // Retrieve the customer's name from the command-line arguments
        String customerName = args[0];

        // Initialize the customer chat user interface
        CustomerChatUI customerChat = new CustomerChatUI(customerName);

        // Create a scanner to read user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chat with Support started for " + customerName + ". Type 'exit' to quit.");

        String message;
        while (true) {
            // Prompt the customer to enter a message
            System.out.print(customerName + ": ");
            message = scanner.nextLine();

            // Exit the chat loop if the customer types "exit"
            if (message.equalsIgnoreCase("exit")) {
                break;
            }

            // Send the message to the chat UI if it is not empty or whitespace
            if (!message.trim().isEmpty()) {
                customerChat.sendMessage(message);
            }
        }

        // Close the chat session and clean up resources
        customerChat.closeChat();
        scanner.close();
        System.out.println("Chat ended for " + customerName + ".");
    }
}