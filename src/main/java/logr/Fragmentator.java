package logr;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Fragmentator {

    public static List<LineFragment> process(final String line, final List<Highlight> highlights) {
        if (CollectionUtils.isEmpty(highlights)) {
            return Collections.emptyList();
        }
        // line should contain all included
        if (highlights.stream().anyMatch(highlight -> !highlight.isExcluded() && !line.contains(highlight.getKeyword()))) {
            return Collections.emptyList();
        }
        // line should not contain excluded
        if (highlights.stream().anyMatch(highlight -> highlight.isExcluded() && line.contains(highlight.getKeyword()))) {
            return Collections.emptyList();
        }

        List<LineFragment> fragments = Collections.singletonList(LineFragment.of(line));
        for (final Highlight highlight : highlights) {
            fragments = fragments.stream()
                    .map(fragment -> {
                        final String keyword = highlight.getKeyword();
                        final Color color = highlight.getColor();

                        final String fragmentText = fragment.getText();
                        final Color fragmentColor = fragment.getColor();
                        if (fragmentColor != null) {
                            return Collections.singletonList(fragment);
                        }

                        if (!fragmentText.contains(keyword)) {
                            return Collections.singletonList(fragment);
                        }

                        final List<LineFragment> subFragments = new ArrayList<>();
                        final String[] subParts = fragmentText.split(keyword);
                        int n = 0;
                        for (final String subpart : subParts) {
                            subFragments.add(LineFragment.of(subpart));
                            if (n++ < subParts.length - 1) {
                                subFragments.add(LineFragment.of(keyword).with(color));
                            }
                        }
                        if (fragmentText.endsWith(keyword)) {
                            subFragments.add(LineFragment.of(keyword).with(color));
                        }

                        return subFragments;
                    }).flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        return fragments.stream()
                .filter(frag -> !StringUtils.EMPTY.equals(frag.getText()))
                .collect(Collectors.toList());
    }
}
