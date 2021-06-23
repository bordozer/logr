package logr;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegexUtilsTest {

    @Test
    void shouldSplitByKeyword() {
        // given
        final var text = "one two three";
        final var keyword = "two";

        // when
        final var strings = RegexUtils.split(text, keyword);

        // then
        assertThat(strings).hasSize(2);
        assertThat(strings.get(0)).isEqualTo("one ");
        assertThat(strings.get(1)).isEqualTo(" three");
    }

    @Test
    void shouldSplitByPattern1() {
        // given
        final var text = "one two three";
        final var keyword = "one .* three";

        // when
        final var strings = RegexUtils.split(text, keyword);

        // then
        assertThat(strings).hasSize(1);
        assertThat(strings.get(0)).isEqualTo("one two three");
    }

    @Test
    void shouldSplitByPattern2() {
        // given
        final var text = "0 aac 1 abc 2";
        final var keyword = "a.*c";

        // when
        final var strings = RegexUtils.split(text, keyword);

        // then
        assertThat(strings).hasSize(3);
        assertThat(strings.get(0)).isEqualTo("0 ");
        assertThat(strings.get(1)).isEqualTo(" 1 ");
        assertThat(strings.get(2)).isEqualTo(" 2");
    }
}
