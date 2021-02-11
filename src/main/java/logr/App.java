package logr;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    private static final Logger LOGGER = new Logger();

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
            LOGGER.error(ErrorUtils.getStackTrace(ex));
            System.exit(1);
        }
    }

    private static void process(final String[] args) {
        final var mask = args[0];
        final var files = FileUtils.getFiles(mask);

        final var highlights = HighlightCollector.buildHighlights(args);
        new FileProcessor(LOGGER).process(files, highlights);
    }
}
