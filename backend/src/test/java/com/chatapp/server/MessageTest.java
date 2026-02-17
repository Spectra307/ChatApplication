package com.chatapp.server;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * MessageTest - Unit tests for Message data model
 * 
 * Tests cover:
 * - Message creation
 * - Getter/setter functionality
 * - Message state management
 */
public class MessageTest {
    private Message message;

    /**
     * Setup - Create a message instance before each test
     */
    @Before
    public void setUp() {
        java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
        message = new Message("alice", "bob", "Hello Bob!", timestamp);
    }

    /**
     * Test message creation with valid parameters
     */
    @Test
    public void testMessageCreation() {
        assertNotNull("Message should be created", message);
        assertEquals("Sender should be alice", "alice", message.getSender());
        assertEquals("Recipient should be bob", "bob", message.getRecipient());
        assertEquals("Content should match", "Hello Bob!", message.getContent());
    }

    /**
     * Test message read status defaults to false
     */
    @Test
    public void testMessageDefaultUnread() {
        assertFalse("Message should be unread by default", message.isRead());
    }

    /**
     * Test setting message as read
     */
    @Test
    public void testSetMessageAsRead() {
        message.setRead(true);
        assertTrue("Message should be marked as read", message.isRead());
    }

    /**
     * Test message ID setter and getter
     */
    @Test
    public void testMessageIdSetterGetter() {
        message.setMessageId(42);
        assertEquals("Message ID should be 42", 42, message.getMessageId());
    }

    /**
     * Test message toString method
     */
    @Test
    public void testMessageToString() {
        String result = message.toString();
        assertNotNull("toString should not be null", result);
        assertTrue("toString should contain sender", result.contains("alice"));
        assertTrue("toString should contain recipient", result.contains("bob"));
        assertTrue("toString should contain content", result.contains("Hello Bob!"));
    }

    /**
     * Test message with empty content
     */
    @Test
    public void testMessageWithEmptyContent() {
        java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
        Message emptyMessage = new Message("alice", "bob", "", timestamp);
        assertEquals("Content should be empty", "", emptyMessage.getContent());
    }

    /**
     * Test message with special characters
     */
    @Test
    public void testMessageWithSpecialCharacters() {
        java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
        Message specialMessage = new Message("alice", "bob", "Hello! @#$%^&*()", timestamp);
        assertEquals("Content should preserve special characters", "Hello! @#$%^&*()", specialMessage.getContent());
    }
}
