package Task3;

import org.example.Task3.CsvWriterService;
import org.example.Task3.SecurityRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvWriterServiceTest {
    @TempDir
    Path tempDir;

    @Test
    void testWriteCsvFile() throws IOException {
        System.setProperty("user.home", tempDir.toString());

        SecurityRecord record1 = new SecurityRecord(
                "SEC1", "Short1", "Reg1", "Name1",
                "Emitent1", "1234567890", "987654321");
        SecurityRecord record2 = new SecurityRecord(
                "SEC2", "Short2", "Reg2", "Name2",
                "Emitent2", "0987654321", "123456789");

        String fileName = "test_file";

        CsvWriterService.write(fileName, List.of(record1, record2));

        Path csvFile = tempDir.resolve("MOEX securities")
                .resolve(fileName + ".csv");
        assertTrue(Files.exists(csvFile));

        List<String> lines = Files.readAllLines(csvFile);
        assertEquals(3, lines.size());

        assertEquals(
                "secid,shortname,regnumber,name,emitent_title,emitent_inn,emitent_okpo",
                lines.get(0));
        assertTrue(lines.get(1).contains("SEC1"));
        assertTrue(lines.get(2).contains("SEC2"));
    }
}
