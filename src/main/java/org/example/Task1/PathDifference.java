package org.example.Task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

public class PathDifference {
    public static List<PathDifferenceStatus> difference(Path path1, Path path2) {
        return Arrays.stream(PathDifferenceStatus.values())
                .filter(status -> status.test(path1, path2))
                .toList();
    }

    private static boolean isRegularFiles(Path path1, Path path2) {
        return Files.isRegularFile(path1) && Files.isRegularFile(path2);
    }

    public static boolean isNotExists(Path path1, Path path2) {
        return Files.notExists(path1) || Files.notExists(path2);
    }

    public static boolean isSameFile(Path path1, Path path2) {
        try {
            return isRegularFiles(path1, path2)
                    && Files.isSameFile(path1, path2);
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean compareFileSizes(Path path1, Path path2, BiPredicate<Long, Long> check) {
        if (!isRegularFiles(path1, path2)) return false;
        try {
            long size1 = Files.size(path1);
            long size2 = Files.size(path2);
            return check.test(size1, size2);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isBiggerFile(Path path1, Path path2) {
        return compareFileSizes(path1, path2, (size1, size2) -> size1 > size2);
    }

    public static boolean isSmallerFile(Path path1, Path path2) {
        return compareFileSizes(path1, path2, (size1, size2) -> size1 < size2);
    }

    public static boolean isSameSizeFile(Path path1, Path path2) {
        return compareFileSizes(path1, path2, Long::equals);
    }

    public static boolean isSameDirectory(Path path1, Path path2) {
        try {
            return Files.isDirectory(path1)
                    && Files.isDirectory(path2)
                    && path1.toRealPath().equals(path2.toRealPath());
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isSameAbsoluteNameDepth(Path path1, Path path2) {
        Path absPath1 = path1.toAbsolutePath().normalize();
        Path absPath2 = path2.toAbsolutePath().normalize();
        return absPath1.getNameCount() == absPath2.getNameCount();
    }

    public static boolean isSamePrefix(Path path1, Path path2) {
        Path normPath1 = path1.toAbsolutePath().normalize();
        Path normPath2 = path2.toAbsolutePath().normalize();

        int minDepth = Math.min(normPath1.getNameCount(), normPath2.getNameCount());

        for (int i = 0; i < minDepth - 1; i++) {
            if (!normPath1.getName(i).equals(normPath2.getName(i))) {
                return false;
            }
        }
        return minDepth > 1;
    }

    public static boolean isSameRoot(Path path1, Path path2) {
        Path rootPath1 = path1.toAbsolutePath().getRoot();
        Path rootPath2 = path2.toAbsolutePath().getRoot();
        return rootPath1 != null && rootPath1.equals(rootPath2);
    }

    private static boolean isSubpathOf(Path parent, Path child) {
        Path path1 = parent.toAbsolutePath().normalize();
        Path path2 = child.toAbsolutePath().normalize();
        return path2.startsWith(path1) && !path1.equals(path2);
    }

    public static boolean isSubpath(Path path1, Path path2) {
        return isSubpathOf(path1, path2);
    }

    public static boolean isParentPath(Path path1, Path path2) {
        return isSubpathOf(path2, path1);
    }
}
