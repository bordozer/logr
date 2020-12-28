package showme;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

class ColorizerTest {

    static Stream<Arguments> dataSupplier() {
        return Stream.of(
                Arguments.of(
                        "",
                        Collections.emptyList(),
                        ""
                ),
                Arguments.of(
                        "one two",
                        Collections.emptyList(),
                        "one two"
                ),
                Arguments.of(
                        "",
                        newArrayList(new Highlight("one two", Color.CYAN), new Highlight("one", Color.PURPLE)),
                        ""
                ),
                Arguments.of(
                        "one",
                        newArrayList(new Highlight("one", Color.CYAN)),
                        "\u001B[1;36mone\u001B[0m"
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one two", Color.CYAN), new Highlight("one", Color.PURPLE)),
                        "\u001B[1;36mone two\u001B[0m \u001B[1;35mone\u001B[0m three two \u001B[1;35mone\u001B[0m \u001B[1;36mone two\u001B[0m"
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one", Color.CYAN)),
                        "\u001B[1;36mone\u001B[0m two \u001B[1;36mone\u001B[0m three two \u001B[1;36mone\u001B[0m \u001B[1;36mone\u001B[0m two"
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one two one", Color.CYAN)),
                        "\u001B[1;36mone two one\u001B[0m three two one one two"
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one", Color.CYAN), new Highlight("two", Color.CYAN), new Highlight("three", Color.CYAN)),
                        "\u001B[1;36mone\u001B[0m \u001B[1;36mtwo\u001B[0m \u001B[1;36mone\u001B[0m \u001B[1;36mthree\u001B[0m \u001B[1;36mtwo\u001B[0m \u001B[1;36mone\u001B[0m \u001B[1;36mone\u001B[0m \u001B[1;36mtwo\u001B[0m"
                )
        );
    }

    @DisplayName("Should colorize line")
    @ParameterizedTest
    @MethodSource("dataSupplier")
    void shouldReturnColorized(final String line, final List<Highlight> highlights, final String expected) {
        // given

        // when
        final var string = Colorizer.parseLine(line, highlights);

        // then
        assertThat(string).isEqualTo(expected);
    }
}
