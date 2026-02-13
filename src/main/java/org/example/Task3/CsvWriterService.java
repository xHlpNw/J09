package org.example.Task3;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvWriterService {
    private static final String DIRECTORY_NAME = "MOEX securities";

    public static void write(String fileName,
                             List<SecurityRecord> records) throws IOException {
        Path dir = Paths.get(System.getProperty("user.home"), DIRECTORY_NAME);
        Files.createDirectories(dir);

        Path file = dir.resolve(sanitizeFileName(fileName) + ".csv");

        try (CSVPrinter printer = new CSVPrinter(Files.newBufferedWriter(file),
                CSVFormat.DEFAULT.withHeader(
                        "secid", "shortname", "regnumber", "name",
                        "emitent_title", "emitent_inn", "emitent_okpo"
                )
        )) {
            for (SecurityRecord record : records) {
                printer.printRecord(
                        record.secid(), record.shortname(),
                        record.regnumber(), record.name(),
                        record.emitentTitle(), record.emitentInn(),
                        record.emitentOkpo()
                );
            }
        }
    }

    private static String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9а-яА-Я._-]", "_");
    }
}
