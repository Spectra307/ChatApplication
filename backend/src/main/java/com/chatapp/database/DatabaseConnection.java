package com.chatapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection - Manages database connectivity
 * 
 * This class handles:
 * - Connection pool management
 * - Database connection establishment
 * - Connection closure and cleanup
 * 
 * Supported databases:
 * - PostgreSQL (primary)
 * - Supabase (PostgreSQL-based)
 * - SQL Server
 */
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/chatapp";
    private static final String DB_USER = "chatapp_user";
    private static final String DB_PASSWORD = "your_secure_password";
    private static final String DRIVER = "org.postgresql.Driver";

    /**
     * Constructor - Initialize database driver
     */
    public DatabaseConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found: " + e.getMessage());
        }
    }

    /**
     * Get a database connection
     * 
     * @return Connection object if successful, null otherwise
     * @throws SQLException - If connection fails
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Test database connectivity
     * 
     * @return true if connection successful, false otherwise
     */
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Database connection successful!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Close database connection
     * 
     * @param connection - Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
