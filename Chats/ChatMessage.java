package Chats;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender;
    private String message;
    private LocalDateTime timestamp;
    private boolean isSupport;
    
    public ChatMessage(String sender, String message, boolean isSupport) {
        this.sender = sender;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.isSupport = isSupport;
    }
    
    public String getSender() {
        return sender;
    }
    
    public String getMessage() {
        return message;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public boolean isSupport() {
        return isSupport;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public void setSupport(boolean support) {
        isSupport = support;
    }
} 