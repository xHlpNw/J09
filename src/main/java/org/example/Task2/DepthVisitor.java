package org.example.Task2;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class DepthVisitor implements FileVisitor<Path> {
    private final Path root;
    private int maxDepth = 0;

    public DepthVisitor(Path root) {
        this.root = root;
    }

    public Path getRoot() {return this.root;}

    public int getMaxDepth() {return this.maxDepth;}

    @Override
    public FileVisitResult preVisitDirectory(Path dir,
                                             BasicFileAttributes attributes) {
        if (!dir.equals(root)) {
            int depth = root.relativize(dir).getNameCount();
            maxDepth = Math.max(maxDepth, depth);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attributes) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }
}
