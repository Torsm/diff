package de.torsm.diff;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Builder for DirectoryScan.
 *
 * Required properties are originalDirectory, revisedDirectory, and sourceFilePredicate.
 */
public final class DirectoryScanBuilder {
    private Path originalDirectory;
    private Path revisedDirectory;
    private SourceFilePredicate sourceFilePredicate;
    private boolean ignoreNonSourceFiles = false;
    private Charset charset = StandardCharsets.UTF_8;

    public DirectoryScan create() {
        Objects.requireNonNull(originalDirectory);
        Objects.requireNonNull(revisedDirectory);
        Objects.requireNonNull(sourceFilePredicate);
        return new DirectoryScan(originalDirectory, revisedDirectory, sourceFilePredicate, ignoreNonSourceFiles, charset);
    }

    public DirectoryScanBuilder setOriginalDirectory(Path originalDirectory) {
        this.originalDirectory = originalDirectory;
        return this;
    }

    public DirectoryScanBuilder setRevisedDirectory(Path revisedDirectory) {
        this.revisedDirectory = revisedDirectory;
        return this;
    }

    public DirectoryScanBuilder setSourceFilePredicate(SourceFilePredicate sourceFilePredicate) {
        this.sourceFilePredicate = sourceFilePredicate;
        return this;
    }

    public DirectoryScanBuilder ignoreNonSourceFiles() {
        this.ignoreNonSourceFiles = true;
        return this;
    }

    public DirectoryScanBuilder setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }
}
