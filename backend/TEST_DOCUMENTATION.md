# Backend Unit Tests Documentation

## Overview

Comprehensive unit test suite for Chat Application backend components using JUnit 4 and Mockito.

## Test Files

### 1. AuthServiceTest (`src/test/java/com/chatapp/auth/AuthServiceTest.java`)
Tests for user authentication and session management.

**Test Cases:**
- `testRegisterUserSuccess()` - Valid user registration
- `testRegisterUserEmptyUsername()` - Registration with empty username
- `testAuthenticateValidCredentials()` - Login with correct credentials
- `testAuthenticateInvalidPassword()` - Login with wrong password
- `testAuthenticateNonExistentUser()` - Login with non-existent user
- `testSessionCreatedOnLogin()` - Session created after authentication
- `testLogoutRemovesSession()` - Session removed on logout
- `testIsLoggedInFalseForNonAuthenticatedUser()` - Non-authenticated user status
- `testMultipleUsersLoggedInSimultaneously()` - Multiple concurrent sessions
- `testLogoutOnlyAffectsSpecificUser()` - Logout isolation

### 2. UserTest (`src/test/java/com/chatapp/auth/UserTest.java`)
Tests for User data model.

**Test Cases:**
- `testUserCreation()` - Valid user object creation
- `testUserActiveByDefault()` - User active status default
- `testSetUserActive()` - Changing user active status
- `testSetUserId()` - Setting user ID
- `testSetPassword()` - Updating password
- `testSetEmail()` - Updating email
- `testUserToString()` - String representation
- `testUserWithNullEmail()` - Null email handling
- `testMultipleUsersIndependent()` - User object independence

### 3. ChatServerTest (`src/test/java/com/chatapp/server/ChatServerTest.java`)
Tests for message server functionality.

**Test Cases:**
- `testSendMessageSuccess()` - Successful message sending
- `testListenerNotifiedOnMessageSent()` - Event listener notification
- `testMultipleMessagesNotifyListener()` - Multiple message notifications
- `testStoreMessage()` - Message storage
- `testSendMessageEmptyContent()` - Empty message handling
- `testSendMessageLongContent()` - Long message handling
- `testGetConversationHistoryEmpty()` - Conversation history retrieval
- `testMultipleListeners()` - Multiple event listeners
- `testSendMessageSpecialCharacters()` - Special character handling

### 4. MessageTest (`src/test/java/com/chatapp/server/MessageTest.java`)
Tests for Message data model.

**Test Cases:**
- `testMessageCreation()` - Valid message object creation
- `testMessageDefaultUnread()` - Message unread status default
- `testSetMessageAsRead()` - Marking message as read
- `testMessageIdSetterGetter()` - Message ID getter/setter
- `testMessageToString()` - String representation
- `testMessageWithEmptyContent()` - Empty message content
- `testMessageWithSpecialCharacters()` - Special characters in content

### 5. ChatControllerTest (`src/test/java/com/chatapp/api/ChatControllerTest.java`)
Tests for REST API endpoints.

**Test Cases:**
- `testHealthEndpoint()` - Health check endpoint
- `testLoginValidCredentials()` - Login endpoint functionality
- `testLoginMissingPassword()` - Login missing password validation
- `testLoginMissingUsername()` - Login missing username validation
- `testLogoutSuccess()` - Logout endpoint functionality
- `testLogoutMissingUsername()` - Logout missing username validation
- `testRegisterSuccess()` - Registration endpoint
- `testRegisterMissingUsername()` - Registration validation
- `testSendMessageSuccess()` - Message sending endpoint
- `testSendMessageMissingSender()` - Message endpoint validation
- `testSendMessageMissingContent()` - Message content validation
- `testSendMessageEmptyRecipient()` - Default recipient handling

## Dependencies

Added to `pom.xml`:
- JUnit 4.13.2 - Unit testing framework
- Mockito 5.2.0 - Mocking framework
- H2 2.1.214 - In-memory database for testing

## Running Tests

### Run All Tests
```bash
cd backend
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=AuthServiceTest
mvn test -Dtest=ChatServerTest
mvn test -Dtest=ChatControllerTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=AuthServiceTest#testAuthenticateValidCredentials
```

### Run Test Suite
```bash
mvn test -Dtest=AllTests
```

### Generate Test Coverage Report
```bash
mvn clean test jacoco:report
# Coverage report: target/site/jacoco/index.html
```

### Run Tests with Verbose Output
```bash
mvn test -X
```

## Test Coverage

Current test coverage includes:
- **AuthService**: 10 test cases (authentication, session management, registration)
- **User**: 9 test cases (model functionality, getters/setters)
- **ChatServer**: 9 test cases (message handling, event listeners)
- **Message**: 7 test cases (model functionality)
- **ChatController**: 12 test cases (API endpoints, validation)

**Total: 47 unit tests**

## Code Quality Metrics

### Assertions Used
- `assertTrue()` / `assertFalse()` - Boolean conditions
- `assertEquals()` - Value equality
- `assertNotNull()` - Null checks
- `assertNull()` - Null verification

### Test Patterns
- Arrange-Act-Assert (AAA) pattern
- Setup/Teardown with @Before/@After
- Mock dependencies with @Mock
- Test data factories for complex objects

## Adding New Tests

### Template for New Test Class
```java
package com.chatapp.yourpackage;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class YourServiceTest {
    private YourService service;

    @Before
    public void setUp() {
        service = new YourService();
    }

    @Test
    public void testSomething() {
        // Arrange
        String input = "test";
        
        // Act
        String result = service.doSomething(input);
        
        // Assert
        assertEquals("Expected result", input, result);
    }
}
```

## Best Practices

1. **Naming Convention**: Test methods start with `test` followed by method name
2. **One Assertion Focus**: Each test should focus on one behavior
3. **Setup/Cleanup**: Use @Before and @After for test initialization
4. **Meaningful Messages**: Include assertion messages for clarity
5. **No Test Dependencies**: Tests should be independent and runnable in any order
6. **Mock External Dependencies**: Use Mockito for database, external services
7. **Test Edge Cases**: Include tests for null values, empty strings, boundary conditions

## Troubleshooting

### Tests Not Running
```bash
# Verify test directory structure
ls -R src/test/java/

# Check pom.xml has test dependencies
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-surefire-plugin
```

### Mocking Issues
```java
// Verify @RunWith annotation
@RunWith(MockitoJUnitRunner.class)

// Verify @Mock/@InjectMocks annotations
@Mock private Service service;
@InjectMocks private Controller controller;
```

### Database Connection Tests
Tests use in-memory H2 database by default. Configure in `DatabaseConnection.java` for test environment:
```java
// Test database URL
String DB_URL = "jdbc:h2:mem:testdb";
```

---

**Last Updated**: February 17, 2026  
**Test Framework**: JUnit 4 + Mockito 5
