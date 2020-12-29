package showme;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static showme.Colorizer.parseLine;

public final class LinesCollector {

    public static List<FileLines> collect(final List<File> files, final List<Highlight> highlights) {
        return files.stream()
                .map(file -> processFile(file, highlights))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private static FileLines processFile(final File file, final List<Highlight> highlights) {
        final var counter = new AtomicInteger(1);
        final List<Pair<Integer, String>> lines = Files.lines(Path.of(file.toURI()))
                .map(line -> parseLine(line, highlights))
                .map(line -> Pair.of(counter.getAndIncrement(), line))
                .filter(pair -> StringUtils.isNotEmpty(pair.getValue()))
                .collect(Collectors.toList());
        return new FileLines(file, lines);
    }
}
