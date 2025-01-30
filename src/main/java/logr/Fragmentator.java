package logr;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static logr.StrUtils.endsWith;
import static logr.StrUtils.isContains;
import static logr.StrUtils.splitIgnoreCase;

public final class Fragmentator {

    public static List<LineFragment> process(final String line, final List<Highlight> highlights, final Parameters parameters) {
        if (CollectionUtils.isEmpty(highlights)) {
            return Collections.emptyList();
        }
        final boolean caseSensitive = parameters.isCaseSensitive();
        // line should contain all included
        if (highlights.stream().anyMatch(highlight -> !highlight.isExcluded() && !isContains(line, highlight.getKeyword(), caseSensitive))) {
            return Collections.emptyList();
        }
        // line should not contain excluded
        if (highlights.stream().anyMatch(highlight -> highlight.isExcluded() && isContains(line, highlight.getKeyword(), caseSensitive))) {
            return Collections.emptyList();
        }

        List<LineFragment> fragments = Collections.singletonList(LineFragment.of(line));
        for (final Highlight highlight : highlights) {
            fragments = fragments.stream()
                    .map(fragment -> {
                        final String fragmentText = fragment.getText();
                        @Nullable final Color fragmentColor = fragment.getColor();
                        if (fragmentColor != null) {
                            return Collections.singletonList(fragment);
                        }

                        final String keyword = highlight.getKeyword();
                        if (!isContains(fragmentText, keyword, caseSensitive)) {
                            return Collections.singletonList(fragment);
                        }

                        @Nullable final Color color = highlight.getColor();
                        final List<LineFragment> subFragments = new ArrayList<>();
                        final String[] subParts = splitIgnoreCase(fragmentText, keyword, caseSensitive);
                        if (subParts.length == 0) {
                            return Collections.singletonList(LineFragment.of(fragmentText).with(color));
                        }
                        if (subParts.length == 1 && subParts[0].equals(fragmentText)) {
                            return null;
                        }
                        int n = 0;
                        for (final String subpart : subParts) {
                            subFragments.add(LineFragment.of(subpart));
                            if (n++ < subParts.length - 1) {
                                subFragments.add(LineFragment.of(keyword).with(color));
                            }
                        }
                        if (endsWith(fragmentText, keyword, caseSensitive)) {
                            subFragments.add(LineFragment.of(keyword).with(color));
                        }

                        return subFragments;
                    })
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        return fragments.stream()
                .filter(frag -> StringUtils.isNotEmpty(frag.getText()))
                .collect(Collectors.toList());
    }
}
