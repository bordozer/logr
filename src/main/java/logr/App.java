package logr;

import logr.utils.ArgUtils;
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
            final Parameters parameters = ArgUtils.extractParameters(args, LOGGER);
            if (parameters.isHelpRequire()) {
                LOGGER.info("Help me"); // TODO
                System.exit(0);
            }
            process(args, parameters);
        } catch (final Throwable ex) {
            LOGGER.error(ErrorUtils.getStackTrace(ex));
            System.exit(1);
        }
    }

    private static void process(final String[] args, final Parameters params) {
        final String mask = args[0];
        final List<File> files = FileUtils.getFiles(mask);
        log.info("Files: \"{}\"", files.stream().map(File::getName).collect(Collectors.joining(", ")));

        final List<Highlight> highlights = HighlightCollector.buildHighlights(params.getKeywords());

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
