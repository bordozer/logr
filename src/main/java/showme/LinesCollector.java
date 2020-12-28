package showme;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static showme.Colorizer.parseLine;

public final class LinesCollector {

    public static List<String> collect(final List<File> files, final List<Highlight> highlights) {
        return files.stream()
                    .map(file -> processFile(file, highlights))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
    }

    @SneakyThrows
    private static List<String> processFile(final File file, final List<Highlight> highlights) {
        return Files.lines(Path.of(file.toURI()))
                .map(line -> parseLine(line, highlights))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }
}
