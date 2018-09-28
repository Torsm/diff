package de.torsm.diff.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Does the same as a SimpleFileVisitor but gets rid of boilerplate code, especially when using anonymous classes.
 */
public class ContinuousFileVisitor extends SimpleFileVisitor<Path> {
    private final Visitor visitorFunction;

    public ContinuousFileVisitor(Visitor visitorFunction) {
        this.visitorFunction = visitorFunction;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
        FileVisitResult result = super.visitFile(file, attributes);
        visitorFunction.visit(file);
        return result;
    }

    public interface Visitor {
        void visit(Path path) throws IOException;
    }
}
