package logr;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
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
            if (args.length < 3) {
                throw new IllegalArgumentException("Please, define at least one keyword as a second parameter");
            }

            boolean isCaseSensitive = false;
            final String firstArg = args[1];
            if (firstArg.length() == 1) {
                switch (firstArg) {
                    case "h": {
                        LOGGER.info(String.format("Invalid argument: \"%s\"", firstArg));
                        return;
                    }
                    case "c": {
                        LOGGER.info("The search is case-sensitive");
                        isCaseSensitive = true;
                    }
                    default:
                        LOGGER.info(String.format("Invalid argument: \"%s\"", firstArg));
                        return;
                }
            }
            final Parameters params = Parameters.builder()
                    .isCaseSensitive(isCaseSensitive)
                    .build();
            process(args, params);
        } catch (final Throwable ex) {
            //            Logger.error(ErrorUtils.getMessage(ex));
            LOGGER.error(ErrorUtils.getStackTrace(ex));
            System.exit(1);
        }
    }

    private static void process(final String[] args, final Parameters params) {
        final String mask = args[0];
        final List<File> files = FileUtils.getFiles(mask);
        log.info("Files: \"{}\"", files.stream().map(File::getName).collect(Collectors.joining(", ")));

        final List<Highlight> highlights = HighlightCollector.buildHighlights(args);

        final long start = System.currentTimeMillis();
        final List<Pair<String, String>> lines = new FileProcessor(params, LOGGER).process(files, highlights);
        lines.forEach(pair -> LOGGER.info(String.format("%s%s%s %s\n", Logger.ROW_NUMBER, pair.getKey(), Logger.RESET, pair.getValue())));
        final long end = System.currentTimeMillis();

        final long total = lines.size();
        LOGGER.summary(String.format("  Total: %s line(s) in %s file(s) in %s"
                        , total
                        , files.size()
                        , Duration.of(end - start, ChronoUnit.MILLIS).toString().substring(2).replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase()
                )
        );
    }
}
