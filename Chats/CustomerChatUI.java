package Chats;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The CustomerChatUI class provides a user interface for customers to interact with a chat service.
 * It handles sending messages, receiving support messages, and periodically polling for new messages.
 */
public class CustomerChatUI {
    private ChatService chatService;
    private String customerId;
    private LocalDateTime lastCheck;
    private Timer pollingTimer;

    /**
     * Constructs a CustomerChatUI instance for a specific customer.
     * Initializes the chat session and starts polling for new messages.
     *
     * @param customerId The unique identifier for the customer.
     */
    public CustomerChatUI(String customerId) {
        this.customerId = customerId;
        this.chatService = new ChatService();
        this.lastCheck = LocalDateTime.now();
        chatService.initializeChat(customerId);
        startPolling();
    }

    /**
     * Starts a timer that periodically checks for new messages from the chat service.
     */
    private void startPolling() {
        pollingTimer = new Timer();
        pollingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkNewMessages();
            }
        }, 0, 500); // Check every 0.5 seconds for a more real-time feel
    }

    /**
     * Checks for new messages from the chat service since the last polling time.
     * Updates the last check time and displays any new support messages.
     */
    private void checkNewMessages() {
        List<ChatMessage> newMessages = chatService.getNewMessages(customerId, lastCheck);
        if (!newMessages.isEmpty()) {
            displayMessages(newMessages);
            lastCheck = LocalDateTime.now();
        }
    }

    /**
     * Sends a message to the chat service on behalf of the customer.
     *
     * @param message The content of the message to send.
     */
    public void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(customerId, message, false);
        chatService.sendMessage(customerId, chatMessage);
    }

    /**
     * Displays a list of messages received from the support team.
     *
     * @param messages The list of new chat messages to display.
     */
    private void displayMessages(List<ChatMessage> messages) {
        for (ChatMessage message : messages) {
            if (message.isSupport()) { // Only display support messages
                String formattedMessage = "Support: " + message.getMessage();
                System.out.println(formattedMessage);
            }
        }
    }

    /**
     * Closes the chat session by stopping the polling timer.
     */
    public void closeChat() {
        if (pollingTimer != null) {
            pollingTimer.cancel();
            pollingTimer = null;
        }
    }
}