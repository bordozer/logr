package showme;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

class LinesCollectorTest {

    @Test
    void shouldIgnoreStringWithoutKeywords() {
        // given
        final var files = Collections.singletonList(CommonUtils.readResourceFile("file-2.txt"));
        final var highlights = newArrayList(new Highlight("two", Color.YELLOW));

        // when
        final var list = LinesCollector.collect(files, highlights);

        // then
        assertThat(list).hasSize(2);

        final var string1 = list.get(0);
        assertThat(string1).isEqualTo("one \u001B[1;33mtwo\u001B[0m three");
        final var string2 = list.get(1);
        assertThat(string2).isEqualTo("\u001B[1;33mtwo\u001B[0m five");
    }
}
