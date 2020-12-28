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
        if (args == null || args.length == 0) {
            Logger.error("Define file mask");
            System.exit(1);
        }

        final var mask = args[0];
        final var files = FileUtils.getFiles(mask);

        final var highlights = buildHighlights(args);
        LinesCollector.collect(files, highlights)
                .forEach(Logger::info);
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
