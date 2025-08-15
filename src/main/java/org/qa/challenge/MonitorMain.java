package org.qa.challenge;

import org.qa.challenge.db.DatabaseInitializer;
import org.qa.challenge.monitoring.ApiMonitor;

public class MonitorMain {
    public static void main(String[] args) {
        // Initialize DB
        DatabaseInitializer.initialize();
        System.out.println("Database initialized.");

        // Initialize monitoring
        System.out.println("Starting API monitoring...");
        ApiMonitor.startMonitoring();

        System.out.println("Scheduler running... check request_logs.db for entries.");

    }
}