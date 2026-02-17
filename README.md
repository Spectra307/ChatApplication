# Chat Application - Complete Documentation

A modern real-time chat application built with **Java**, **JavaScript**, **HTML**, **CSS**, and **PostgreSQL**.

## 📋 Table of Contents

1. [Project Overview](#project-overview)
2. [Tech Stack](#tech-stack)
3. [Project Structure](#project-structure)
4. [Getting Started](#getting-started)
5. [Component Documentation](#component-documentation)
6. [API Reference](#api-reference)
7. [Database Schema](#database-schema)

---

## 📱 Project Overview

This Chat Application follows the sequence diagram provided in StarUML, implementing a complete messaging system with:
- User authentication (login/logout)
- Real-time message sending and receiving
- Secure database storage
- REST API backend
- Modern responsive frontend

---

## 🛠️ Tech Stack

### Frontend
- **HTML5** - Markup structure
- **CSS3** - Styling and responsive design  
- **JavaScript (Vanilla)** - Client-side logic

### Backend
- **Java 11+** - Core application logic
- **Spring Boot 3.0** - REST API framework
- **Maven** - Dependency management and build

### Database
- **PostgreSQL** - Primary database (recommended)
- **Supabase** - Cloud alternative (PostgreSQL-based)

---

## 📁 Project Structure

```
ChatApplication/
│
├── RULES.md                          # Project rules and guidelines
├── README.md                         # This file
│
├── frontend/                         # Frontend application
│   ├── index.html                   # Login & chat UI
│   ├── styles.css                   # Styling
│   ├── script.js                    # JavaScript logic
│   └── FRONTEND_SETUP.md            # Frontend setup guide
│
├── backend/                         # Java backend application
│   ├── pom.xml                      # Maven configuration
│   ├── BACKEND_SETUP.md             # Backend setup guide
│   └── src/main/java/com/chatapp/
│       ├── ChatApplicationMain.java # Application entry point
│       ├── api/
│       │   └── ChatController.java  # REST API endpoints
│       ├── auth/
│       │   ├── AuthService.java     # Authentication logic
│       │   └── User.java             # User model
│       ├── server/
│       │   ├── ChatServer.java      # Chat functionality
│       │   └── Message.java         # Message model
│       └── database/
│           └── DatabaseConnection.java # DB connectivity
│
└── database/                        # Database scripts
    ├── schema.sql                   # Database schema
    └── README.md                    # Database setup guide
```

---

## 🚀 Getting Started

### Prerequisites

- **Java JDK 11 or higher**
- **Maven 3.6+**
- **PostgreSQL 12+ or Supabase account**
- **Node.js** (optional, for running local HTTP server)
- **Modern web browser**

### Step 1: Database Setup

#### Using PostgreSQL Locally

```bash
# Create database and user
createdb chatapp
createuser chatapp_user
psql -U postgres

# In PostgreSQL prompt:
ALTER USER chatapp_user WITH PASSWORD 'your_secure_password';
GRANT ALL PRIVILEGES ON DATABASE chatapp TO chatapp_user;
\q

# Run schema
psql -U chatapp_user -d chatapp -f database/schema.sql
```

#### Using Supabase

1. Create account at https://supabase.com
2. Create a new project
3. Go to SQL Editor and paste contents of `database/schema.sql`
4. Update `DatabaseConnection.java` with your Supabase connection details

### Step 2: Backend Setup & Run

```bash
# Navigate to backend directory
cd backend

# Copy sample configuration (if needed)
# Update DatabaseConnection.java with your DB credentials

# Install dependencies
mvn clean install

# Run application
mvn spring-boot:run
```

Backend will be available at: `http://localhost:8080`

### Step 3: Frontend Setup & Run

#### Option A: Direct File Access
```bash
# Simply open index.html in a web browser
# File path: frontend/index.html
```

#### Option B: Using HTTP Server (Recommended)

```bash
# Using Python (Python 3)
cd frontend
python -m http.server 8000

# Using Node.js (if http-server is installed)
npm install -g http-server
http-server frontend

# Using PHP
php -S localhost:8000 -t frontend
```

Access at: `http://localhost:8000`

---

## 📚 Component Documentation

### Frontend Components

#### Login Page (`index.html`)
- Username and password input fields
- Form validation
- Error message display
- Automatic dashboard redirect on success

#### Dashboard
- User welcome greeting
- Chat message display area
- Message input field with send button
- Logout button

#### Styling (`styles.css`)
- Gradient purple background
- Responsive flexbox layout
- Smooth animations and transitions
- Mobile-friendly design

#### JavaScript Logic (`script.js`)
- `handleLogin()` - Processes authentication
- `sendMessage()` - Sends messages to backend
- `displayMessage()` - Renders messages in UI
- `logout()` - Terminates session

### Backend Components

#### AuthService
- User credential verification
- Session management
- Login/logout operations
- User registration

#### ChatServer
- Message storage and retrieval
- Message delivery
- Conversation history
- Event listeners for real-time updates

#### Database Connection
- PostgreSQL/Supabase connectivity
- Connection pooling
- Error handling

#### REST API (ChatController)
- Authentication endpoints
- Message endpoints
- Health check endpoint

---

## 🔌 API Reference

### Authentication Endpoints

#### Login
```
POST /api/auth/login
Content-Type: application/json

Request:
{
  "username": "alice",
  "password": "password123"
}

Response:
{
  "success": true,
  "message": "Login successful",
  "username": "alice"
}
```

#### Logout
```
POST /api/auth/logout
Content-Type: application/json

Request:
{
  "username": "alice"
}

Response:
{
  "success": true,
  "message": "Logged out successfully"
}
```

#### Register
```
POST /api/auth/register
Content-Type: application/json

Request:
{
  "username": "newuser",
  "password": "password123",
  "email": "user@example.com"
}

Response:
{
  "success": true,
  "message": "Registration successful"
}
```

### Chat Endpoints

#### Send Message
```
POST /api/chat/send
Content-Type: application/json

Request:
{
  "sender": "alice",
  "recipient": "bob",
  "message": "Hello Bob!"
}

Response:
{
  "success": true,
  "message": "Message sent successfully"
}
```

#### Health Check
```
GET /api/health

Response:
{
  "status": "ok",
  "service": "Chat Application API"
}
```

---

## 🗄️ Database Schema

### Users Table
```sql
- user_id (INT, PK)
- username (VARCHAR, UNIQUE)
- password (VARCHAR)
- email (VARCHAR, UNIQUE)
- created_at (TIMESTAMP)
- is_active (BOOLEAN)
```

### Messages Table
```sql
- message_id (INT, PK)
- sender (VARCHAR, FK)
- recipient (VARCHAR, FK)
- content (TEXT)
- sent_at (TIMESTAMP)
- is_read (BOOLEAN)
```

### Sessions Table
```sql
- session_id (INT, PK)
- username (VARCHAR, FK)
- created_at (TIMESTAMP)
- expires_at (TIMESTAMP)
```

---

## 📝 Sample Test Users

Credentials for testing (after running schema.sql):
- **Username**: alice | **Password**: password123
- **Username**: bob | **Password**: password123
- **Username**: charlie | **Password**: password123

---

## 🔒 Security Notes

⚠️ **Important**: This is a development version. For production:
- Hash passwords using bcrypt or similar
- Implement JWT token authentication
- Add SSL/TLS encryption
- Validate all inputs server-side
- Implement rate limiting
- Add proper error handling
- Sanitize all user inputs

---

## 📖 Additional Resources

- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [RFC 5322 - Email Format](https://tools.ietf.org/html/rfc5322)
- [OWASP Security Guidelines](https://owasp.org/)

---

## 📝 Development Guidelines

As per RULES.md:
1. ✅ Code documentation is mandatory
2. ✅ All architectural modifications require StarUML diagrams
3. ✅ Follow tech stack: Frontend (HTML/CSS/JS), Backend (Java), Database (SQL)
4. ✅ Never modify without permission

---

**Project Start Date**: February 17, 2026  
**Status**: ✅ Initial Implementation Complete
