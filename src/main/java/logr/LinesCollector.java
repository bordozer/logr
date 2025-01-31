package logr;

import logr.model.FileContainer;
import logr.model.FileContainer.FileRow;
import logr.model.Highlight;
import logr.model.Parameters;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class LinesCollector {

    public static List<FileContainer> collect(final List<File> files, final List<Highlight> highlights, final Parameters parameters) {
        return files.stream()
                .map(file -> processFile(file, highlights, parameters))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private static FileContainer processFile(final File file, final List<Highlight> highlights, final Parameters parameters) {
        if (file.isDirectory()) {
            return FileContainer.builder()
                    .file(file)
                    .directory(true)
                    .build();
        }
        final AtomicInteger counter = new AtomicInteger(1);
        try (final var lines = Files.lines(Path.of(file.toURI()))) {
            final List<FileRow> result = lines.map(rawLine -> Fragmentator.process(rawLine, highlights, parameters))
                    .map(fragments -> new FileRow(counter.getAndIncrement(), fragments)) // before filter to get original row number
                    .filter(row -> CollectionUtils.isNotEmpty(row.getFragments()))
                    .collect(Collectors.toList());
            return FileContainer.builder()
                    .file(file)
                    .lines(result)
                    .build();
        }
    }
}
