package com.chatapp.auth;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * UserTest - Unit tests for User data model
 * 
 * Tests cover:
 * - User creation
 * - Getter/setter functionality
 * - User state management
 */
public class UserTest {
    private User user;

    /**
     * Setup - Create a user instance before each test
     */
    @Before
    public void setUp() {
        user = new User("testuser", "password123", "test@example.com");
    }

    /**
     * Test user creation with valid parameters
     */
    @Test
    public void testUserCreation() {
        assertNotNull("User should be created", user);
        assertEquals("Username should be testuser", "testuser", user.getUsername());
        assertEquals("Password should be password123", "password123", user.getPassword());
        assertEquals("Email should be test@example.com", "test@example.com", user.getEmail());
    }

    /**
     * Test user is active by default
     */
    @Test
    public void testUserActiveByDefault() {
        assertTrue("User should be active by default", user.isActive());
    }

    /**
     * Test setting user active status
     */
    @Test
    public void testSetUserActive() {
        user.setActive(false);
        assertFalse("User should be inactive", user.isActive());
        
        user.setActive(true);
        assertTrue("User should be active again", user.isActive());
    }

    /**
     * Test setting user ID
     */
    @Test
    public void testSetUserId() {
        user.setUserId(123);
        assertEquals("User ID should be 123", 123, user.getUserId());
    }

    /**
     * Test updating password
     */
    @Test
    public void testSetPassword() {
        user.setPassword("newpassword");
        assertEquals("Password should be updated", "newpassword", user.getPassword());
    }

    /**
     * Test updating email
     */
    @Test
    public void testSetEmail() {
        user.setEmail("newemail@example.com");
        assertEquals("Email should be updated", "newemail@example.com", user.getEmail());
    }

    /**
     * Test user toString method
     */
    @Test
    public void testUserToString() {
        String result = user.toString();
        assertNotNull("toString should not be null", result);
        assertTrue("toString should contain username", result.contains("testuser"));
        assertTrue("toString should contain email", result.contains("test@example.com"));
    }

    /**
     * Test user with null email
     */
    @Test
    public void testUserWithNullEmail() {
        User userNoEmail = new User("testuser", "password123", null);
        assertNull("Email should be null", userNoEmail.getEmail());
    }

    /**
     * Test multiple users are independent
     */
    @Test
    public void testMultipleUsersIndependent() {
        User user2 = new User("anotheruser", "password456", "another@example.com");
        
        assertEquals("User1 username should be testuser", "testuser", user.getUsername());
        assertEquals("User2 username should be anotheruser", "anotheruser", user2.getUsername());
        
        user.setPassword("newpass");
        assertEquals("User2 password should still be password456", "password456", user2.getPassword());
    }
}
