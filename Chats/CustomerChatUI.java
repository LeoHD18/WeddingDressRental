package Chats;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CustomerChatUI {
    private ChatService chatService;
    private String customerId;
    private LocalDateTime lastCheck;
    private Timer pollingTimer;
    
    public CustomerChatUI(String customerId) {
        this.customerId = customerId;
        this.chatService = new ChatService();
        this.lastCheck = LocalDateTime.now();
        chatService.initializeChat(customerId);
        startPolling();
    }
    
    private void startPolling() {
        pollingTimer = new Timer();
        pollingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkNewMessages();
            }
        }, 0, 500); // Check every 0.5 seconds for a more real-time feel
    }
    
    private void checkNewMessages() {
        List<ChatMessage> newMessages = chatService.getNewMessages(customerId, lastCheck);
        if (!newMessages.isEmpty()) {
            displayMessages(newMessages);
            lastCheck = LocalDateTime.now();
        }
    }
    
    public void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(customerId, message, false);
        chatService.sendMessage(customerId, chatMessage);
    }
    
    private void displayMessages(List<ChatMessage> messages) {
        for (ChatMessage message : messages) {
            if (message.isSupport()) { // Only display support messages
                String formattedMessage = "Support: " + message.getMessage();
                System.out.println(formattedMessage);
            }
        }
    }
    
    public void closeChat() {
        if (pollingTimer != null) {
            pollingTimer.cancel();
            pollingTimer = null;
        }
    }
} 