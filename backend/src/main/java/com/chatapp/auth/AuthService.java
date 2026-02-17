package com.chatapp.auth;

import com.chatapp.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthService - Handles user authentication and login/logout operations
 * 
 * This service manages:
 * - User credential verification
 * - Session management
 * - User authentication flow
 */
public class AuthService {
    private DatabaseConnection dbConnection;
    private Map<String, String> activeSessions;

    /**
     * Constructor - Initialize AuthService
     */
    public AuthService() {
        this.dbConnection = new DatabaseConnection();
        this.activeSessions = new HashMap<>();
    }

    /**
     * Authenticate user with username and password
     * 
     * @param username - User's username
     * @param password - User's password (should be hashed in production)
     * @return true if credentials are valid, false otherwise
     */
    public boolean authenticate(String username, String password) {
        try (Connection conn = dbConnection.getConnection()) {
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                // In production, compare hashed passwords
                if (storedPassword.equals(password)) {
                    activeSessions.put(username, System.currentTimeMillis() + "");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during authentication: " + e.getMessage());
        }
        return false;
    }

    /**
     * Register a new user
     * 
     * @param username - New username
     * @param password - User's password (should be hashed in production)
     * @return true if registration successful, false otherwise
     */
    public boolean registerUser(String username, String password) {
        try (Connection conn = dbConnection.getConnection()) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password); // Should be hashed in production

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Logout user and terminate session
     * 
     * @param username - Username to logout
     */
    public void logout(String username) {
        activeSessions.remove(username);
    }

    /**
     * Check if user is currently logged in
     * 
     * @param username - Username to check
     * @return true if user has active session, false otherwise
     */
    public boolean isLoggedIn(String username) {
        return activeSessions.containsKey(username);
    }
}
