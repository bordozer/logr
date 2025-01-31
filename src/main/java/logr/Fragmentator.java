package logr;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static logr.utils.StrUtils.endsWith;
import static logr.utils.StrUtils.isContains;
import static logr.utils.StrUtils.splitIgnoreCase;

public final class Fragmentator {

    public static List<LineFragment> process(final String line, final List<Highlight> highlights, final Parameters parameters) {
        if (CollectionUtils.isEmpty(highlights)) {
            return Collections.emptyList();
        }
        final boolean caseSensitive = parameters.isCaseSensitive();
        final boolean isWordsOnly = parameters.isWordsOnly();
        // line should contain all included
        if (highlights.stream().anyMatch(highlight -> !highlight.isExcluded() && !isContains(line, highlight.getKeyword(), caseSensitive, isWordsOnly))) {
            return Collections.emptyList();
        }
        // line should not contain excluded
        if (highlights.stream().anyMatch(highlight -> highlight.isExcluded() && isContains(line, highlight.getKeyword(), caseSensitive, isWordsOnly))) {
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
                        if (!isContains(fragmentText, keyword, caseSensitive, isWordsOnly)) {
                            return Collections.singletonList(fragment);
                        }

                        @Nullable final Color color = highlight.getColor();
                        final List<LineFragment> subFragments = new ArrayList<>();
                        final Pair<List<String>, List<String>> listListPair = splitIgnoreCase(fragmentText, keyword, caseSensitive, isWordsOnly);
                        final List<String> subParts = listListPair.getKey();
                        final List<String> keywords = listListPair.getValue();
                        if (subParts.isEmpty()) {
                            return Collections.singletonList(LineFragment.of(fragmentText).with(color));
                        }
                        if (subParts.size() == 1 && subParts.get(0).equals(fragmentText)) {
                            return null;
                        }
                        int n = 0;
                        for (int i = 0; i < subParts.size(); i++) {
                            final String subpart = subParts.get(i);
                            subFragments.add(LineFragment.of(subpart));
                            if (n++ < subParts.size() - 1) {
                                subFragments.add(LineFragment.of(keywords.get(i)).with(color));
                            }
                        }
                        if (endsWith(fragmentText, keyword, caseSensitive, isWordsOnly)) {
                            subFragments.add(LineFragment.of(keywords.get(keywords.size() - 1)).with(color));
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
