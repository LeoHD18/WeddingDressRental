package Chats;

import java.util.Scanner;

/**
 * The SupportChatApp class serves as the entry point for the support team to interact with customers in a chat session.
 * It facilitates sending messages to customers and managing the chat lifecycle.
 */
public class SupportChatApp {

    /**
     * The main method initializes a chat session with a specified customer and enables support agents to send messages.
     *
     * @param args Command-line arguments where the first argument is the customer's name.
     *             Usage: java Chats.SupportChatApp <customerName>
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Chats.SupportChatApp <customerName>");
            return;
        }
        
        String customerName = args[0];
        SupportChatUI supportChat = new SupportChatUI(customerName);
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chat with " + customerName + " started. Type 'exit' to quit.");
        
        String message;
        while (true) {
            System.out.print("Support: ");
            message = scanner.nextLine();
            
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
            
            if (!message.trim().isEmpty()) {
                supportChat.sendMessage(message);
            }
        }
        
        supportChat.closeChat();
        scanner.close();
        System.out.println("Chat with " + customerName + " ended.");
    }
}