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
        final var text = "one two three four one two three";
        final var keyword = "one .* three";

        // when
        final var strings = RegexUtils.split(text, keyword);

        // then
        assertThat(strings).hasSize(1);
        assertThat(strings.get(0)).isEqualTo(" four ");
    }
}
