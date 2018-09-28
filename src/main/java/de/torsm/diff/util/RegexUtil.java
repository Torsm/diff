package de.torsm.diff.util;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class RegexUtil {

    /**
     * Creates a pattern that matches strings ending with a dot followed by any of the passed file extensions, having at
     * least 1 character before the dot.
     *
     * @param extensions List of file extensions to match
     * @return Compiled Pattern
     */
    public static Pattern fileExtensionPattern(String... extensions) {
        String regex = Arrays.stream(extensions).collect(Collectors.joining("|", "^.+\\.(", ")$"));
        return Pattern.compile(regex);
    }
}
