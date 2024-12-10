package Chats;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The SupportChatUI class provides a user interface for support agents to interact with customers.
 * It handles sending messages, receiving messages from customers, and managing the chat session lifecycle.
 */
public class SupportChatUI {
    private ChatService chatService;
    private String customerId;
    private LocalDateTime lastCheck;
    private Timer pollingTimer;

    /**
     * Constructs a SupportChatUI instance for a specific customer.
     *
     * @param customerId The ID of the customer to chat with.
     */
    public SupportChatUI(String customerId) {
        this.customerId = customerId;
        this.chatService = new ChatService();
        this.lastCheck = LocalDateTime.now();
        startPolling();
    }

    /**
     * Starts polling for new messages from the customer at regular intervals.
     */
    private void startPolling() {
        pollingTimer = new Timer();
        pollingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkNewMessages();
            }
        }, 0, 2000); // Check every 2 seconds
    }

    /**
     * Checks for new messages from the customer since the last poll.
     * If new messages are found, they are displayed, and the last check timestamp is updated.
     */
    private void checkNewMessages() {
        List<ChatMessage> newMessages = chatService.getNewMessages(customerId, lastCheck);
        if (!newMessages.isEmpty()) {
            displayMessages(newMessages);
            lastCheck = LocalDateTime.now();
        }
    }

    /**
     * Sends a message from the support agent to the customer.
     *
     * @param message The message to send.
     */
    public void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage("Support", message, true);
        chatService.sendMessage(customerId, chatMessage);
    }

    /**
     * Displays messages from the customer.
     * Filters out messages sent by support to avoid duplication.
     *
     * @param messages A list of messages to display.
     */
    private void displayMessages(List<ChatMessage> messages) {
        for (ChatMessage message : messages) {
            if (!message.isSupport()) { // Only display customer messages
                String formattedMessage = "Customer: " + message.getMessage();
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