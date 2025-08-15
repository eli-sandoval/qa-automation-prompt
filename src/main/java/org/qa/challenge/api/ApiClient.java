package org.qa.challenge.api;

import org.json.JSONObject;
import org.qa.challenge.db.RequestLogger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.qa.challenge.config.AppConfig.API_URL;

public class ApiClient {

    // Reuse a single, thread-safe HttpClient instance for all requests
    // This avoids creating a new client each time, which is expensive
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void postName(String name) {
        try {
            //Creating JSON payload
            JSONObject json = new JSONObject();
            json.put("name", name);

            // Build the HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString(), StandardCharsets.UTF_8))
                    .build();

            // Synchronous call for simplicity; can be adapted to async for high scalability
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log the request and response to the database for monitoring
            RequestLogger.logInDb(API_URL, name, response.statusCode(), response.body());

        } catch (Exception e) {
            throw new RuntimeException("API request failed", e);
        }
    }
}
