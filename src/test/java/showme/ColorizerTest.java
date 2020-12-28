package showme;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

class ColorizerTest {

    @Test
    void shouldReturnNothingIfFileIsEmpty() {
        // given
        final var file = CommonUtils.readResourceFile("empty.txt");

        // when
        final var strings = Colorizer.colorizeLines(file, Collections.emptyList());

        // then
        assertThat(strings).hasSize(0);
    }

    @Test
    void shouldReturnNothingIfNoKeyWords() {
        // given
        final var file = CommonUtils.readResourceFile("file-1.txt");

        // when
        final var strings = Colorizer.colorizeLines(file, Collections.emptyList());

        // then
        assertThat(strings).hasSize(0);
    }

    @Test
    void shouldReturnNothingIfFileDoesNotContainsKeywords() {
        // given
        final var file = CommonUtils.readResourceFile("file-1.txt");
        final List<Keyword> keywords = Collections.singletonList(new Keyword("ten", Color.CYAN));

        // when
        final var strings = Colorizer.colorizeLines(file, keywords);

        // then
        assertThat(strings).hasSize(0);
    }

    @Test
    void shouldReturnColorizedIfFileContainsKeyword() {
        // given
        final var file = CommonUtils.readResourceFile("file-1.txt");
        final List<Keyword> keywords = Collections.singletonList(new Keyword("one", Color.CYAN));

        // when
        final var strings = Colorizer.colorizeLines(file, keywords);

        // then
        assertThat(strings).hasSize(1);
        final var string = strings.get(0);
        assertThat(string).isNotNull();
        assertThat(string).isEqualTo("\u001B[0;36mone\u001B[0m");
    }

    @Test
    void shouldReturnColorizedIfFileContainsKeywords() {
        // given
        final var file = CommonUtils.readResourceFile("file-2.txt");
        final List<Keyword> keywords = newArrayList(
                new Keyword("one", Color.CYAN),
                new Keyword("three", Color.PURPLE)
        );

        // when
        final var strings = Colorizer.colorizeLines(file, keywords);

        // then
        assertThat(strings).hasSize(2);

        final var string1 = strings.get(0);
        assertThat(string1).isNotNull();
        assertThat(string1).isEqualTo("\u001B[0;36mone\u001B[0m two \u001B[0;35mthree\u001B[0m");

        final var string2 = strings.get(1);
        assertThat(string2).isNotNull();
        assertThat(string2).isEqualTo("\u001B[0;35mthree\u001B[0m four");
    }
}
