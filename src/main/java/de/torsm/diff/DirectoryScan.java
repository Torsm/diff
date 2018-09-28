package de.torsm.diff;

import de.torsm.diff.difference.AddedFileDiff;
import de.torsm.diff.difference.FileDiff;
import de.torsm.diff.difference.SourceFileDiff;
import de.torsm.diff.util.ContinuousFileVisitor;
import difflib.Delta;
import difflib.DiffUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Scans two specified directories for file differences within their relative file trees.
 */
public final class DirectoryScan {
    private final Path originalDirectory;
    private final Path revisedDirectory;
    private final SourceFilePredicate sourceFilePredicate;
    private final boolean ignoreNonSourceFiles;
    private final Charset charset;

    /**
     * @param originalDirectory Absolute path to the directory with the original files
     * @param revisedDirectory Absolute path to the directory with content that may have changed
     * @param sourceFilePredicate SourceFilePredicate to test if the file a path leads to is a source file
     * @param ignoreNonSourceFiles Whether or not to include non-source files in this scan
     * @param charset Charset with which the contents of a source file should be decoded
     */
    DirectoryScan(Path originalDirectory, Path revisedDirectory, SourceFilePredicate sourceFilePredicate, boolean ignoreNonSourceFiles, Charset charset) {
        this.originalDirectory = originalDirectory;
        this.revisedDirectory = revisedDirectory;
        this.sourceFilePredicate = sourceFilePredicate;
        this.ignoreNonSourceFiles = ignoreNonSourceFiles;
        this.charset = charset;
    }

    /**
     * Compares both directories file by file and adds differences to a set that is returned after scanning all files.
     *
     * If a file exists in the original directory, but not in the revised directory, it is an added file difference.
     * If a file exists in the revised directory, but not in the original directory, it is a deleted file difference.
     * If a file exists in both directories, the contents of both directories' files get compared. If the contents
     * differ, the line changes such as inserts, deletes, and changes are computed.
     *
     * @return Set of differences
     * @throws IOException Forwarded exceptions from accessing the files and file tree
     */
    public Set<FileDiff> findDifferences() throws IOException {
        final Set<Path> visited = new HashSet<>();
        final Set<FileDiff> differences = new HashSet<>();

        Files.walkFileTree(originalDirectory, new ContinuousFileVisitor(path -> {
            Path relativePath = originalDirectory.relativize(path);
            Path resolvedPath = revisedDirectory.resolve(relativePath);

            boolean sourceFile = sourceFilePredicate.test(relativePath);
            if (!sourceFile && ignoreNonSourceFiles) {
                return;
            }

            if (!Files.exists(resolvedPath)) {
                differences.add(new FileDiff(FileDiff.Type.FILE_DELETED, relativePath));
                return;
            }

            byte[] originalContent = Files.readAllBytes(path);
            byte[] revisedContent = Files.readAllBytes(resolvedPath);

            if (!Arrays.equals(originalContent, revisedContent)) {
                if (sourceFile) {
                    List<String> originalLines = Arrays.asList(new String(originalContent, charset).split("\\r?\\n"));
                    List<String> revisedLines = Arrays.asList(new String(revisedContent, charset).split("\\r?\\n"));

                    List<Delta<String>> changes = DiffUtils.diff(originalLines, revisedLines).getDeltas();

                    differences.add(new SourceFileDiff(relativePath, changes));
                } else {
                    differences.add(new FileDiff(FileDiff.Type.FILE_MODIFIED, relativePath));
                }
            }

            visited.add(relativePath);
        }));

        Files.walkFileTree(revisedDirectory, new ContinuousFileVisitor(path -> {
            Path relativePath = revisedDirectory.relativize(path);
            if (!visited.contains(relativePath)) {
                boolean sourceFile = sourceFilePredicate.test(relativePath);
                if (sourceFile || !ignoreNonSourceFiles) {
                    List<String> content = sourceFile ? Files.readAllLines(path) : Collections.EMPTY_LIST;
                    differences.add(new AddedFileDiff(relativePath, content));
                }
            }
        }));

        return differences;
    }
}