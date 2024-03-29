package logr;

import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import logr.FileContainer.FileRow;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class LinesCollector {

    public static List<FileContainer> collect(final List<File> files, final List<Highlight> highlights) {
        return files.stream()
                .map(file -> processFile(file, highlights))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private static FileContainer processFile(final File file, final List<Highlight> highlights) {
        if (file.isDirectory()) {
            return FileContainer.builder()
                    .file(file)
                    .directory(true)
                    .build();
        }
        final var counter = new AtomicInteger(1);
        final List<FileRow> lines = Files.lines(Path.of(file.toURI()), StandardCharsets.ISO_8859_1)
                .map(rawLine -> Fragmentator.process(rawLine, highlights))
                .map(fragments -> new FileRow(counter.getAndIncrement(), fragments)) // before filter to get original row number
                .filter(row -> CollectionUtils.isNotEmpty(row.getFragments()))
                .collect(Collectors.toList());
        return FileContainer.builder()
                .file(file)
                .lines(lines)
                .build();
    }
}
