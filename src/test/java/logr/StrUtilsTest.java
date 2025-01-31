package logr;

import logr.utils.StrUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StrUtilsTest {

    static Stream<Arguments> dataSupplier() {
        return Stream.of(
                Arguments.of("1", "1", "1"),
                Arguments.of("1", "10", ".1"),
                Arguments.of("1", "100", "..1"),
                Arguments.of("50", "100", ".50"),
                Arguments.of("100", "100", "100"),
                Arguments.of("100", "9999999", "....100")
        );
    }

    @DisplayName("Should colorize line")
    @ParameterizedTest
    @MethodSource("dataSupplier")
    void shouldReturnColorized(final Integer number, final Integer max, final String expected) {
        // given

        // when
        final String actual = StrUtils.formatRowNumber(number, max);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> dataSupplier1() {
        return Stream.of(
                Arguments.of("text", "text", false, List.of()),
                Arguments.of("text", "e", false, List.of("t", "xt")),
                Arguments.of("text", "E", false, List.of("t", "xt")),
                Arguments.of("text is TEXT", "E", false, List.of("t", "xt is T", "XT")),
                Arguments.of("text is TEXT", "E", true, List.of("text is T", "XT"))
        );
    }

    @DisplayName("Should split line")
    @ParameterizedTest
    @MethodSource("dataSupplier1")
    void shouldSplitLine(final String text, final String splitter, final boolean isCase, final List<String> expected) {
        // given

        // when
        final List<String> actual = StrUtils.splitIgnoreCase(text, splitter, isCase, false).getKey();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
