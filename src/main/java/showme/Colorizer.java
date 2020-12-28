package showme;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Colorizer {

    @SneakyThrows
    public static List<String> colorizeLines(final File file, final List<Highlight> highlights) {
        return Files.lines(Path.of(file.toURI()))
                .map(line -> parseLine(highlights, line))
                .map(Colorizer::buildColorizedLine)
                .collect(Collectors.toList());
    }

    private static List<LineFragment> parseLine(final List<Highlight> highlights, final String line) {
        List<LineFragment> fragments = Collections.singletonList(LineFragment.of(line));
        for (final Highlight highlight : highlights) {
            fragments = fragments.stream()
                    .map(fragment -> {
                        final var keyword = highlight.getKeyword();
                        final var color = highlight.getColor();
                        final var fragmentText = fragment.getText();
                        final var fragmentColor = fragment.getColor();
                        if (fragmentColor != null) {
                            return Collections.singletonList(fragment);
                        }

                        if (!fragmentText.contains(keyword)) {
                            return Collections.singletonList(fragment);
                        }

                        final List<LineFragment> subFragments = new ArrayList<>();
                        final String[] subparts = fragmentText.split(keyword);
                        int n = 0;
                        for (final String subpart : subparts) {
                            subFragments.add(LineFragment.of(subpart));
                            if (n++ < subparts.length - 1) {
                                subFragments.add(LineFragment.of(keyword).with(color));
                            }
                        }
                        if (fragmentText.substring(fragmentText.length() - keyword.length()).equals(keyword)) {
                            subFragments.add(LineFragment.of(keyword).with(color));
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
                    return String.format("%s%s%s", fragment.getColor().getValue(), fragment.getText(), Logger.RESET);
                })
                .collect(Collectors.joining());
    }
}
