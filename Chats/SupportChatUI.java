package Chats;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SupportChatUI {
    private ChatService chatService;
    private String customerId;
    private LocalDateTime lastCheck;
    private Timer pollingTimer;
    
    public SupportChatUI(String customerId) {
        this.customerId = customerId;
        this.chatService = new ChatService();
        this.lastCheck = LocalDateTime.now();
        startPolling();
    }
    
    private void startPolling() {
        pollingTimer = new Timer();
        pollingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkNewMessages();
            }
        }, 0, 2000); // Check every 2 seconds
    }
    
    private void checkNewMessages() {
        List<ChatMessage> newMessages = chatService.getNewMessages(customerId, lastCheck);
        if (!newMessages.isEmpty()) {
            displayMessages(newMessages);
            lastCheck = LocalDateTime.now();
        }
    }
    
    public void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage("Support", message, true);
        chatService.sendMessage(customerId, chatMessage);
    }
    
    private void displayMessages(List<ChatMessage> messages) {
        for (ChatMessage message : messages) {
            if (!message.isSupport()) { // Only display customer messages
                String formattedMessage = "Customer: " + message.getMessage();
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