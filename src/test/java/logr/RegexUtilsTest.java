package logr;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RegexUtilsTest {

    static Stream<Arguments> dataSupplier() {
        return Stream.of(
                Arguments.of(
                        "one two three",
                        "two",
                        new String[]{"one ", " three"}
                ),
                Arguments.of(
                        "0 aac 1 abc 2",
                        "a.*c",
                        new String[]{"0 ", " 1 ", " 2"}
                )
        );
    }

    @DisplayName("Should split line")
    @ParameterizedTest
    @MethodSource("dataSupplier")
    void shouldReturnColorized(final String text, final String keyword, final String[] expected) {
        // given

        // when
        final var strings = RegexUtils.split(text, keyword);

        // then
        strings.forEach(string -> assertThat(string).isEqualTo(expected[strings.indexOf(string)]));
    }
}
