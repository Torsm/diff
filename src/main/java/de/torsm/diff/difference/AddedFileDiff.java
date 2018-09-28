package de.torsm.diff.difference;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * A FileDiff for the specific case in which the file does not exist in the original directory but does exist in the
 * revised directory.
 */
public class AddedFileDiff extends FileDiff {
    private final List<String> content;

    /**
     * @param path Relative path to resolve the file location in both directories
     * @param content The content of the added file split into lines
     */
    public AddedFileDiff(Path path, List<String> content) {
        super(Type.FILE_ADDED, path);
        this.content = content;
    }

    public List<String> getContent() {
        return Collections.unmodifiableList(content);
    }
}
