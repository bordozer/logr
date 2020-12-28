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
    }
}
