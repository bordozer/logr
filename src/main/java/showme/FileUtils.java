package showme;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

@Slf4j
public final class FileUtils {

    public static List<File> getFiles(final String mask) {
        final File dir = getDir(mask);
        final var wildcard = getMask(mask);
        final FileFilter fileFilter = new WildcardFileFilter(wildcard);
        final var files = dir.listFiles(fileFilter);
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("No file found by mask");
        }
        return Arrays.asList(files);
    }

    public static File getDir(final String mask) {
        final var file = new File(mask);
        if (file.isDirectory()) {
            return file;
        }
        return file.getParentFile();
    }

    public static String getMask(final String mask) {
        final var file = new File(mask);
        if (file.isDirectory()) {
            return "*";
        }
        return file.getName();
    }
}
