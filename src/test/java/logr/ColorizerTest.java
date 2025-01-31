package logr;

import logr.model.Color;
import logr.model.LineFragment;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ColorizerTest {

    @Test
    void shouldReturnEmptyString() {
        // given
        final List<LineFragment> fragments = Collections.emptyList();

        // when
        final var actual = Colorizer.buildColorizedString(fragments);

        // then
        assertThat(actual).isEqualTo(StringUtils.EMPTY);
    }

    @Test
    void shouldReturnColorized() {
        // given
        final List<LineFragment> fragments = new ArrayList<>();
        fragments.add(LineFragment.of("one two").with(Color.CYAN));

        // when
        final var actual = Colorizer.buildColorizedString(fragments);

        // then
        assertThat(actual).isEqualTo("\u001B[1;36mone two\u001B[0m");
    }

    @Test
    void shouldReturnColorized1() {
        // given
        final List<LineFragment> fragments = new ArrayList<>();
        fragments.add(LineFragment.of("one two").with(Color.CYAN));
        fragments.add(LineFragment.of("  three  "));
        fragments.add(LineFragment.of("four").with(Color.GREEN));

        // when
        final var actual = Colorizer.buildColorizedString(fragments);

        // then
        assertThat(actual).isEqualTo("\u001B[1;36mone two\u001B[0m  three  \u001B[1;32mfour\u001B[0m");
    }

    @Test
    void shouldReturnOriginalString() {
        // given
        final List<LineFragment> fragments = new ArrayList<>();
        fragments.add(LineFragment.of("  three  "));

        // when
        final var actual = Colorizer.buildColorizedString(fragments);

        // then
        assertThat(actual).isEqualTo("  three  ");
    }
}
