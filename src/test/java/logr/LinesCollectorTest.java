package logr;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

class LinesCollectorTest {

    @Test
    void shouldIgnoreStringsWithoutKeywords() {
        // given
        final var files = Collections.singletonList(CommonUtils.readResourceFile("file-2.txt"));
        final var highlights = newArrayList(new Highlight("two", Color.YELLOW));

        // when
        final var list = LinesCollector.collect(files, highlights);

        // then
        assertThat(list).hasSize(1);

        final var fl1 = list.get(0);

        final var lines = fl1.getLines();
        assertThat(lines).hasSize(3);

        final var row1 = lines.get(0);
        assertThat(row1.getOriginalRowNumber()).isEqualTo(1);
        final var fragments1 = row1.getFragments();
        assertThat(fragments1).hasSize(3);
        assertThat(fragments1.get(0)).isEqualTo(LineFragment.of("one "));
        assertThat(fragments1.get(1)).isEqualTo(LineFragment.of("two").with(Color.YELLOW));
        assertThat(fragments1.get(2)).isEqualTo(LineFragment.of(" three"));

        final var row2 = lines.get(1);
        assertThat(row2.getOriginalRowNumber()).isEqualTo(2);
        final var fragments2 = row2.getFragments();
        assertThat(fragments2).hasSize(2);
        assertThat(fragments2.get(0)).isEqualTo(LineFragment.of("two").with(Color.YELLOW));
        assertThat(fragments2.get(1)).isEqualTo(LineFragment.of(" five"));

        final var row3 = lines.get(2);
        assertThat(row3.getOriginalRowNumber()).isEqualTo(3);
        final var fragments3 = row3.getFragments();
        assertThat(fragments3).hasSize(1);
        assertThat(fragments3.get(0)).isEqualTo(LineFragment.of("three four"));
    }

    @Test
    void shouldIgnoreExcludedStrings() {
        // given
        final var files = Collections.singletonList(CommonUtils.readResourceFile("file-4.txt"));
        final var highlights = newArrayList(new Highlight("two", Color.YELLOW), new Highlight("one", Color.YELLOW, true));

        // when
        final var list = LinesCollector.collect(files, highlights);

        // then
        assertThat(list).hasSize(1);

        final var fl = list.get(0);

        final var lines = fl.getLines();
        assertThat(lines).hasSize(1);

        final var row = lines.get(0);
        assertThat(row.getOriginalRowNumber()).isEqualTo(3);
        final var fragments = row.getFragments();
        assertThat(fragments).hasSize(2);
        assertThat(fragments.get(0)).isEqualTo(LineFragment.of("two").with(Color.YELLOW));
        assertThat(fragments.get(1)).isEqualTo(LineFragment.of(" three"));
    }
}
