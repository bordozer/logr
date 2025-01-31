package logr.utils;

import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public final class FileUtils {

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
