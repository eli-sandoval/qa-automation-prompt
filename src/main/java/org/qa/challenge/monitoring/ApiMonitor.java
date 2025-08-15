package org.qa.challenge.monitoring;

import com.github.javafaker.Faker;
import org.qa.challenge.api.ApiClient;
import org.qa.challenge.config.AppConfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ApiMonitor {

    // ScheduledExecutorService manages periodic execution of tasks in a separate thread to avoid Thread.Sleep()
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void startMonitoring() {
        //Uses Faker to generate random test names
        Faker faker = new Faker();

        Runnable task = () -> {
            String testName = faker.name().firstName();
            try {
                //Send POST request and log in DB
                ApiClient.postName(testName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(
                task,
                0, // Initial delay before first execution (0 = start immediately)
                AppConfig.MONITOR_INTERVAL_MS, // Interval between executions
                TimeUnit.MILLISECONDS
        );

        // Stops scheduler after duration
        scheduler.schedule(() -> {
            scheduler.shutdown();
            System.out.println("Monitoring finished.");
        }, AppConfig.MONITOR_DURATION_SEC, TimeUnit.SECONDS);
    }
}