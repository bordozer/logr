package logr;

import javax.annotation.CheckForNull;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public final class HighlightCollector {

    public static final AtomicInteger COUNTER = new AtomicInteger();

    public static List<Highlight> buildHighlights(final List<String> keywords) {
        COUNTER.set(0);
        final List<Highlight> highlights = keywords.stream()
                .map(HighlightCollector::getHighlight)
                .collect(Collectors.toList());
        validate(highlights);
        return highlights;
    }

    private static Highlight getHighlight(final String parameter) {
        final boolean isExcluded = parameter.startsWith("!");
        @CheckForNull final Color color = isExcluded ? null : Color.values()[COUNTER.getAndIncrement()];
        return new Highlight(getKeyword(parameter, isExcluded), isExcluded, color);
    }

    private static void validate(final List<Highlight> highlights) {
        final List<Highlight> included = highlights.stream()
                .filter(Highlight::isExcluded)
                .collect(Collectors.toList());
        final List<Highlight> excluded = highlights.stream()
                .filter(h -> !h.isExcluded())
                .collect(Collectors.toList());

        final boolean hasTheSameElement = included.stream()
                .map(Highlight::getKeyword)
                .anyMatch(excluded.stream()
                        .map(Highlight::getKeyword)
                        .collect(toSet())
                        ::contains);
        if (hasTheSameElement) {
            throw new IllegalArgumentException("Included contains the same keyword as excluded");
        }
    }

    private static String getKeyword(final String word, final boolean isExcluded) {
        if (isExcluded) {
            return word.substring(1);
        }
        return word;
    }
}
