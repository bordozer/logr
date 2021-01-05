package showme;

import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Colorizer {

    @CheckForNull
    public static String parseLine(final String line, final List<Highlight> highlights) {
        if (CollectionUtils.isEmpty(highlights)) {
            return null;
        }
        // line should contains all included
        if (highlights.stream().anyMatch(highlight -> !highlight.isExcluded() && !line.contains(highlight.getKeyword()))) {
            return null;
        }
        // line should not contain excluded
        if (highlights.stream().anyMatch(highlight -> highlight.isExcluded() && line.contains(highlight.getKeyword()))) {
            return null;
        }

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
