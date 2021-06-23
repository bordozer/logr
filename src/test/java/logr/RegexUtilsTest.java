package logr;

import logr.RegexUtils.Part;
import org.junit.jupiter.api.DisplayName;
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
                        new Part[]{
                                Part.text("one "),
                                Part.keyword("two"),
                                Part.text(" three")
                        }
                ),
                Arguments.of(
                        "0 aac 1 abc 2",
                        "a.*c",
                        new Part[]{
                                Part.text("0 "),
                                Part.keyword("aac"),
                                Part.text(" 1 "),
                                Part.keyword("abc"),
                                Part.text(" 2")
                        }
                ),
                Arguments.of(
                        "aac 1 abc 2",
                        "a.*c",
                        new Part[]{
                                Part.keyword("aac"),
                                Part.text(" 1 "),
                                Part.keyword("abc"),
                                Part.text(" 2"),
                        }
                ),
                Arguments.of(
                        "aac 1 abc",
                        "a.*c",
                        new Part[]{
                                Part.keyword("aac"),
                                Part.text(" 1 "),
                                Part.keyword("abc")
                        }
                ),
                Arguments.of(
                        "aac 1 abc",
                        "a.?c",
                        new Part[]{
                                Part.keyword("aac"),
                                Part.text(" 1 "),
                                Part.keyword("abc")
                        }
                ),
                Arguments.of(
                        "aac 1 ac",
                        "a.?c",
                        new Part[]{
                                Part.keyword("aac"),
                                Part.text(" 1 "),
                                Part.keyword("ac")
                        }
                ),
                Arguments.of(
                        "aac 1 abdc",
                        "a.?c",
                        new Part[]{
                                Part.keyword("aac"),
                                Part.text(" 1 abdc")
                        }
                ),
                Arguments.of(
                        "aac 1 abc",
                        "a.*d",
                        new Part[]{
                                Part.text("aac 1 abc")
                        }
                )
        );
    }

    @DisplayName("Should split line")
    @ParameterizedTest
    @MethodSource("dataSupplier")
    void shouldReturnColorized(final String text, final String keyword, final Part[] expected) {
        // given

        // when
        final var strings = RegexUtils.split(text, keyword);

        // then
        assertThat(strings).containsExactly(expected);
    }
}
