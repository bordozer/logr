package showme;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class App {

    @SneakyThrows
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            Logger.error("Define file mask");
            System.exit(1);
        }
        final var words = Arrays.asList(Arrays.copyOfRange(args, 1, args.length));
        final List<Keyword> keywords = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            keywords.add(new Keyword(words.get(i), Color.values()[i]));
        }

        final var mask = args[0];
        final var files = FileUtils.getFiles(mask);
        final List<FileLine> list = files.stream()
                .map(file -> getCollect(file, keywords))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        list.forEach(FileLineLogger::log);
    }

    @SneakyThrows
    private static List<FileLine> getCollect(final File file, final List<Keyword> keywords) {
        return Files.lines(Path.of(file.toURI()))
                .map(line -> {
                    var coloredLine = line;
                    for (int i = 0; i < keywords.size(); i++) {
                        final var keyword = keywords.get(i);
                        final var coloredKeywordText = String.format("%s%s%s", keyword.getColor().getValue(), keyword.getText(), Logger.RESET);
                        coloredLine = coloredLine.replace(keyword.getText(), coloredKeywordText);
                    }
                    final List<FileLineFragment> fragments = new ArrayList<>();
                    fragments.add(FileLineFragment.builder()
                            .text(line)
                            .color(Color.PURPLE)
                            .build());
                    return new FileLine(fragments);
                })
                .collect(Collectors.toList());
    }
}
