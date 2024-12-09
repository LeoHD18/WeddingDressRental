package Chats;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatService {
    // Use an empty string to indicate the project root directory
    private static final String CHAT_DIRECTORY = ""; 
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    public ChatService() {
        // No need to create a directory since we're using the project root
    }
    
    public void initializeChat(String customerId) {
        String chatFileName = CHAT_DIRECTORY + customerId + ".txt"; // Use .txt extension
        try {
            File chatFile = new File(chatFileName);
            if (!chatFile.exists()) {
                if (chatFile.createNewFile()) {
                    System.out.println("Chat file created: " + chatFileName);
                } else {
                    System.err.println("Failed to create chat file: " + chatFileName);
                }
            }
        } catch (IOException e) {
            System.err.println("Error initializing chat for customer " + customerId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String customerId, ChatMessage message) {
        String chatFileName = CHAT_DIRECTORY + customerId + ".txt"; // Use .txt extension
        try (FileWriter fw = new FileWriter(chatFileName, true)) {
            fw.write(message.getSender() + "|" + 
                    message.getMessage() + "|" + 
                    message.getTimestamp().format(formatter) + "|" + 
                    message.isSupport() + "\n");
            System.out.println("Message written to file: " + chatFileName);
        } catch (IOException e) {
            System.err.println("Error sending message for customer " + customerId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public List<ChatMessage> getNewMessages(String customerId, LocalDateTime since) {
        List<ChatMessage> newMessages = new ArrayList<>();
        String chatFileName = CHAT_DIRECTORY + customerId + ".txt"; // Use .txt extension
        
        try {
            List<String> lines = Files.readAllLines(Path.of(chatFileName));
            for (String line : lines) {
                ChatMessage message = parseMessage(line);
                if (message.getTimestamp().isAfter(since)) {
                    newMessages.add(message);
                }
            }
        } catch (IOException e) {
            System.err.println("Error retrieving messages for customer " + customerId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return newMessages;
    }
    
    private ChatMessage parseMessage(String line) {
        String[] parts = line.split("\\|");
        ChatMessage message = new ChatMessage(
            parts[0], // sender
            parts[1], // message
            Boolean.parseBoolean(parts[3]) // isSupport
        );
        message.setTimestamp(LocalDateTime.parse(parts[2], formatter));
        return message;
    }
}