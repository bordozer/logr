package logr.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static logr.utils.FileUtils.getDir;
import static logr.utils.FileUtils.getMask;

@UtilityClass
public class SearchFileByWildcard {

    @SneakyThrows
    public static List<File> searchWithWc(final String mask) {
        final File dir = getDir(mask);
        final String pattern = "glob:%s".formatted(getMask(mask));
        final Path rootDir = dir.toPath();
        final List<File> matchesList = new ArrayList<>();
        final FileVisitor<Path> matcherVisitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attribs) {
                final FileSystem fs = FileSystems.getDefault();
                final PathMatcher matcher = fs.getPathMatcher(pattern);
                final Path fileName = file.getFileName();
                if (matcher.matches(fileName)) {
                    matchesList.add(file.toFile());
                }
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(rootDir, matcherVisitor);
        return matchesList;
    }
}
