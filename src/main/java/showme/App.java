package showme;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        colorizedLines.forEach(Logger::info);

        Logger.system(String.format("Total: %s line(s)", colorizedLines.size()));
    }

    private static List<Highlight> buildHighlights(final String[] args) {
        final var words = Arrays.asList(Arrays.copyOfRange(args, 1, args.length));
        final List<Highlight> highlights = new ArrayList<>();
        for (var i = 0; i < words.size(); i++) {
            highlights.add(new Highlight(words.get(i), Color.values()[i]));
        }
        return highlights;
    }
}
