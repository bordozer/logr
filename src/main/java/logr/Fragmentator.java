package logr;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Fragmentator {

    public static List<LineFragment> process(final String line, final List<Highlight> highlights) {
        if (CollectionUtils.isEmpty(highlights)) {
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
                        final var keyword = highlight.getKeyword();
                        final var color = highlight.getColor();

                        final var fragmentText = fragment.getText();
                        if (StringUtils.isEmpty(fragmentText)) {
                            return Collections.<LineFragment>emptyList();
                        }

                        final var fragmentColor = fragment.getColor();
                        if (fragmentColor != null) {
                            // this fragment is previous keyword
                            return Collections.singletonList(fragment);
                        }

                        final var parts = RegexUtils.split(fragmentText, keyword);
                        return parts.stream()
                                .map(part -> part.isKeyword() ? LineFragment.of(part.getText()).with(color) : LineFragment.of(part.getText()))
                                .collect(Collectors.toList());
                    }).flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        if (fragments.size() == 1 && fragments.get(0).getText().equals(line) && fragments.get(0).getColor() == null) {
            return Collections.emptyList();
        }
        return fragments;
    }
}
