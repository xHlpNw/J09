package Task2;

import org.example.Task2.DirectoryDepthCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectoryDepthCalculatorTest {
    @TempDir
    Path tempDir;

    @Test
    public void testNonExistentDirectory() {
        assertEquals(-1, DirectoryDepthCalculator.getMaxDepth(
                tempDir.resolve("UnknownFile")));
    }

    @Test
    public void testNotDirectoryFile() throws IOException {
        Path file = Files.createFile(tempDir.resolve("UnknownFile.bin"));
        assertEquals(-1, DirectoryDepthCalculator.getMaxDepth(file));
    }

    @Test
    public void testEmptyDir() {
        assertEquals(0, DirectoryDepthCalculator.getMaxDepth(tempDir));
    }

    @Test
    public void testSingleLevelHierarchy() throws IOException {
        Path subDir = Files.createDirectory(tempDir.resolve("subDir"));
        assertEquals(1, DirectoryDepthCalculator.getMaxDepth(tempDir));
    }

    @Test
    public void testMultipleLevelHierarchy() throws IOException {
        Path level1 = Files.createDirectory(tempDir.resolve("Level1"));
        Path level2 = Files.createDirectory(level1.resolve("Level2"));
        Files.createDirectory(level2.resolve("level3"));
        Files.createDirectory(level1.resolve("Level22"));
        assertEquals(3, DirectoryDepthCalculator.getMaxDepth(tempDir));
    }
}
