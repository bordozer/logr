package showme;

import lombok.NonNull;
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
        //        log.info("args: {}", JsonUtils.toJson(args));
        if (args == null || args.length == 0) {
            Logger.error("Define file mask");
            System.exit(1);
        }
        final var keywords = Arrays.copyOfRange(args, 1, args.length);
        //        log.info("keywords: {}", JsonUtils.toJson(keywords));

        final var mask = args[0];
        final var files = FileUtils.getFiles(mask);
        final List<FileLine> list = files.stream()
                .map(App::getCollect)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        list.forEach(FileLineLogger::log);
    }

    @SneakyThrows
    private static List<FileLine> getCollect(final File file) {
        return Files.lines(Path.of(file.toURI()))
                .map(line -> {
//                    log.info("line: {}", line);
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
