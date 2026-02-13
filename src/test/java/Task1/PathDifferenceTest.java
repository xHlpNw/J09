package Task1;

import org.example.Task1.PathDifference;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathDifferenceTest {
    static Path file1;
    static Path file2;
    static Path dir1;
    static Path dir2;

    @BeforeAll
    static void setup() throws IOException {
        dir1 = Files.createTempDirectory("dir1");
        dir2 = Files.createTempDirectory("dir2");
        file1 = Files.createTempFile(dir1, "file1", ".txt");
        file2 = Files.createTempFile(dir2, "file2", ".txt");

        Files.writeString(file1, "Small");
        Files.writeString(file2, "Big file with huge amount of symbols");
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(file1);
        Files.deleteIfExists(file2);
        Files.deleteIfExists(dir1);
        Files.deleteIfExists(dir2);
    }

    @Test
    public void testIsNotExists() {
        assertFalse(PathDifference.isNotExists(file1, file2));
        assertFalse(PathDifference.isNotExists(dir1, dir2));
        assertFalse(PathDifference.isNotExists(file1, dir1));
        assertTrue(
                PathDifference.isNotExists(file1, Path.of("UnknownFile.bin")));
    }

    @Test
    public void testIsSameFile() {
        assertFalse(PathDifference.isSameFile(dir1, dir1));
        assertFalse(PathDifference.isSameFile(file1, file2));
        assertTrue(PathDifference.isSameFile(file1, file1));
        assertFalse(PathDifference.isSameFile(Path.of("UnknownFile.bin"),
                Path.of("UnknownFile.bin")));
    }

    @Test
    public void testIsBiggerFile() {
        assertFalse(PathDifference.isBiggerFile(dir1, file2));
        assertFalse(PathDifference.isBiggerFile(dir1, dir2));
        assertFalse(PathDifference.isBiggerFile(file1, file2));
        assertTrue(PathDifference.isBiggerFile(file2, file1));
        assertFalse(PathDifference.isBiggerFile(Path.of("UnknownFile.bin"),
                Path.of("UnknownFile.bin")));
    }

    @Test
    public void testIsSmallerFile() {
        assertFalse(PathDifference.isSmallerFile(dir1, file2));
        assertFalse(PathDifference.isSmallerFile(dir1, dir2));
        assertFalse(PathDifference.isSmallerFile(file2, file1));
        assertTrue(PathDifference.isSmallerFile(file1, file2));
        assertFalse(PathDifference.isSmallerFile(Path.of("UnknownFile.bin"),
                Path.of("UnknownFile.bin")));
    }

    @Test
    public void testIsSameSizeFile() {
        assertFalse(PathDifference.isSameSizeFile(dir1, dir1));
        assertFalse(PathDifference.isSameSizeFile(file1, file2));
        assertTrue(PathDifference.isSameSizeFile(file1, file1));
        assertFalse(PathDifference.isSameSizeFile(Path.of("UnknownFile.bin"),
                Path.of("UnknownFile.bin")));
    }

    @Test
    public void testIsSameDirectory() {
        assertFalse(PathDifference.isSameDirectory(file1, file1));
        assertFalse(PathDifference.isSameDirectory(dir1, dir2));
        assertTrue(PathDifference.isSameDirectory(dir1, dir1));
        assertFalse(PathDifference.isSameFile(Path.of("UnknownDir"),
                Path.of("UnknownDir")));
    }

    @Test
    public void testIsSameAbsoluteNameDepth() throws IOException {
        assertTrue(PathDifference.isSameAbsoluteNameDepth(dir1, dir1));
        assertTrue(PathDifference.isSameAbsoluteNameDepth(dir1, dir2));
        assertFalse(PathDifference.isSameAbsoluteNameDepth(dir1, file1));
        assertTrue(PathDifference.isSameAbsoluteNameDepth(
                Paths.get("folder1", "file1.txt"),
                Paths.get("folder1", "folder2", "..", "file1.txt")));
    }

    @Test
    public void testIsSamePrefix() {
        Path root = Paths.get(
                FileSystems.getDefault().getRootDirectories().iterator().next()
                        .toUri());
        System.out.println(root);
        assertFalse(PathDifference.isSamePrefix(root, root));
        assertFalse(PathDifference.isSamePrefix(root.resolve("file1"),
                root.resolve("file2")));
        assertTrue(PathDifference.isSamePrefix(
                root.resolve("folder1").resolve("file1"),
                root.resolve("folder1").resolve("file2")));
        assertTrue(PathDifference.isSamePrefix(
                root.resolve("folder1").resolve("folder2").resolve("..")
                        .resolve("file1"),
                root.resolve("folder1").resolve("file2")));
        assertTrue(PathDifference.isSamePrefix(
                root.resolve("folder1").resolve("folder2").resolve("file1"),
                root.resolve("folder1").resolve("file2")));
    }

    @Test
    public void testIsSameRoot() {
        File[] roots = File.listRoots();
        assertTrue(PathDifference.isSameRoot(
                Paths.get(roots[0].getAbsolutePath(), "folder1",
                        "newFile1.bin"),
                Paths.get(roots[0].getAbsolutePath(), "folder2",
                        "newFile2.txt")));
        if (roots.length >= 2) {
            assertFalse(PathDifference.isSameRoot(
                    Paths.get(roots[0].getAbsolutePath(), "folder1",
                            "newFile1.bin"),
                    Paths.get(roots[1].getAbsolutePath(), "folder2",
                            "newFile2.txt")));
        }
    }

    @Test
    public void testIsSubpath() {
        assertTrue(PathDifference.isSubpath(dir1, file1));
        assertFalse(PathDifference.isSubpath(file1, dir1));
        assertFalse(PathDifference.isSubpath(dir1, dir2));
        assertFalse(PathDifference.isSubpath(dir1, dir1));
    }

    @Test
    public void testIsParentPath() {
        assertTrue(PathDifference.isParentPath(file1, dir1));
        assertFalse(PathDifference.isParentPath(dir1, file1));
        assertFalse(PathDifference.isParentPath(dir1, dir2));
        assertFalse(PathDifference.isParentPath(dir1, dir1));
    }
}
