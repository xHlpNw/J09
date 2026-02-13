package org.example.Task3;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class MoexClient {
    private final HttpClient client;

    public MoexClient(HttpClient client) {
        this.client = client;
    }

    public CompletableFuture<String> searchAsync(String query) {

        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = "https://iss.moex.com/iss/securities.json?q=" + encodedQuery;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            return client
                    .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body);

        } catch (Exception e) {
            CompletableFuture<String> failed = new CompletableFuture<>();
            failed.completeExceptionally(e);
            return failed;
        }
    }
}
