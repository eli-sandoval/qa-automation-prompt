package tests;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.qa.challenge.config.AppConfig;
import org.qa.challenge.db.DatabaseInitializer;
import org.qa.challenge.db.RequestLogger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NameFormatTest {

    private static final String[] PROBLEMATIC_NAMES = {"apple", "puppet", "puppies", "happen", "ripple"};

    @BeforeAll
    public static void setup() {
        // Initialize DB and schema before running tests
        DatabaseInitializer.initialize();
    }

    //Bug occurs when the string used for the name field includes two lower-case p. Regardless of order or proximity.
    //Due to the hard-coded flakiness meant for the uptime task, this bug may sometimes fail with the "system down" message.
    @Test
    public void reproduceTwoPLowercaseBug() {
        HttpClient client = HttpClient.newHttpClient();

        for (int i = 0; i < PROBLEMATIC_NAMES.length; i++) {
            String name = PROBLEMATIC_NAMES[i];
            try {
                System.out.println("Attempt " + (i + 1) + ": Sending name " + name);

                // Prepare JSON payload
                JSONObject json = new JSONObject();
                json.put("name", name);

                // Build HTTP POST request
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(AppConfig.API_URL))
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                        .build();

                // Send request and capture response
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Assert on response
                Assertions.assertEquals(500, response.statusCode());
                Assertions.assertEquals("{\"message\":\"Unexpected server error\"}", response.body());
                System.out.println("Status code: " + response.statusCode());
                System.out.println("Response body: " + response.body());

                // Log to database (optional but keeps record of test)
                RequestLogger.logInDb(AppConfig.API_URL, name, response.statusCode(), response.body());

            } catch (Exception e) {
                e.printStackTrace(); // Prevents a single failure from stopping the loop
            }
        }
    }
}