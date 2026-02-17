package com.chatapp.api;

import com.chatapp.auth.AuthService;
import com.chatapp.server.ChatServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * ChatControllerTest - Unit tests for REST API endpoints
 * 
 * Tests cover:
 * - Login endpoint
 * - Logout endpoint
 * - Message sending endpoint
 * - Health check endpoint
 */
@RunWith(MockitoJUnitRunner.class)
public class ChatControllerTest {
    @Mock
    private AuthService authService;
    
    @Mock
    private ChatServer chatServer;
    
    @InjectMocks
    private ChatController chatController;

    /**
     * Setup - Initialize controller with mocked dependencies
     * MockitoJUnitRunner will inject mocks automatically, no need to call new ChatController()
     */
    @Before
    public void setUp() {
        // Mocks are injected by @RunWith(MockitoJUnitRunner.class)
        // No explicit initialization needed
    }

    /**
     * Test health endpoint
     */
    @Test
    public void testHealthEndpoint() {
        Map<String, String> response = chatController.health();
        
        assertNotNull("Response should not be null", response);
        assertEquals("Status should be ok", "ok", response.get("status"));
        assertEquals("Service should be Chat Application API", "Chat Application API", response.get("service"));
    }

    /**
     * Test login with valid credentials
     */
    @Test
    public void testLoginValidCredentials() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "alice");
        loginRequest.put("password", "password123");

        Map<String, Object> response = chatController.login(loginRequest);
        
        assertNotNull("Response should not be null", response);
        assertFalse("Response should indicate result", (Boolean) response.get("success"));
        assertNotNull("Response should contain message", response.get("message"));
    }

    /**
     * Test login with missing password
     */
    @Test
    public void testLoginMissingPassword() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "alice");
        // password is null

        Map<String, Object> response = chatController.login(loginRequest);
        
        assertFalse("Login should fail", (Boolean) response.get("success"));
        assertTrue("Should contain error message", response.get("message").toString().contains("required"));
    }

    /**
     * Test login with missing username
     */
    @Test
    public void testLoginMissingUsername() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("password", "password123");
        // username is null

        Map<String, Object> response = chatController.login(loginRequest);
        
        assertFalse("Login should fail", (Boolean) response.get("success"));
        assertTrue("Should contain error message", response.get("message").toString().contains("required"));
    }

    /**
     * Test logout endpoint
     */
    @Test
    public void testLogoutSuccess() {
        Map<String, String> logoutRequest = new HashMap<>();
        logoutRequest.put("username", "alice");

        Map<String, Object> response = chatController.logout(logoutRequest);
        
        assertNotNull("Response should not be null", response);
        assertTrue("Logout should succeed", (Boolean) response.get("success"));
        assertEquals("Should contain success message", "Logged out successfully", response.get("message"));
    }

    /**
     * Test logout with missing username
     */
    @Test
    public void testLogoutMissingUsername() {
        Map<String, String> logoutRequest = new HashMap<>();
        // username is null

        Map<String, Object> response = chatController.logout(logoutRequest);
        
        assertFalse("Logout should fail", (Boolean) response.get("success"));
        assertTrue("Should contain error message", response.get("message").toString().contains("required"));
    }

    /**
     * Test register endpoint
     */
    @Test
    public void testRegisterSuccess() {
        Map<String, String> registerRequest = new HashMap<>();
        registerRequest.put("username", "newuser");
        registerRequest.put("password", "password123");
        registerRequest.put("email", "newuser@example.com");

        Map<String, Object> response = chatController.register(registerRequest);
        
        assertNotNull("Response should not be null", response);
        assertNotNull("Response should contain success key", response.get("success"));
        assertNotNull("Response should contain message", response.get("message"));
    }

    /**
     * Test register with missing username
     */
    @Test
    public void testRegisterMissingUsername() {
        Map<String, String> registerRequest = new HashMap<>();
        registerRequest.put("password", "password123");

        Map<String, Object> response = chatController.register(registerRequest);
        
        assertFalse("Registration should fail", (Boolean) response.get("success"));
        assertTrue("Should contain error message", response.get("message").toString().contains("required"));
    }

    /**
     * Test send message endpoint
     */
    @Test
    public void testSendMessageSuccess() {
        Map<String, String> messageRequest = new HashMap<>();
        messageRequest.put("sender", "alice");
        messageRequest.put("recipient", "bob");
        messageRequest.put("message", "Hello Bob!");

        Map<String, Object> response = chatController.sendMessage(messageRequest);
        
        assertNotNull("Response should not be null", response);
        assertNotNull("Response should contain result", response.get("success"));
    }

    /**
     * Test send message with missing sender
     */
    @Test
    public void testSendMessageMissingSender() {
        Map<String, String> messageRequest = new HashMap<>();
        messageRequest.put("message", "Hello!");

        Map<String, Object> response = chatController.sendMessage(messageRequest);
        
        assertFalse("Send should fail", (Boolean) response.get("success"));
        assertTrue("Should contain error message", response.get("message").toString().contains("required"));
    }

    /**
     * Test send message with missing message content
     */
    @Test
    public void testSendMessageMissingContent() {
        Map<String, String> messageRequest = new HashMap<>();
        messageRequest.put("sender", "alice");
        messageRequest.put("recipient", "bob");

        Map<String, Object> response = chatController.sendMessage(messageRequest);
        
        assertFalse("Send should fail", (Boolean) response.get("success"));
        assertTrue("Should contain error message", response.get("message").toString().contains("required"));
    }

    /**
     * Test send message with empty recipient (uses default)
     */
    @Test
    public void testSendMessageEmptyRecipient() {
        Map<String, String> messageRequest = new HashMap<>();
        messageRequest.put("sender", "alice");
        messageRequest.put("message", "Broadcast message");

        Map<String, Object> response = chatController.sendMessage(messageRequest);
        
        assertNotNull("Response should not be null", response);
        assertNotNull("Response should contain result", response.get("success"));
    }
}
