package com.chatapp.api;

import com.chatapp.ChatApplicationMain;
import com.chatapp.auth.AuthService;
import com.chatapp.server.ChatServer;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ChatController - REST API endpoints for Chat Application
 * 
 * Provides endpoints for:
 * - User authentication (login/logout)
 * - Message sending and retrieval
 * - User management
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChatController {
    private AuthService authService;
    private ChatServer chatServer;

    /**
     * Constructor - Initialize services
     */
    public ChatController() {
        this.authService = ChatApplicationMain.getAuthService();
        this.chatServer = ChatApplicationMain.getChatServer();
    }

    /**
     * User login endpoint
     * 
     * @param loginRequest - JSON object with username and password
     * @return Response with success/failure message
     */
    @PostMapping("/auth/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginRequest) {
        Map<String, Object> response = new HashMap<>();
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (username == null || password == null) {
            response.put("success", false);
            response.put("message", "Username and password are required");
            return response;
        }

        if (authService.authenticate(username, password)) {
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("username", username);
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password");
        }

        return response;
    }

    /**
     * User logout endpoint
     * 
     * @param logoutRequest - JSON object with username
     * @return Response with success/failure message
     */
    @PostMapping("/auth/logout")
    public Map<String, Object> logout(@RequestBody Map<String, String> logoutRequest) {
        Map<String, Object> response = new HashMap<>();
        String username = logoutRequest.get("username");

        if (username == null) {
            response.put("success", false);
            response.put("message", "Username is required");
            return response;
        }

        authService.logout(username);
        response.put("success", true);
        response.put("message", "Logged out successfully");

        return response;
    }

    /**
     * User registration endpoint
     * 
     * @param registerRequest - JSON object with username, password, email
     * @return Response with success/failure message
     */
    @PostMapping("/auth/register")
    public Map<String, Object> register(@RequestBody Map<String, String> registerRequest) {
        Map<String, Object> response = new HashMap<>();
        String username = registerRequest.get("username");
        String password = registerRequest.get("password");
        String email = registerRequest.get("email");

        if (username == null || password == null) {
            response.put("success", false);
            response.put("message", "Username and password are required");
            return response;
        }

        if (authService.registerUser(username, password)) {
            response.put("success", true);
            response.put("message", "Registration successful");
        } else {
            response.put("success", false);
            response.put("message", "Registration failed");
        }

        return response;
    }

    /**
     * Send message endpoint
     * 
     * @param messageRequest - JSON object with sender, recipient (optional), message
     * @return Response with success/failure message
     */
    @PostMapping("/chat/send")
    public Map<String, Object> sendMessage(@RequestBody Map<String, String> messageRequest) {
        Map<String, Object> response = new HashMap<>();
        String sender = messageRequest.get("sender");
        String recipient = messageRequest.get("recipient");
        String message = messageRequest.get("message");

        if (sender == null || message == null) {
            response.put("success", false);
            response.put("message", "Sender and message content are required");
            return response;
        }

        // Check if sender is logged in
        if (!authService.isLoggedIn(sender)) {
            response.put("success", false);
            response.put("message", "Sender is not logged in");
            return response;
        }

        // Use default recipient if not specified
        if (recipient == null) {
            recipient = "broadcast";
        }

        if (chatServer.sendMessage(sender, recipient, message)) {
            response.put("success", true);
            response.put("message", "Message sent successfully");
        } else {
            response.put("success", false);
            response.put("message", "Failed to send message");
        }

        return response;
    }

    /**
     * Health check endpoint
     * 
     * @return Server status
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("service", "Chat Application API");
        return response;
    }
}
