package showme;

import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import showme.FileLines.FileRow;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class LinesCollector {

    public static List<FileLines> collect(final List<File> files, final List<Highlight> highlights) {
        return files.stream()
                .map(file -> processFile(file, highlights))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private static FileLines processFile(final File file, final List<Highlight> highlights) {
        if (file.isDirectory()) {
            return FileLines.builder()
                    .file(file)
                    .directory(true)
                    .build();
        }
        final var counter = new AtomicInteger(1);
        final List<FileRow> lines = Files.lines(Path.of(file.toURI()))
                .map(rawLine -> Fragmentator.process(rawLine, highlights))
                .map(fragments -> new FileRow(counter.getAndIncrement(), fragments)) // before filter to get original row number
                .filter(row -> CollectionUtils.isNotEmpty(row.getFragments()))
                .collect(Collectors.toList());
        return FileLines.builder()
                .file(file)
                .lines(lines)
                .build();
    }
}
