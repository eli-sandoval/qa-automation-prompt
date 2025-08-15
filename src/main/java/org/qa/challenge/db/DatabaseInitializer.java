package org.qa.challenge.db;


import org.apache.ibatis.jdbc.ScriptRunner;
import org.qa.challenge.config.AppConfig;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Objects;

//Ensures automatic closing of resources, preventing leaks.
//Centralizes DB connection logic and avoids multiple connections
public class DatabaseInitializer {

    public static void initialize() {
        try (Connection connection = DatabaseConnection.getConnection();
             // Load the SQL schema file from resources
             InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(DatabaseInitializer.class.getClassLoader().getResourceAsStream(AppConfig.SCHEMA_PATH), "Schema not found"),
                     StandardCharsets.UTF_8)) {

            ScriptRunner runner = new ScriptRunner(connection);
            // Execute the SQL schema script
            runner.runScript(reader);

        } catch (Exception e) {
            throw new RuntimeException("DB initialization failed.", e);
        }
    }
}