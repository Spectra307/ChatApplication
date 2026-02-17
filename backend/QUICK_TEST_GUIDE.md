# Quick Test Guide

## Fast Start: Run All Tests

```bash
cd backend
mvn clean test
```

## Individual Test Runs

### Authentication Tests
```bash
mvn test -Dtest=AuthServiceTest
```

### User Model Tests
```bash
mvn test -Dtest=UserTest
```

### Chat Server Tests
```bash
mvn test -Dtest=ChatServerTest
```

### Message Model Tests
```bash
mvn test -Dtest=MessageTest
```

### REST API Tests
```bash
mvn test -Dtest=ChatControllerTest
```

### All Tests in Suite
```bash
mvn test -Dtest=AllTests
```

## Specific Test Methods

```bash
# Test login authentication
mvn test -Dtest=AuthServiceTest#testAuthenticateValidCredentials

# Test user registration
mvn test -Dtest=AuthServiceTest#testRegisterUserSuccess

# Test message sending
mvn test -Dtest=ChatServerTest#testSendMessageSuccess

# Test logout functionality
mvn test -Dtest=AuthServiceTest#testLogoutRemovesSession
```

## With Coverage Report

```bash
# Added jacoco plugin to pom.xml
mvn clean test jacoco:report

# Open coverage report
# Windows: start target\site\jacoco\index.html
# Linux/Mac: open target/site/jacoco/index.html
```

## Verbose Output

```bash
mvn test -X -e
```

## Skip Tests During Build

```bash
mvn clean install -DskipTests
```

## Test Summary

- **Total Tests**: 47
- **Test Files**: 6
- **Code Coverage**: AuthService, User, ChatServer, Message, ChatController
- **Framework**: JUnit 4 + Mockito
