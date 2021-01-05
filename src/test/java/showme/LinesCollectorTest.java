package showme;

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
        assertThat(lines).hasSize(2);

        final var pair1 = lines.get(0);
        assertThat(pair1.getKey()).isEqualTo(1);
        assertThat(pair1.getValue()).isEqualTo("one \u001B[1;33mtwo\u001B[0m three");

        final var pair2 = lines.get(1);
        assertThat(pair2.getKey()).isEqualTo(2);
        assertThat(pair2.getValue()).isEqualTo("\u001B[1;33mtwo\u001B[0m five");
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

        final var pair = lines.get(0);
        assertThat(pair.getKey()).isEqualTo(3);
        assertThat(pair.getValue()).isEqualTo("\u001B[1;33mtwo\u001B[0m three");
    }
}
