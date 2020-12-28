package showme;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public final class Colorizer {

    @SneakyThrows
    public static List<String> colorizeLines(final File file, final List<Highlight> highlights) {
        return Files.lines(Path.of(file.toURI()))
                .map(line -> parseLine(highlights, line))
                .map(Colorizer::buildColorizedLine)
                .collect(Collectors.toList());
    }

    private static List<LineFragment> parseLine(final List<Highlight> highlights, final String line) {
        var index = 0;
        List<LineFragment> fragments = newArrayList(LineFragment.of(line));
        for (final Highlight highlight : highlights) {
            fragments = fragments.stream()
                    .map(fragment -> {
                        final var keyword = highlight.getKeyword();
                        final var fragmentText = fragment.getText();

                        if (!fragmentText.contains(keyword)) {
                            return Collections.singletonList(fragment);
                        }

                        final List<LineFragment> subFragments = newArrayList();
                        final String[] subparts = fragmentText.split(keyword);
                        for (final String subpart : subparts) {
                            subFragments.add(LineFragment.of(subpart));
                            subFragments.add(LineFragment.of(keyword).with(Color.values()[index]));
                        }

                        return subFragments;
                    }).flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        return fragments;
    }

    private static String buildColorizedLine(final List<LineFragment> fragments) {
        return fragments.stream()
                .map(fragment -> {
                    if (fragment.getColor() == null) {
                        return fragment.getText();
                    }
                    return String.format("%s%s%s", fragment.getColor(), fragment.getText(), Logger.RESET);
                })
                .collect(Collectors.joining());
    }
}
