package logr;

import logr.model.Color;
import logr.model.Highlight;
import logr.model.LineFragment;
import logr.model.Parameters;
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
                        newArrayList(new Highlight("one two", Color.CYAN_BOLD), new Highlight("one", Color.MAGENTA_BOLD)),
                        EMPTY_LIST
                ),
                Arguments.of(
                        "one",
                        newArrayList(new Highlight("one", Color.CYAN_BOLD)),
                        new LineFragment[]{
                                LineFragment.of("one").with(Color.CYAN_BOLD)
                        }
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one two", Color.CYAN_BOLD), new Highlight("one", Color.MAGENTA_BOLD)),
                        new LineFragment[]{
                                LineFragment.of("one two").with(Color.CYAN_BOLD),
                                LineFragment.of(" "),
                                LineFragment.of("one").with(Color.MAGENTA_BOLD),
                                LineFragment.of(" three two "),
                                LineFragment.of("one").with(Color.MAGENTA_BOLD),
                                LineFragment.of(" "),
                                LineFragment.of("one two").with(Color.CYAN_BOLD)
                        }
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one", Color.CYAN_BOLD)),
                        new LineFragment[]{
                                LineFragment.of("one").with(Color.CYAN_BOLD),
                                LineFragment.of(" two "),
                                LineFragment.of("one").with(Color.CYAN_BOLD),
                                LineFragment.of(" three two "),
                                LineFragment.of("one").with(Color.CYAN_BOLD),
                                LineFragment.of(" "),
                                LineFragment.of("one").with(Color.CYAN_BOLD),
                                LineFragment.of(" two")
                        }
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one two one", Color.CYAN_BOLD)),
                        new LineFragment[]{
                                LineFragment.of("one two one").with(Color.CYAN_BOLD),
                                LineFragment.of(" three two one one two")
                        }
                ),
                Arguments.of(
                        "one two one three two one one two",
                        newArrayList(new Highlight("one", Color.CYAN_BOLD), new Highlight("two", Color.CYAN_BOLD), new Highlight("three", Color.CYAN_BOLD)),
                        new LineFragment[]{
                                LineFragment.of("one").with(Color.CYAN_BOLD),
                                LineFragment.of(" "),
                                LineFragment.of("two").with(Color.CYAN_BOLD),
                                LineFragment.of(" "),
                                LineFragment.of("one").with(Color.CYAN_BOLD),
                                LineFragment.of(" "),
                                LineFragment.of("three").with(Color.CYAN_BOLD),
                                LineFragment.of(" "),
                                LineFragment.of("two").with(Color.CYAN_BOLD),
                                LineFragment.of(" "),
                                LineFragment.of("one").with(Color.CYAN_BOLD),
                                LineFragment.of(" "),
                                LineFragment.of("one").with(Color.CYAN_BOLD),
                                LineFragment.of(" "),
                                LineFragment.of("two").with(Color.CYAN_BOLD)
                        }
                ),
                Arguments.of(
                        "one",
                        newArrayList(new Highlight("one", Color.CYAN_BOLD), new Highlight("two", Color.CYAN_BOLD)),
                        EMPTY_LIST
                ),
                Arguments.of(
                        "one two",
                        newArrayList(new Highlight("one", Color.CYAN_BOLD), new Highlight("two", true, Color.CYAN_BOLD)),
                        EMPTY_LIST
                ),
                Arguments.of(
                        "one two",
                        newArrayList(new Highlight("one", Color.CYAN_BOLD), new Highlight("one", Color.CYAN_BOLD)),
                        new LineFragment[]{
                                LineFragment.of("one").with(Color.CYAN_BOLD),
                                LineFragment.of(" two")
                        }
                ),
                Arguments.of(
                        "one",
                        newArrayList(new Highlight("one", Color.CYAN_BOLD)),
                        new LineFragment[]{
                                LineFragment.of("one").with(Color.CYAN_BOLD)
                        }
                ),
                Arguments.of(
                        "one",
                        newArrayList(new Highlight("one", Color.CYAN_BOLD), new Highlight("one", true, Color.CYAN_BOLD)),
                        EMPTY_LIST
                ),
                Arguments.of(
                        "Saving offer 100019500 event Øriggered, traceId=\"f36587a58cf2fd34\"",
                        newArrayList(new Highlight("f36587a58cf2fd34", Color.CYAN_BOLD)),
                        new LineFragment[]{
                                LineFragment.of("Saving offer 100019500 event Øriggered, traceId=\""),
                                LineFragment.of("f36587a58cf2fd34").with(Color.CYAN_BOLD),
                                LineFragment.of("\"")
                        }
                )
        );
    }

    @DisplayName("Should colorize line")
    @ParameterizedTest
    @MethodSource("dataSupplier")
    void shouldReturnColorized(final String line, final List<Highlight> highlights, final LineFragment[] expected) {
        // given
        final Parameters params = Parameters.builder()
                .caseSensitive(true)
                .build();

        // when
        final var fragments = Fragmentator.process(line, highlights, params);

        // then
        assertThat(fragments).containsExactly(expected);
    }
}
