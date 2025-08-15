package org.qa.challenge.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequestLogger {

    // SQL query to insert a request record into the database
    // Using placeholders (?) prevents SQL injection
    private static final String INSERT_SQL =
            "INSERT INTO request_logs(url, name_parameter, response_status, response_text) VALUES (?, ?, ?, ?)";

    public static void logInDb(String url, String nameParameter, int status, String response) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setString(1, url);
            preparedStatement.setString(2, nameParameter);
            preparedStatement.setInt(3, status);
            preparedStatement.setString(4, response);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}