package com.chatapp.server;

import com.chatapp.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * ChatServer - Handles message sending, receiving, and delivery
 * 
 * This service manages:
 * - Message storage in database
 * - Message delivery to recipients
 * - Message history retrieval
 */
public class ChatServer {
    private DatabaseConnection dbConnection;
    private List<ChatListener> listeners;

    /**
     * Constructor - Initialize ChatServer
     */
    public ChatServer() {
        this.dbConnection = new DatabaseConnection();
        this.listeners = new ArrayList<>();
    }

    /**
     * Send a message from one user to another
     * 
     * @param senderUsername - Username of sender
     * @param recipientUsername - Username of recipient
     * @param messageContent - Content of the message
     * @return true if message sent successfully, false otherwise
     */
    public boolean sendMessage(String senderUsername, String recipientUsername, String messageContent) {
        try (Connection conn = dbConnection.getConnection()) {
            String query = "INSERT INTO messages (sender, recipient, content, sent_at) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, senderUsername);
            stmt.setString(2, recipientUsername);
            stmt.setString(3, messageContent);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();

            // Notify listeners about new message
            notifyListeners(senderUsername, messageContent);
            return true;
        } catch (SQLException e) {
            System.err.println("Error sending message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Store message in database
     * 
     * @param sender - Sender username
     * @param recipient - Recipient username
     * @param messageContent - Content of message
     * @return Message ID if successful, -1 otherwise
     */
    public long storeMessage(String sender, String recipient, String messageContent) {
        return sendMessage(sender, recipient, messageContent) ? 1 : -1;
    }

    /**
     * Get conversation history between two users
     * 
     * @param user1 - First user
     * @param user2 - Second user
     * @return List of messages between the two users
     */
    public List<Message> getConversationHistory(String user1, String user2) {
        List<Message> messages = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection()) {
            String query = "SELECT * FROM messages WHERE (sender = ? AND recipient = ?) OR (sender = ? AND recipient = ?) ORDER BY sent_at ASC";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user1);
            stmt.setString(2, user2);
            stmt.setString(3, user2);
            stmt.setString(4, user1);

            // Execute query and populate messages list
            // Note: ResultSet processing would be implemented here
        } catch (SQLException e) {
            System.err.println("Error retrieving conversation history: " + e.getMessage());
        }
        return messages;
    }

    /**
     * Add listener for message events
     * 
     * @param listener - ChatListener implementation
     */
    public void addListener(ChatListener listener) {
        listeners.add(listener);
    }

    /**
     * Notify all listeners about new message
     * 
     * @param sender - Sender username
     * @param message - Message content
     */
    private void notifyListeners(String sender, String message) {
        for (ChatListener listener : listeners) {
            listener.onMessageReceived(sender, message);
        }
    }

    /**
     * Interface for chat event listeners
     */
    public interface ChatListener {
        void onMessageReceived(String sender, String message);
    }
}
