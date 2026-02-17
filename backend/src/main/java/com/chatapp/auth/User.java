package com.chatapp.auth;

/**
 * User - Data model for chat application users
 * 
 * Represents a user with username, password, and user metadata
 */
public class User {
    private long userId;
    private String username;
    private String password;
    private String email;
    private boolean isActive;

    /**
     * Constructor - Create a new user
     * 
     * @param username - User's username
     * @param password - User's password (should be hashed)
     * @param email - User's email address
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = true;
    }

    // Getters
    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * String representation of user
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
