package com.chatapp;

import com.chatapp.auth.AuthService;
import com.chatapp.server.ChatServer;
import com.chatapp.database.DatabaseConnection;

/**
 * ChatApplicationMain - Main entry point for Chat Application
 * 
 * This class initializes:
 * - Database connection
 * - Authentication service
 * - Chat server
 * - REST API endpoints
 */
public class ChatApplicationMain {
    private static AuthService authService;
    private static ChatServer chatServer;
    private static DatabaseConnection dbConnection;

    /**
     * Initialize application services
     */
    public static void initializeServices() {
        dbConnection = new DatabaseConnection();
        authService = new AuthService();
        chatServer = new ChatServer();

        // Test database connection
        if (dbConnection.testConnection()) {
            System.out.println("✓ Database connection successful");
        } else {
            System.err.println("✗ Database connection failed");
            System.exit(1);
        }

        System.out.println("✓ AuthService initialized");
        System.out.println("✓ ChatServer initialized");
    }

    /**
     * Main method - Application entry point
     * 
     * @param args - Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Chat Application Starting...");
        System.out.println("========================================");

        initializeServices();

        System.out.println("\n✓ Chat Application ready!");
        System.out.println("  Backend Server: http://localhost:8080");
        System.out.println("========================================\n");

        // Keep application running
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down Chat Application...");
        }));
    }

    // Getters for services
    public static AuthService getAuthService() {
        return authService;
    }

    public static ChatServer getChatServer() {
        return chatServer;
    }

    public static DatabaseConnection getDbConnection() {
        return dbConnection;
    }
}
