package org.qa.challenge.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    // Configuration values read from application.properties
    // All are public static so they can be accessed anywhere without instantiating
    public static String API_URL;
    public static String DB_FILE;
    public static int MONITOR_INTERVAL_MS;
    public static int MONITOR_DURATION_SEC;
    public static String SCHEMA_PATH;
    public static String DB_URL;

    // Static initializer block runs once when the class is first loaded
    // Ensures all configuration is loaded before any other class accesses these variables
    static {
        Properties properties = new Properties();
        // Load the application.properties file from resources
        try (InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in == null) throw new RuntimeException("application.properties not found");
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }

        //Assigning values to variables. This method allows variables to change without the need to touch actual code.
        API_URL = properties.getProperty("api.url");
        DB_FILE = properties.getProperty("db.file");
        MONITOR_INTERVAL_MS = Integer.valueOf(properties.getProperty("monitor.interval.ms"));
        MONITOR_DURATION_SEC = Integer.valueOf(properties.getProperty("monitor.duration.sec"));
        SCHEMA_PATH = properties.getProperty("db.schema.path");
        DB_URL = properties.getProperty("db.url");
    }
}