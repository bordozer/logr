package showme;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class App {

    @SneakyThrows
    public static void main(String[] args) {
//        log.info("args: {}", JsonUtils.toJson(args));
        if (args == null || args.length == 0) {
            Logger.error("Define file mask");
            System.exit(1);
        }

        final var mask = args[0];
//        log.info("mask: {}", mask);
        final var files = FileUtils.getFiles(mask);
        Logger.system(String.format("Found %s file(s)", files.size()));

        final var highlights = buildHighlights(args);
        final var colorizedLines = LinesCollector.collect(files, highlights);
        colorizedLines.forEach(pair -> Logger.info(String.format("%s%s%s %s", Logger.GREEN_BOLD_BRIGHT, pair.getKey(), Logger.RESET, pair.getValue())));

        Logger.system(String.format("Total: %s line(s)", colorizedLines.size()));
    }

    private static List<Highlight> buildHighlights(final String[] args) {
        final var words = Arrays.asList(Arrays.copyOfRange(args, 1, args.length));
        if (CollectionUtils.isEmpty(words)) {
            throw new IllegalArgumentException("Please, define at least one keyword");
        }
        final var counter = new AtomicInteger();
        final List<Highlight> highlights = new ArrayList<>();
        for (final String word : words) {
            final var isExcluded = word.startsWith("!");
            @CheckForNull final var color = isExcluded ? null : Color.values()[counter.getAndIncrement()];
            highlights.add(new Highlight(getWord(word, isExcluded), color, isExcluded));
        }
//        log.info("highlights: {}", JsonUtils.toJson(highlights));
        return highlights;
    }

    private static String getWord(final String word, final boolean isExcluded) {
        if (isExcluded) {
            return word.substring(1);
        }
        return word;
    }
}
