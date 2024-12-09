package Chats;

import java.util.Scanner;

public class CustomerChatApp {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Chats.CustomerChatApp <customerName>");
            return;
        }
        
        String customerName = args[0];
        CustomerChatUI customerChat = new CustomerChatUI(customerName);
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chat with Support started for " + customerName + ". Type 'exit' to quit.");
        
        String message;
        while (true) {
            System.out.print(customerName + ": ");
            message = scanner.nextLine();
            
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
            
            if (!message.trim().isEmpty()) {
                customerChat.sendMessage(message);
            }
        }
        
        customerChat.closeChat();
        scanner.close();
        System.out.println("Chat ended for " + customerName + ".");
    }
} 