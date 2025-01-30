package logr.utils;

import logr.Logger;
import logr.Parameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ArgUtilsTest {

    private static final Logger LOGGER = new Logger();

    @Test
    void shouldSetHelpRequiredParameter() {
        // given
        final String[] args = new String[]{"h"};

        // when
        final Parameters parameters = ArgUtils.extractParameters(args, LOGGER);

        // then
        assertThat(parameters.isHelpRequire()).isTrue();
        assertThat(parameters.isCaseSensitive()).isFalse();
    }

    static Stream<Arguments> dataSupplier() {
        return Stream.of(
                Arguments.of(null, List.of("keyword1"), false),
                Arguments.of(null, List.of("keyword1", "keyword2"), false),
                Arguments.of("c", List.of("keyword1"), true),
                Arguments.of("c", List.of("keyword1", "keyword2"), true)
        );
    }

    @ParameterizedTest
    @MethodSource("dataSupplier")
    void shouldSetCaseSensitiveParameter(@Nullable final String arg, final List<String> keywords, final boolean expected) {
        // given
        final List<String> args = new ArrayList<>();
        args.add("./");
        if (arg != null) {
            args.add("c");
        }
        args.addAll(keywords);
        final String[] strings = args.toArray(String[]::new);

        // when
        final Parameters parameters = ArgUtils.extractParameters(strings, LOGGER);

        // then
        assertThat(parameters.isHelpRequire()).isFalse();
        assertThat(parameters.isCaseSensitive()).isEqualTo(expected);
    }

    static Stream<Arguments> dataSupplier1() {
        return Stream.of(
                Arguments.of(null, List.of("keyword1")),
                Arguments.of(null, List.of("keyword1", "keyword2")),
                Arguments.of("c", List.of("keyword1")),
                Arguments.of("c", List.of("keyword1", "keyword2"))
        );
    }

    @ParameterizedTest
    @MethodSource("dataSupplier1")
    void shouldSetParameters(@Nullable final String arg, final List<String> keywords) {
        // given
        final List<String> args = new ArrayList<>();
        args.add("./");
        if (arg != null) {
            args.add("c");
        }
        args.addAll(keywords);
        final String[] strings = args.toArray(String[]::new);

        // when
        final Parameters parameters = ArgUtils.extractParameters(strings, LOGGER);

        // then
        assertThat(parameters.getKeywords()).isEqualTo(keywords);
    }
}