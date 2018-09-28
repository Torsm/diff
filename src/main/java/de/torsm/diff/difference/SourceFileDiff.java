package de.torsm.diff.difference;

import difflib.Delta;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * A FileDiff for the specific case in which only the content of a source file has been changed.
 */
public class SourceFileDiff extends FileDiff {
    private final List<Delta<String>> changes;

    /**
     * @param path Relative path to resolve the file location in both directories
     * @param changes List of changes that have been applied to the original version of the file
     */
    public SourceFileDiff(Path path, List<Delta<String>> changes) {
        super(Type.FILE_MODIFIED, path);
        this.changes = changes;
    }

    public List<Delta<String>> getChanges() {
        return Collections.unmodifiableList(changes);
    }
}
