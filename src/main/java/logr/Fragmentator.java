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
        // line should contains all included
        /*if (highlights.stream().anyMatch(highlight -> !highlight.isExcluded() && !Pattern.compile(highlight.getKeyword()).matcher(line).matches())) {
            return Collections.emptyList();
        }*/
        // line should not contain excluded
        if (highlights.stream().anyMatch(highlight -> highlight.isExcluded() && line.contains(highlight.getKeyword()))) {
            return Collections.emptyList();
        }

        /*for (final Highlight highlight : highlights) {
            final var keyword = highlight.getKeyword();
            final var color = highlight.getColor();

            final var parts = RegexUtils.split(line, keyword);
            return parts.stream()
                    .map(part -> part.isKeyword() ? LineFragment.of(part.getText()).with(color) : LineFragment.of(part.getText()))
                    .collect(Collectors.toList());
        }*/

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

                        final var parts = RegexUtils.split(fragmentText, keyword);
                        return parts.stream()
                                .map(part -> part.isKeyword() ? LineFragment.of(part.getText()).with(color) : LineFragment.of(part.getText()))
                                .collect(Collectors.toList());

                        /*
                        if (!Pattern.compile(keyword).matcher(fragmentText).matches()) {
                            return Collections.singletonList(fragment);
                        }

                        final List<LineFragment> subFragments = new ArrayList<>();
                        final String[] subParts = RegexUtils.split(fragmentText, keyword).toArray(new String[0]);
                        int n = 0;
                        for (final String subpart : subParts) {
                            subFragments.add(LineFragment.of(subpart));
                            if (n++ < subParts.length - 1) {
                                subFragments.add(LineFragment.of(keyword).with(color));
                            }
                        }
                        if (Pattern.compile(keyword).matcher(fragmentText).matches()) {
                            subFragments.add(LineFragment.of(fragmentText).with(color));
                        }

                        return subFragments;*/
                    }).flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }
        return fragments.stream()
                .filter(frag -> !StringUtils.EMPTY.equals(frag.getText()))
                .collect(Collectors.toList());
    }
}
