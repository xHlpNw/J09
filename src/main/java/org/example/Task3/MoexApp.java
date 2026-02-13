package org.example.Task3;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MoexApp {
    public static void main(String[] args) {
        MoexClient client = new MoexClient(HttpClient.newHttpClient());
        SecuritiesParser parser = new SecuritiesParser();
        SecuritiesService service = new SecuritiesService(client, parser);

        ConsoleInputHandler inputHandler = new ConsoleInputHandler();

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        inputHandler.start(query -> {
            CompletableFuture<Void> future = service.processQuery(query);
            futures.add(future);
        });

        System.out.println("Ожидание завершения всех загрузок...");
        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        all.join();
    }
}
