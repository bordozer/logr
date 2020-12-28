package showme;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static showme.Colorizer.parseLine;

public final class LinesCollector {

    public static List<Pair<Integer, String>> collect(final List<File> files, final List<Highlight> highlights) {
        return files.stream()
                    .map(file -> processFile(file, highlights))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
    }

    @SneakyThrows
    private static List<Pair<Integer, String>> processFile(final File file, final List<Highlight> highlights) {
        Logger.system(file.getAbsolutePath());
        final var counter = new AtomicInteger(1);
        return Files.lines(Path.of(file.toURI()))
                .map(line -> parseLine(line, highlights))
                .map(line -> Pair.of(counter.getAndIncrement(), line))
                .filter(pair -> StringUtils.isNotEmpty(pair.getValue()))
                .collect(Collectors.toList());
    }
}
