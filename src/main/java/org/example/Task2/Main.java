package org.example.Task2;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Specify the path in args");
            return;
        }

        Path path = Paths.get(args[0]);

        int depth = DirectoryDepthCalculator.getMaxDepth(path);

        if (depth == -1) System.out.println("This is not a directory");
        else System.out.println(DirectoryDepthCalculator.getMaxDepth(path));
    }
}
