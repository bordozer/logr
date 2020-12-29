package showme;

import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public final class HighlightCollector {

    public static List<Highlight> buildHighlights(final String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Please, define at least one keyword");
        }
        final var words = Arrays.copyOfRange(args, 1, args.length);
        final var counter = new AtomicInteger();
        final List<Highlight> highlights = new ArrayList<>();
        for (final String word : words) {
            final var isExcluded = word.startsWith("!");
            @CheckForNull final var color = isExcluded ? null : Color.values()[counter.getAndIncrement()];
            highlights.add(new Highlight(getWord(word, isExcluded), color, isExcluded));
        }
        //        log.info("highlights: {}", JsonUtils.toJson(highlights));
        final var included = highlights.stream()
                .filter(Highlight::isExcluded)
                .collect(Collectors.toList());
        final var excluded = highlights.stream()
                .filter(h -> !h.isExcluded())
                .collect(Collectors.toList());

        final var hasTheSameElement = included.stream()
                .map(Highlight::getKeyword)
                .anyMatch(
                        excluded.stream()
                                .map(Highlight::getKeyword)
                                .collect(toSet())
                                ::contains);
        if (hasTheSameElement) {
            throw new IllegalArgumentException("Please, define at least one keyword");
        }
        return highlights;
    }

    private static String getWord(final String word, final boolean isExcluded) {
        if (isExcluded) {
            return word.substring(1);
        }
        return word;
    }
}
