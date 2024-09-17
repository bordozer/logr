package logr;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

@Slf4j
public final class FileUtils {

    public static List<File> getFiles(final String mask) {
        final String wildcard = getMask(mask);
        final FileFilter fileFilter = new WildcardFileFilter(wildcard);
        final File dir = getDir(mask);
        final File[] files = dir.listFiles(fileFilter);
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("No file found by mask");
        }
        return Arrays.asList(files);
    }

    public static File getDir(final String mask) {
        final File file = new File(mask);
        if (file.isDirectory()) {
            return file;
        }
        final File dir = file.getParentFile();
        if (dir != null) {
            return dir;
        }
        return new File("./");
    }

    public static String getMask(final String mask) {
        final File file = new File(mask);
        if (file.isDirectory()) {
            return "*";
        }
        return file.getName();
    }
}
