package logr;

import logr.model.Highlight;
import org.junit.jupiter.api.Test;

import java.util.List;

import static logr.model.Color.PURPLE_BOLD_BRIGHT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HighlightCollectorTest {

    /*@Test
    void shouldThrowExceptionIfArgsLenIsZero() {
        // given
        final String[] args = new String[]{};

        // when
        assertThrows(IllegalArgumentException.class, () -> HighlightCollector.buildHighlights(args));

        // then
    }*/

    /*@Test
    void shouldThrowExceptionIfArgsLenThenOne() {
        // given
        final String[] args = new String[]{"./"};

        // when
        assertThrows(IllegalArgumentException.class, () -> HighlightCollector.buildHighlights(args));

        // then
    }*/

    @Test
    void shouldCollectIncluded() {
        // given
        final List<String> args = List.of("keyword");

        // when
        final List<Highlight> highlights = HighlightCollector.buildHighlights(args);

        // then
        assertThat(highlights)
                .hasSize(1)
                .first()
                .satisfies(highlight -> {
                    assertThat(highlight.getKeyword()).isEqualTo("keyword");
                    assertThat(highlight.getColor()).isEqualTo(PURPLE_BOLD_BRIGHT);
                    assertThat(highlight.isExcluded()).isEqualTo(false);
                });
    }

    @Test
    void shouldConvertExcluded() {
        // given
        final List<String> params = List.of("!keyword");

        // when
        final List<Highlight> highlights = HighlightCollector.buildHighlights(params);

        // then
        assertThat(highlights)
                .hasSize(1)
                .first()
                .satisfies(highlight -> {
                    assertThat(highlight.getKeyword()).isEqualTo("keyword");
                    assertThat(highlight.getColor()).isNull();
                    assertThat(highlight.isExcluded()).isEqualTo(true);
                });
    }

    @Test
    void shouldCollectIncludedAndExcluded() {
        // given
        final List<String> params = List.of("include", "!exclude");

        // when
        final List<Highlight> highlights = HighlightCollector.buildHighlights(params);

        // then
        assertThat(highlights).hasSize(2);
    }

    @Test
    void shouldThrowExceptionIfIncludedHAsTheSameKeywordAsExcluded() {
        // given
        final List<String> params = List.of("keyword", "!keyword");

        // when
        assertThrows(IllegalArgumentException.class, () -> HighlightCollector.buildHighlights(params));

        // then
    }
}
