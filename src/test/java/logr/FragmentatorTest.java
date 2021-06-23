package logr;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

class FragmentatorTest {

    private static final LineFragment[] EMPTY_LIST = {};

    static Stream<Arguments> dataSupplier() {
        return Stream.of(
                Arguments.of(
                        "",
                        Collections.emptyList(),
                        EMPTY_LIST
                ),
                Arguments.of(
                        "one two",
                        Collections.emptyList(),
                        EMPTY_LIST
                ),
                Arguments.of(
                        "",
                        newArrayList(new Highlight("one two", Color.CYAN), new Highlight("one", Color.PURPLE)),
                        EMPTY_LIST
                ),
                Arguments.of(
                        "one",
                        newArrayList(new Highlight("one", Color.CYAN)),
                        new LineFragment[]{
                                LineFragment.of("one").with(Color.CYAN)
                        }
                ),
                Arguments.of(
                        "one",
                        newArrayList(new Highlight("one", Color.CYAN), new Highlight("two", Color.PURPLE)),
                        EMPTY_LIST
                ),
                Arguments.of(
                        "two",
                        newArrayList(new Highlight("one", Color.CYAN), new Highlight("two", Color.PURPLE)),
                        EMPTY_LIST
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one two", Color.CYAN), new Highlight("one", Color.PURPLE)),
                        new LineFragment[]{
                                LineFragment.of("one two").with(Color.CYAN),
                                LineFragment.of(" "),
                                LineFragment.of("one").with(Color.PURPLE),
                                LineFragment.of(" three two "),
                                LineFragment.of("one").with(Color.PURPLE),
                                LineFragment.of(" "),
                                LineFragment.of("one two").with(Color.CYAN)
                        }
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one", Color.CYAN)),
                        new LineFragment[]{
                                LineFragment.of("one").with(Color.CYAN),
                                LineFragment.of(" two "),
                                LineFragment.of("one").with(Color.CYAN),
                                LineFragment.of(" three two "),
                                LineFragment.of("one").with(Color.CYAN),
                                LineFragment.of(" "),
                                LineFragment.of("one").with(Color.CYAN),
                                LineFragment.of(" two")
                        }
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one two one", Color.CYAN)),
                        new LineFragment[]{
                                LineFragment.of("one two one").with(Color.CYAN),
                                LineFragment.of(" three two one one two")
                        }
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one", Color.CYAN), new Highlight("two", Color.PURPLE), new Highlight("three", Color.BLUE)),
                        new LineFragment[]{
                                LineFragment.of("one").with(Color.CYAN),
                                LineFragment.of(" "),
                                LineFragment.of("two").with(Color.PURPLE),
                                LineFragment.of(" "),
                                LineFragment.of("one").with(Color.CYAN),
                                LineFragment.of(" "),
                                LineFragment.of("three").with(Color.BLUE),
                                LineFragment.of(" "),
                                LineFragment.of("two").with(Color.PURPLE),
                                LineFragment.of(" "),
                                LineFragment.of("one").with(Color.CYAN),
                                LineFragment.of(" "),
                                LineFragment.of("one").with(Color.CYAN),
                                LineFragment.of(" "),
                                LineFragment.of("two").with(Color.PURPLE)
                        }
                ),
                Arguments.of(
                        "one two",
                        newArrayList(new Highlight("one", Color.CYAN), new Highlight("two", Color.CYAN, true)),
                        EMPTY_LIST
                ),
                Arguments.of(
                        "one two",
                        newArrayList(new Highlight("one", Color.CYAN), new Highlight("one", Color.CYAN)),
                        new LineFragment[]{
                                LineFragment.of("one").with(Color.CYAN),
                                LineFragment.of(" two")
                        }
                ),
                Arguments.of(
                        "one",
                        newArrayList(new Highlight("one", Color.CYAN)),
                        new LineFragment[]{
                                LineFragment.of("one").with(Color.CYAN)
                        }
                ),
                Arguments.of(
                        "one",
                        newArrayList(new Highlight("one", Color.CYAN), new Highlight("one", Color.CYAN, true)),
                        EMPTY_LIST
                ),
                Arguments.of(
                        "one_two_three",
                        newArrayList(new Highlight("one_.*_three", Color.CYAN)),
                        new LineFragment[]{
                                LineFragment.of("one_two_three").with(Color.CYAN)
                        }
                ),
                Arguments.of(
                        "one_one_three one_two_three one_three_three",
                        newArrayList(new Highlight("one_.*_three", Color.CYAN)),
                        new LineFragment[]{
                                LineFragment.of("one_one_three").with(Color.CYAN),
                                LineFragment.of(" "),
                                LineFragment.of("one_two_three").with(Color.CYAN),
                                LineFragment.of(" "),
                                LineFragment.of("one_three_three").with(Color.CYAN),
                        }
                ),
                Arguments.of(
                        "URI=\"/position/user/+61490541848/m6xelkqm\", contentType=\"application/json\"",
                        newArrayList(new Highlight("m6xelkqm", Color.CYAN)),
                        new LineFragment[]{
                                LineFragment.of("URI=\"/position/user/+61490541848/"),
                                LineFragment.of("m6xelkqm").with(Color.CYAN),
                                LineFragment.of("\", contentType=\"application/json\""),
                        }
                ),
                Arguments.of(
                        "URI=\"/position/user/+61490541848/m6xelkqm\", contentType=\"application/json\"",
                        newArrayList(new Highlight("/position/user/.*/m6xelkqm", Color.CYAN)),
                        new LineFragment[]{
                                LineFragment.of("URI=\""),
                                LineFragment.of("/position/user/+61490541848/m6xelkqm").with(Color.CYAN),
                                LineFragment.of("\", contentType=\"application/json\""),
                        }
                ),
                Arguments.of(
                        "aaa bb cc",
                        newArrayList(new Highlight("dd", Color.CYAN)),
                        EMPTY_LIST
                )
        );
    }

    @DisplayName("Should colorize line")
    @ParameterizedTest
    @MethodSource("dataSupplier")
    void shouldReturnColorized(final String line, final List<Highlight> highlights, final LineFragment[] expected) {
        // given

        // when
        final var fragments = Fragmentator.process(line, highlights);

        // then
        assertThat(fragments).containsExactly(expected);
    }
}
