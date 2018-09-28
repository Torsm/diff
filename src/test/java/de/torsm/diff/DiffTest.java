package de.torsm.diff;

import com.google.gson.GsonBuilder;
import de.torsm.diff.difference.FileDiff;
import de.torsm.diff.serialization.DeltaSerializer;
import difflib.Delta;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

public class DiffTest {

    @Test
    public void test() throws IOException {
        DirectoryScan scan = new DirectoryScanBuilder()
                .setOriginalDirectory(Paths.get("C:\\Users\\Torben\\workspace\\old\\src1"))
                .setRevisedDirectory(Paths.get("C:\\Users\\Torben\\workspace\\old\\src2"))
                .setSourceFilePredicate(SourceFilePredicate.forFileExtensions("java", "kt", "xml", "fxml", "css", "txt"))
                .create();
        Set<FileDiff> differences = scan.findDifferences();
        String s = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .registerTypeAdapter(Delta.class, new DeltaSerializer()).create().toJson(differences);

        System.out.println();
        System.out.println();
        System.out.println(s);
        System.out.println();
        System.out.println();
    }
}
