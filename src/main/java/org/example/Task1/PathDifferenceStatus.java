package org.example.Task1;

import java.nio.file.Path;
import java.util.function.BiPredicate;

public enum PathDifferenceStatus {
    NotExists(PathDifference::isNotExists),
    SameFile(PathDifference::isSameFile),
    BiggerFile(PathDifference::isBiggerFile),
    SmallerFile(PathDifference::isSmallerFile),
    SameSizeFile(PathDifference::isSameSizeFile),
    SameDirectory(PathDifference::isSameDirectory),
    SameAbsoluteNameDepth(PathDifference::isSameAbsoluteNameDepth),
    SamePrefix(PathDifference::isSamePrefix),
    SameRoot(PathDifference::isSameRoot),
    Subpath(PathDifference::isSubpath),
    ParentPath(PathDifference::isParentPath);

    private final BiPredicate<Path, Path> check;

    PathDifferenceStatus(BiPredicate<Path, Path> check) {
        this.check = check;
    }

    public boolean test(Path path1, Path path2) {
        return check.test(path1, path2);
    }
}
