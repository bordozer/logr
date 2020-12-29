package showme;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

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
            Logger.error(ex.getMessage());
            System.exit(1);
        }
    }

    private static void process(final String[] args) {
        final var mask = args[0];
        final var files = FileUtils.getFiles(mask);
        Logger.system(String.format("Found %s file(s)", files.size()));

        final var highlights = HighlightCollector.buildHighlights(args);
        final var colorizedLines = LinesCollector.collect(files, highlights);
        colorizedLines.forEach(pair -> Logger.info(String.format("%s%s%s %s", Logger.BLACK_BACKGROUND_BRIGHT, pair.getKey(), Logger.RESET, pair.getValue())));

        Logger.system(String.format("Total: %s line(s)", colorizedLines.size()));
    }
}
