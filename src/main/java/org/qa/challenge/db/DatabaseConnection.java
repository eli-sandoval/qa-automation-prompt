package org.qa.challenge.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.qa.challenge.config.AppConfig.DB_FILE;
import static org.qa.challenge.config.AppConfig.DB_URL;

public class DatabaseConnection {
    // Reusing the same connection avoids opening multiple connections unnecessarily
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        // Create a new connection if none exists or if previous one is closed
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(DB_URL + DB_FILE);
        }
        return conn;
    }
}