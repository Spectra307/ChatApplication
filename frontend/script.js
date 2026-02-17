/**
 * Chat Application - Frontend JavaScript
 * Handles login, message sending, and UI interactions
 */

const API_BASE_URL = 'http://localhost:8080/api';
let currentUser = null;

/**
 * Initialize event listeners on page load
 */
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }
});

/**
 * Handle user login
 * @param {Event} event - Form submit event
 */
async function handleLogin(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const loginMessage = document.getElementById('loginMessage');

    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (response.ok) {
            currentUser = username;
            loginMessage.className = 'message success';
            loginMessage.textContent = 'Login successful! Redirecting...';
            
            setTimeout(() => {
                showDashboard();
            }, 1000);
        } else {
            loginMessage.className = 'message error';
            loginMessage.textContent = data.message || 'Login failed. Please try again.';
        }
    } catch (error) {
        loginMessage.className = 'message error';
        loginMessage.textContent = 'Error connecting to server.';
        console.error('Login error:', error);
    }
}

/**
 * Show dashboard and hide login page
 */
function showDashboard() {
    document.querySelector('.container').classList.add('hidden');
    document.getElementById('dashboard').classList.remove('hidden');
    document.getElementById('userDisplay').textContent = currentUser;
}

/**
 * Send message to chat server
 */
async function sendMessage() {
    const messageInput = document.getElementById('messageInput');
    const message = messageInput.value.trim();

    if (!message) return;

    try {
        const response = await fetch(`${API_BASE_URL}/chat/send`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                sender: currentUser,
                message: message
            })
        });

        if (response.ok) {
            displayMessage(message, 'sent');
            messageInput.value = '';
        } else {
            console.error('Failed to send message');
        }
    } catch (error) {
        console.error('Error sending message:', error);
    }
}

/**
 * Display message in chat box
 * @param {string} message - Message content
 * @param {string} type - 'sent' or 'received'
 */
function displayMessage(message, type) {
    const chatBox = document.getElementById('chatBox');
    const messageDiv = document.createElement('div');
    messageDiv.className = `chat-message ${type}`;
    messageDiv.textContent = message;
    chatBox.appendChild(messageDiv);
    chatBox.scrollTop = chatBox.scrollHeight;
}

/**
 * Handle user logout
 */
async function logout() {
    try {
        await fetch(`${API_BASE_URL}/auth/logout`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username: currentUser })
        });

        currentUser = null;
        document.querySelector('.container').classList.remove('hidden');
        document.getElementById('dashboard').classList.add('hidden');
        document.getElementById('loginForm').reset();
        document.getElementById('chatBox').innerHTML = '';
    } catch (error) {
        console.error('Logout error:', error);
    }
}
