package org.example.Task3;

import java.util.Scanner;
import java.util.function.Consumer;

public class ConsoleInputHandler {
    public void start(Consumer<String> onInput) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите строку поиска (/exit для выхода):");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("/exit")) {
                break;
            }

            if (!input.isEmpty()) {
                onInput.accept(input);
            }
        }
    }
}
