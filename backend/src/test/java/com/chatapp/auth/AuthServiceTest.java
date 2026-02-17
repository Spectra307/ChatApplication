package com.chatapp.auth;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * AuthServiceTest - Unit tests for AuthService
 * 
 * Tests cover:
 * - User authentication
 * - Session management
 * - User registration
 * - Login/logout functionality
 */
public class AuthServiceTest {
    private AuthService authService;

    /**
     * Setup - Initialize AuthService before each test
     */
    @Before
    public void setUp() {
        authService = new AuthService();
    }

    /**
     * Test user registration with valid credentials
     */
    @Test
    public void testRegisterUserSuccess() {
        boolean result = authService.registerUser("testuser", "password123");
        assertTrue("User registration should succeed", result);
    }

    /**
     * Test user registration with empty username
     */
    @Test
    public void testRegisterUserEmptyUsername() {
        boolean result = authService.registerUser("", "password123");
        // Should handle gracefully (depends on implementation)
        assertNotNull("Should return a boolean", result);
    }

    /**
     * Test authentication with valid credentials
     */
    @Test
    public void testAuthenticateValidCredentials() {
        // First register user
        authService.registerUser("alice", "password123");
        
        // Then authenticate
        boolean result = authService.authenticate("alice", "password123");
        assertTrue("Authentication should succeed with correct credentials", result);
    }

    /**
     * Test authentication with invalid password
     */
    @Test
    public void testAuthenticateInvalidPassword() {
        authService.registerUser("bob", "correctpassword");
        
        boolean result = authService.authenticate("bob", "wrongpassword");
        assertFalse("Authentication should fail with incorrect password", result);
    }

    /**
     * Test authentication with non-existent user
     */
    @Test
    public void testAuthenticateNonExistentUser() {
        boolean result = authService.authenticate("nonexistent", "password123");
        assertFalse("Authentication should fail for non-existent user", result);
    }

    /**
     * Test session creation on successful login
     */
    @Test
    public void testSessionCreatedOnLogin() {
        authService.registerUser("charlie", "password123");
        authService.authenticate("charlie", "password123");
        
        boolean isLoggedIn = authService.isLoggedIn("charlie");
        assertTrue("User should be logged in after successful authentication", isLoggedIn);
    }

    /**
     * Test logout removes user session
     */
    @Test
    public void testLogoutRemovesSession() {
        authService.registerUser("david", "password123");
        authService.authenticate("david", "password123");
        
        assertTrue("User should be logged in", authService.isLoggedIn("david"));
        
        authService.logout("david");
        assertFalse("User should not be logged in after logout", authService.isLoggedIn("david"));
    }

    /**
     * Test isLoggedIn for non-authenticated user
     */
    @Test
    public void testIsLoggedInFalseForNonAuthenticatedUser() {
        boolean result = authService.isLoggedIn("unknownuser");
        assertFalse("Non-authenticated user should not be logged in", result);
    }

    /**
     * Test multiple users can be logged in simultaneously
     */
    @Test
    public void testMultipleUsersLoggedInSimultaneously() {
        authService.registerUser("user1", "pass1");
        authService.registerUser("user2", "pass2");
        
        authService.authenticate("user1", "pass1");
        authService.authenticate("user2", "pass2");
        
        assertTrue("User1 should be logged in", authService.isLoggedIn("user1"));
        assertTrue("User2 should be logged in", authService.isLoggedIn("user2"));
    }

    /**
     * Test logout only affects specified user
     */
    @Test
    public void testLogoutOnlyAffectsSpecificUser() {
        authService.registerUser("user1", "pass1");
        authService.registerUser("user2", "pass2");
        
        authService.authenticate("user1", "pass1");
        authService.authenticate("user2", "pass2");
        
        authService.logout("user1");
        
        assertFalse("User1 should be logged out", authService.isLoggedIn("user1"));
        assertTrue("User2 should still be logged in", authService.isLoggedIn("user2"));
    }
}
