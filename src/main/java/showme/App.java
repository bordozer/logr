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
//            Logger.error(ErrorUtils.getMessage(ex));
            Logger.error(ErrorUtils.getStackTrace(ex));
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
                    if (fl.getDirectory()) {
                        Logger.system2(String.format("  %s is a directory, skipped", file.getAbsolutePath()));
                        return;
                    }
                    final var fileRows = fl.getLines();
                    Logger.system2(String.format("  %s (%s)", file.getAbsolutePath(), fileRows.size()));
                    fileRows.forEach(pair -> Logger.info(String.format("%s%s%s %s", Logger.ROW_NUMBER, pair.getOriginalRowNumber(), Logger.RESET, Colorizer.process(pair.getFragments()))));
                });

        final var total = colorizedLines.stream()
                .map(FileLines::getLines)
                .mapToLong(Collection::size)
                .sum();
        Logger.system1(String.format("  Total: %s line(s) in %s file(s)", total, files.size()));
    }
}
