package de.torsm.diff;

import de.torsm.diff.util.RegexUtil;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * A predicate to test if the inspected path leads to a source file which's content should be inspected for detailed
 * changes.
 */
public interface SourceFilePredicate extends Predicate<Path> {

    /**
     * Creates a SourceFilePredicate that tests if the file name matches any of the file extensions.
     *
     * @param extensions List of file extensions to match
     * @return SourceFilePredicate that tests if the file name matches any of the file extensions
     */
    static SourceFilePredicate forFileExtensions(String... extensions) {
        final Pattern pattern = RegexUtil.fileExtensionPattern(extensions);
        return path -> pattern.matcher(path.getFileName().toString()).matches();
    }
}
