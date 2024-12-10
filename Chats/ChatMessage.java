package Chats;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a chat message exchanged between a sender and a recipient.
 * This class includes details such as the sender, message content, timestamp,
 * and whether the message is from support or a customer.
 */
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L; // Serialization identifier for compatibility
    private String sender; // The sender of the message
    private String message; // The content of the message
    private LocalDateTime timestamp; // The timestamp of when the message was created
    private boolean isSupport; // Flag to indicate if the message is from support

    /**
     * Constructs a new ChatMessage.
     *
     * @param sender    The sender of the message.
     * @param message   The content of the message.
     * @param isSupport A flag indicating if the message is sent by support.
     */
    public ChatMessage(String sender, String message, boolean isSupport) {
        this.sender = sender;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.isSupport = isSupport;
    }

    /**
     * Gets the sender of the message.
     *
     * @return The sender of the message.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the content of the message.
     *
     * @return The message content.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the timestamp of when the message was created.
     *
     * @return The timestamp of the message.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Checks if the message is sent by support.
     *
     * @return {@code true} if the message is from support, otherwise {@code false}.
     */
    public boolean isSupport() {
        return isSupport;
    }

    /**
     * Sets the sender of the message.
     *
     * @param sender The new sender of the message.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Sets the content of the message.
     *
     * @param message The new message content.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the timestamp of the message.
     *
     * @param timestamp The new timestamp of the message.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets whether the message is from support.
     *
     * @param support {@code true} if the message is from support, otherwise {@code false}.
     */
    public void setSupport(boolean support) {
        isSupport = support;
    }
}