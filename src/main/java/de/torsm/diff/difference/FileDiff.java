package de.torsm.diff.difference;

import java.nio.file.Path;

/**
 * Represents a difference between two directories for a single file.
 */
public class FileDiff {
    private final Type type;
    private final String path;

    /**
     * @param type Type of this FileDiff
     * @param path Relative path to resolve the file location in both directories
     */
    public FileDiff(Type type, Path path) {
        this.type = type;
        this.path = path.toString();
    }

    public Type getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public enum Type {
        FILE_ADDED, FILE_DELETED, FILE_MODIFIED
    }
}
