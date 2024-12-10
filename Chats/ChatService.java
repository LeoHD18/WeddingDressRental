package Chats;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling chat functionality, including initializing chats,
 * sending messages, and retrieving new messages for a given customer.
 */
public class ChatService {
    // Directory for storing chat files (currently set to project root)
    private static final String CHAT_DIRECTORY = "";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Formatter for timestamps

    /**
     * Constructs a ChatService instance. No directory initialization is needed 
     * as the chat files are stored in the project root directory.
     */
    public ChatService() {
        // No initialization needed for project root
    }

    /**
     * Initializes a chat file for the specified customer. If the file does not 
     * already exist, it creates a new file in the specified directory.
     *
     * @param customerId The unique identifier for the customer.
     */
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

    /**
     * Sends a message to the chat file associated with the specified customer.
     * The message is appended to the file in a specific format.
     *
     * @param customerId The unique identifier for the customer.
     * @param message    The chat message to be sent.
     */
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

    /**
     * Retrieves new messages for a specific customer since a given timestamp.
     * Only messages with timestamps later than the provided time are returned.
     *
     * @param customerId The unique identifier for the customer.
     * @param since      The timestamp to filter messages by.
     * @return A list of new messages since the specified timestamp.
     */
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

    /**
     * Parses a single line from the chat file into a ChatMessage object.
     * The line must follow the format: sender|message|timestamp|isSupport.
     *
     * @param line The line from the chat file to parse.
     * @return A ChatMessage object created from the line.
     */
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