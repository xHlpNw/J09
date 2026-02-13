package org.example.Task3;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class SecuritiesService {
    private final MoexClient client;
    private final SecuritiesParser parser;

    public SecuritiesService(MoexClient client, SecuritiesParser parser) {
        this.client = client;
        this.parser = parser;
    }

    public CompletableFuture<Void> processQuery(String searchQuery) {
        System.out.println("Начало загрузки: " + searchQuery);

        return client.searchAsync(searchQuery).thenApply(parser::parseAndFilter)
                .thenAccept(records -> {
                    try {
                        CsvWriterService.write(searchQuery, records);
                        System.out.println(
                                "Завершена загрузка: " + searchQuery);
                    } catch (IOException e) {
                        System.err.printf("Ошибка при записи CSV '%s': %s",
                                searchQuery, e.getMessage());
                    }
                }).exceptionally(ex -> {
                    System.err.printf("Ошибка при обработке запроса '%s': %s",
                            searchQuery, ex.getMessage());
                    return null;
                });
    }
}
