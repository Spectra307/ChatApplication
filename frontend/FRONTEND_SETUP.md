# Chat Application Frontend - Setup & Documentation

## Project Structure

```
frontend/
├── index.html       - Main login and chat UI
├── styles.css       - All frontend styling
└── script.js        - JavaScript functionality
```

## Features Implemented

### 1. Login Page
- Username and password input fields
- Login form validation
- Error message display
- Automatic redirect to dashboard on successful login

### 2. Dashboard
- User welcome message
- Chat display area
- Message input field with send button
- Logout button

### 3. UI Components
- Responsive design with gradient background
- Clean and modern interface
- Mobile-friendly layout
- Message bubbles (sent/received distinction)

## Setup & Running

### Prerequisites
- A modern web browser (Chrome, Firefox, Safari, Edge)
- Backend server running on `http://localhost:8080`

### Steps

1. **Open the application:**
   - Open `index.html` in a web browser
   - Or use a local server: `python -m http.server 8000`

2. **Login:**
   - Enter username: `alice`, `bob`, or `charlie`
   - Enter password: `password123`

3. **Use the chat:**
   - Type a message and press Send
   - Messages appear in the chat box

### API Endpoints Used

- `POST /api/auth/login` - Authenticates user
- `POST /api/auth/logout` - Logs out user
- `POST /api/chat/send` - Sends a message

## JavaScript Functions

- `handleLogin(event)` - Processes login form submission
- `showDashboard()` - Displays chat interface
- `sendMessage()` - Sends message to server
- `displayMessage(message, type)` - Shows message in chat box
- `logout()` - Logs out current user

## CSS Styling

- Gradient background with purple tones
- Flexbox layout for responsive design
- Smooth transitions and hover effects
- Clean typography with Segoe UI font family
