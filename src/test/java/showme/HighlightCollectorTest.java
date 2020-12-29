package showme;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static showme.Color.PURPLE_BOLD_BRIGHT;

class HighlightCollectorTest {

    @Test
    void shouldThrowExceptionIfArgsLenIsZero() {
        // given
        final String[] args = new String[]{};

        // when
        assertThrows(IllegalArgumentException.class, () -> HighlightCollector.buildHighlights(args));

        // then
    }

    @Test
    void shouldThrowExceptionIfArgsLenIsOne() {
        // given
        final String[] args = new String[]{"./"};

        // when
        assertThrows(IllegalArgumentException.class, () -> HighlightCollector.buildHighlights(args));

        // then
    }

    @Test
    void shouldConvertIncluded() {
        // given
        final String[] args = new String[]{"./", "keyword"};

        // when
        final var highlights = HighlightCollector.buildHighlights(args);

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
        final String[] args = new String[]{"./", "!keyword"};

        // when
        final var highlights = HighlightCollector.buildHighlights(args);

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
    void shouldConvertIncludedAndExcluded() {
        // given
        final String[] args = new String[]{"./", "include", "!exclude"};

        // when
        final var highlights = HighlightCollector.buildHighlights(args);

        // then
        assertThat(highlights).hasSize(2);
    }

    @Test
    void shouldThrowExceptionIfIncludedHAsTheSameKeywordAsExcluded() {
        // given
        final String[] args = new String[]{"./", "keyword", "!keyword"};

        // when
        assertThrows(IllegalArgumentException.class, () -> HighlightCollector.buildHighlights(args));

        // then
    }
}
