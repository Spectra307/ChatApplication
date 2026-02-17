package com.chatapp.server;

import java.sql.Timestamp;

/**
 * Message - Data model for chat messages
 * 
 * Represents a message with sender, recipient, content, and timestamp
 */
public class Message {
    private long messageId;
    private String sender;
    private String recipient;
    private String content;
    private Timestamp sentAt;
    private boolean isRead;

    /**
     * Constructor - Create a new message
     * 
     * @param sender - Username of sender
     * @param recipient - Username of recipient
     * @param content - Message content
     * @param sentAt - Timestamp when message was sent
     */
    public Message(String sender, String recipient, String content, Timestamp sentAt) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.sentAt = sentAt;
        this.isRead = false;
    }

    // Getters
    public long getMessageId() {
        return messageId;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    // Setters
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    /**
     * String representation of message
     */
    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", content='" + content + '\'' +
                ", sentAt=" + sentAt +
                ", isRead=" + isRead +
                '}';
    }
}
