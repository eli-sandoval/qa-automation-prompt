package org.qa.challenge;

import org.qa.challenge.monitoring.UptimeCalculator;

public class UptimeMain {
    // Calculate uptime from the database and print it to the console
    public static void main(String[] args) {
        System.out.println("Calculating service uptime...");
        UptimeCalculator.printUptime();
    }
}
