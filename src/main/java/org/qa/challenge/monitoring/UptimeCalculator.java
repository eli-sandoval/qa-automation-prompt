package org.qa.challenge.monitoring;

import org.qa.challenge.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UptimeCalculator {

    public static double calculateUptime() {
        // SQL query to count all requests in the database
        String queryTotal = "SELECT COUNT(*) AS total FROM request_logs";
        // SQL query to count only successful requests (HTTP 200)
        String querySuccess = "SELECT COUNT(*) AS success FROM request_logs WHERE response_status = 200";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Execute query to get total requests
            ResultSet resultTotal = statement.executeQuery(queryTotal);
            resultTotal.next();
            int total = resultTotal.getInt("total");

            // Execute query for successful requests
            ResultSet resultSuccess = statement.executeQuery(querySuccess);
            resultSuccess.next();
            int success = resultSuccess.getInt("success");

            // Avoid division by zero if no requests logged
            if (total == 0) return 0.0;

            //Calculate uptime percentage
            return (success * 100.0) / total;

        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate uptime", e);
        }
    }

    public static void printUptime() {
        double uptime = calculateUptime();
        // Print formatted percentage
        System.out.printf("Service uptime: %.2f%%\n", uptime);
    }
}