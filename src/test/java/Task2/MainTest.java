package Task2;

import org.example.Task2.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    @TempDir
    Path tempDir;

    @Test
    public void testNoArgsMessage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Main.main(new String[0]);
        assertEquals("Specify the path in args" + System.lineSeparator(),
                out.toString());
    }

    @Test
    public void testNotDirectoryFile() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Path file = Files.createFile(tempDir.resolve("tempFile.txt"));
        Main.main(new String[]{file.toString()});

        assertEquals("This is not a directory" + System.lineSeparator(),
                out.toString());
    }

    @Test
    public void testCorrectDepthCalculation() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Path level1 = Files.createDirectory(tempDir.resolve("level1"));
        Path level2 = Files.createDirectory(level1.resolve("level2"));
        Files.createFile(level2.resolve("fileLevel3"));
        Main.main(new String[]{tempDir.toString()});

        assertEquals("2" + System.lineSeparator(), out.toString());
    }
}
