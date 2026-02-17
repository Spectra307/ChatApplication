package com.chatapp.config;

import com.chatapp.auth.AuthService;
import com.chatapp.server.ChatServer;

/**
 * TestConfig - Configuration for unit tests
 * 
 * Provides test instances of services without database dependencies
 */
public class TestConfig {
    
    /**
     * Create a test instance of AuthService
     * @return AuthService configured for testing
     */
    public static AuthService createTestAuthService() {
        return new AuthService();
    }

    /**
     * Create a test instance of ChatServer
     * @return ChatServer configured for testing
     */
    public static ChatServer createTestChatServer() {
        return new ChatServer();
    }
}
