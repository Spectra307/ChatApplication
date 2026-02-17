package com.chatapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.chatapp.auth.AuthServiceTest;
import com.chatapp.auth.UserTest;
import com.chatapp.server.ChatServerTest;
import com.chatapp.server.MessageTest;
import com.chatapp.api.ChatControllerTest;

/**
 * AllTests - Test suite runner for all unit tests
 * 
 * This suite runs all unit tests for the Chat Application backend:
 * - AuthServiceTest
 * - UserTest
 * - ChatServerTest
 * - MessageTest
 * - ChatControllerTest
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AuthServiceTest.class,
    UserTest.class,
    ChatServerTest.class,
    MessageTest.class,
    ChatControllerTest.class
})
public class AllTests {
}
