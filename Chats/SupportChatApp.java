package Chats;

import java.util.Scanner;

public class SupportChatApp {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java chat.SupportChatApp <customerName>");
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