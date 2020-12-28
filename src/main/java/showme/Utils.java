package showme;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

public final class Utils {

    public static List<File> getFiles() {
        final File dir = new File(".");
        final FileFilter fileFilter = new WildcardFileFilter("sample*.java");
        final var files = dir.listFiles(fileFilter);
        if (files == null || files.length == 0) {
            Logger.error("No file found by mask");
            System.exit(1);
        }
        return Arrays.asList(files);
    }
}
