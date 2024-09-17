package logr;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

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
        final String mask = args[0];
        final List<File> files = FileUtils.getFiles(mask);
        log.info("Files: \"{}\"", files.stream().map(File::getName).collect(Collectors.joining(", ")));

        final List<Highlight> highlights = HighlightCollector.buildHighlights(args);
        new FileProcessor(LOGGER).process(files, highlights);
    }
}
