package org.example.Task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryDepthCalculator {
    public static int getMaxDepth(Path path) {
        if (!Files.exists(path) || !Files.isDirectory(path)) return -1;

        DepthVisitor visitor = new DepthVisitor(path);
        try {
            Files.walkFileTree(path, visitor);
        } catch (IOException e) {
            return -1;
        }

        return visitor.getMaxDepth();
    }
}
