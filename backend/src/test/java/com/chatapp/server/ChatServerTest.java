package com.chatapp.server;

import com.chatapp.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * ChatServerTest - Unit tests for ChatServer
 * 
 * Tests cover:
 * - Message sending
 * - Message storage
 * - Event listeners
 * - Conversation history
 */
public class ChatServerTest {
    private ChatServer chatServer;
    private TestChatListener testListener;

    /**
     * Test chat listener implementation for testing
     */
    private static class TestChatListener implements ChatServer.ChatListener {
        public String lastSender;
        public String lastMessage;
        public int messageCount = 0;

        @Override
        public void onMessageReceived(String sender, String message) {
            this.lastSender = sender;
            this.lastMessage = message;
            this.messageCount++;
        }
    }

    /**
     * Setup - Initialize ChatServer before each test
     */
    @Before
    public void setUp() {
        chatServer = TestConfig.createTestChatServer();
        testListener = new TestChatListener();
        chatServer.addListener(testListener);
    }

    /**
     * Test sending a message successfully
     */
    @Test
    public void testSendMessageSuccess() {
        boolean result = chatServer.sendMessage("alice", "bob", "Hello!");
        assertTrue("Message should be sent successfully", result);
    }

    /**
     * Test listener receives message when sent
     */
    @Test
    public void testListenerNotifiedOnMessageSent() {
        chatServer.sendMessage("alice", "bob", "Test message");
        
        assertEquals("Listener should receive sender", "alice", testListener.lastSender);
        assertEquals("Listener should receive message", "Test message", testListener.lastMessage);
        assertEquals("Message count should be 1", 1, testListener.messageCount);
    }

    /**
     * Test multiple messages trigger listener multiple times
     */
    @Test
    public void testMultipleMessagesNotifyListener() {
        chatServer.sendMessage("alice", "bob", "Message 1");
        chatServer.sendMessage("bob", "alice", "Message 2");
        chatServer.sendMessage("alice", "bob", "Message 3");
        
        assertEquals("Message count should be 3", 3, testListener.messageCount);
        assertEquals("Last message should be Message 3", "Message 3", testListener.lastMessage);
    }

    /**
     * Test store message functionality
     */
    @Test
    public void testStoreMessage() {
        long result = chatServer.storeMessage("alice", "bob", "Stored message");
        assertTrue("Store message should return positive ID on success", result > 0);
    }

    /**
     * Test sending message with empty content
     */
    @Test
    public void testSendMessageEmptyContent() {
        boolean result = chatServer.sendMessage("alice", "bob", "");
        // Implementation may vary; currently allows empty messages
        assertTrue("Empty message should still be sent", result);
    }

    /**
     * Test sending message with very long content
     */
    @Test
    public void testSendMessageLongContent() {
        String longMessage = "a".repeat(1000);
        boolean result = chatServer.sendMessage("alice", "bob", longMessage);
        assertTrue("Long message should be sent successfully", result);
    }

    /**
     * Test conversation history retrieval (empty initially)
     */
    @Test
    public void testGetConversationHistoryEmpty() {
        java.util.List<Message> history = chatServer.getConversationHistory("alice", "bob");
        assertNotNull("Conversation history should not be null", history);
        // Initially empty (unless database is populated)
    }

    /**
     * Test adding multiple listeners
     */
    @Test
    public void testMultipleListeners() {
        TestChatListener listener2 = new TestChatListener();
        chatServer.addListener(listener2);
        
        chatServer.sendMessage("alice", "bob", "Multi-listener test");
        
        assertEquals("Listener 1 should receive message", "Multi-listener test", testListener.lastMessage);
        assertEquals("Listener 2 should receive message", "Multi-listener test", listener2.lastMessage);
    }

    /**
     * Test message with special characters
     */
    @Test
    public void testSendMessageSpecialCharacters() {
        boolean result = chatServer.sendMessage("alice", "bob", "Hello! @#$%^&*()");
        assertTrue("Message with special characters should be sent", result);
        assertEquals("Special characters should be preserved", "Hello! @#$%^&*()", testListener.lastMessage);
    }
}
