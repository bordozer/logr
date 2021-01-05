package showme;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class App {

    @SneakyThrows
    public static void main(String[] args) {
        try {
            // log.info("args: {}", JsonUtils.toJson(args));
            if (args == null || args.length == 0) {
                throw new IllegalArgumentException("First parameter (file mask) is missed");
            }
            process(args);
        } catch (final Throwable ex) {
            Logger.error(ErrorUtils.getMessage(ex));
            System.exit(1);
        }
    }

    private static void process(final String[] args) {
        final var mask = args[0];
        final var files = FileUtils.getFiles(mask);

        final var highlights = HighlightCollector.buildHighlights(args);
        final var colorizedLines = LinesCollector.collect(files, highlights);
        colorizedLines
                .forEach(fl -> {
                    final var file = fl.getFile();
                    final var lines = fl.getLines();
                    Logger.system(String.format("  %s (%s)", file.getAbsolutePath(), lines.size()));
                    lines.forEach(pair -> Logger.info(String.format("%s%s%s %s", Logger.BLACK_BACKGROUND_BRIGHT, pair.getRowNumber(), Logger.RESET, pair.getLine())));
                });

        final var total = colorizedLines.stream()
                .map(FileLines::getLines)
                .mapToLong(Collection::size)
                .sum();
        Logger.system(String.format("  Total: %s line(s) in %s file(s)", total, files.size()));
    }
}
