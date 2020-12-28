package showme;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Colorizer {

    @SneakyThrows
    public static List<String> colorizeLines(final File file, final List<Keyword> keywords) {
        return Files.lines(Path.of(file.toURI()))
                .map(line -> {
                    var updates = 0;
                    var coloredLine = line;
                    for (final Keyword keyword : keywords) {
                        final var coloredKeywordText = String.format("%s%s%s", keyword.getColor().getValue(), keyword.getText(), Logger.RESET);
                        if (coloredLine.contains(keyword.getText())) {
                            updates++;
                            coloredLine = coloredLine.replace(keyword.getText(), coloredKeywordText);
                        }
                    }
                    if (updates == 0) {
                        return null;
                    }
                    return coloredLine;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
