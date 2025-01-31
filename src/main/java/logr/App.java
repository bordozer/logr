package logr;

import logr.utils.AppUtils;
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
                LOGGER.info("Help:");
                LOGGER.info("Keyword must have at least three characters");
                LOGGER.info("Subfolders are ignored");
                LOGGER.info("To exclude a keyword from search add \\! before it");
                LOGGER.info("Search in file.txt for keyword 'text': \u001B[38;5;218mlogr file.txt text\u001B[0m");
                LOGGER.info("Search in all files in the directory for keyword 'text': \u001B[38;5;218mlogr ./ text\u001B[0m");
                LOGGER.info("Search in all files in the directory for keyword 'text with space': \u001B[38;5;218mlogr ./ \"text with space\"\u001B[0m");
                LOGGER.info("Search in file.txt for keyword 'text1' and 'text2': \u001B[38;5;218mlogr file.txt text1 text2\u001B[0m");
                LOGGER.info("Search in all files in the directory for keyword 'text' but if a row does not contain sibstring 'exluded': \u001B[38;5;218mlogr ./ text \\!exluded\u001B[0m");
                LOGGER.info("Search in all files in the directory for keyword 'text' but if a row does not contain sibstrings 'exluded1' and 'exluded2': \u001B[38;5;218mlogr ./ text \\!exluded1 \\!exluded2\u001B[0m");
                LOGGER.info("Search in all files in the directory if a row does not contain sibstrings 'exluded1' and 'exluded2': \u001B[38;5;218mlogr ./ \\!exluded1 text \\!exluded2\u001B[0m");


                LOGGER.info("Search in file.txt for keyword 'text' with case respect: \u001B[38;5;218mlogr file.txt c text\u001B[0m");
                LOGGER.info("Search in file.txt for word 'text': \u001B[38;5;218mlogr file.txt w text\u001B[0m");
                LOGGER.info("Search in file.txt for word 'text' with case respect: \u001B[38;5;218mlogr file.txt cw text\u001B[0m");
                System.exit(0);
            }
            if (parameters.isCaseSensitive()) {
                LOGGER.info("\u001B[38;5;218mCase sensitive: true\u001B[0m");
            }
            if (parameters.isWordsOnly()) {
                LOGGER.info("\u001B[38;5;218mWords only: true\u001B[0m");
            }
            process(args, parameters);
        } catch (final Throwable ex) {
            LOGGER.error(ErrorUtils.getStackTrace(ex));
            AppUtils.exitApp(ex.getMessage());
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
