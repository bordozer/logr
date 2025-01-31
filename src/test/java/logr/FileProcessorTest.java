package logr;

import logr.model.Color;
import logr.model.CommonUtils;
import logr.model.Highlight;
import logr.model.Parameters;
import logr.utils.Logger;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class FileProcessorTest {

    @Test
    void shouldProcessFiles() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-5.txt"));
        final List<Highlight> highlights = newArrayList(new Highlight("two", Color.BLUE));
        final Logger logger = mock(Logger.class);
        final Parameters params = Parameters.builder()
                .caseSensitive(true)
                .build();

        // when
        final List<Pair<String, String>> actual = new FileProcessor(params, logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(11);
        assertThat(actual.get(0).getValue()).isEqualTo("one \u001B[1;34mtwo\u001B[0m");
        assertThat(actual.get(1).getValue()).isEqualTo("\u001B[1;34mtwo\u001B[0m three \u001B[1;34mtwo\u001B[0m");
        assertThat(actual.get(2).getValue()).isEqualTo("three four \u001B[1;34mtwo\u001B[0m");
        assertThat(actual.get(3).getValue()).isEqualTo("four five \u001B[1;34mtwo\u001B[0m");
        assertThat(actual.get(4).getValue()).isEqualTo("five six \u001B[1;34mtwo\u001B[0m");
        assertThat(actual.get(5).getValue()).isEqualTo("one \u001B[1;34mtwo\u001B[0m");
        assertThat(actual.get(6).getValue()).isEqualTo("\u001B[1;34mtwo\u001B[0m \u001B[1;34mtwo\u001B[0m");
        assertThat(actual.get(7).getValue()).isEqualTo("three \u001B[1;34mtwo\u001B[0m");
        assertThat(actual.get(8).getValue()).isEqualTo("four \u001B[1;34mtwo\u001B[0m");
        assertThat(actual.get(9).getValue()).isEqualTo("five \u001B[1;34mtwo\u001B[0m");
        assertThat(actual.get(10).getValue()).isEqualTo("nine \u001B[1;34mtwo\u001B[0m");
    }

    @Test
    void shouldProcessIgnoreCase1() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-7.log"));
        final List<Highlight> highlights = newArrayList(new Highlight("email", Color.BLUE));
        final Logger logger = mock(Logger.class);

        final Parameters params = Parameters.builder()
                .caseSensitive(false)
                .build();

        // when
        final List<Pair<String, String>> actual = new FileProcessor(params, logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(16);
        assertThat(actual.get(0).getValue()).isEqualTo("Line 1 number one cuatomer\u001B[1;34mEmail\u001B[0m: qwert@domain.com");
        assertThat(actual.get(1).getValue()).isEqualTo("Line 2 number one cuatomer\u001B[1;34memail\u001B[0m: qwert@domain.com");
        assertThat(actual.get(2).getValue()).isEqualTo("Line 3 number one cuatomer\u001B[1;34memail\u001B[0mwork: qwert@domain.com");
        assertThat(actual.get(3).getValue()).isEqualTo("Line 4 number one cuatomer\u001B[1;34mEmail\u001B[0mWork: qwert@domain.com");
        assertThat(actual.get(4).getValue()).isEqualTo("Line 5 number two cuatomer \u001B[1;34memail\u001B[0m: qwert@domain.com");
        assertThat(actual.get(5).getValue()).isEqualTo("Line 6 number two cuatomer \u001B[1;34mEmail\u001B[0m: qwert@domain.com");
        assertThat(actual.get(6).getValue()).isEqualTo("\u001B[1;34memail\u001B[0m");
        assertThat(actual.get(7).getValue()).isEqualTo("\u001B[1;34mEMAIL\u001B[0m");
        assertThat(actual.get(8).getValue()).isEqualTo("\u001B[1;34mEMAILemail\u001B[0m");
        assertThat(actual.get(9).getValue()).isEqualTo("\u001B[1;34mEMAILEMAIL\u001B[0m");
        assertThat(actual.get(10).getValue()).isEqualTo("New 9 \u001B[1;34memail\u001B[0m");
        assertThat(actual.get(11).getValue()).isEqualTo("New 10 \u001B[1;34mEMAIL\u001B[0m");
        assertThat(actual.get(12).getValue()).isEqualTo("\u001B[1;34memail\u001B[0m 11 new");
        assertThat(actual.get(13).getValue()).isEqualTo("\u001B[1;34mEMAIL\u001B[0m 11 new");
        assertThat(actual.get(14).getValue()).isEqualTo(" \u001B[1;34memail\u001B[0m");
        assertThat(actual.get(15).getValue()).isEqualTo(" \u001B[1;34mEMAIL\u001B[0m");
    }

    @Test
    void shouldProcessIgnoreCase2() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-8.log"));
        final List<Highlight> highlights = newArrayList(new Highlight("email", Color.BLUE));
        final Logger logger = mock(Logger.class);

        final Parameters params = Parameters.builder()
                .caseSensitive(false)
                .build();

        // when
        final List<Pair<String, String>> actual = new FileProcessor(params, logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(3);
        assertThat(actual.get(0).getValue()).isEqualTo("Line 1 number one cuatomer\u001B[1;34mEmail\u001B[0m: qwert@\u001B[1;34memail\u001B[0m.com");
        assertThat(actual.get(1).getValue()).isEqualTo("Line 2 number one cuatomer\u001B[1;34memail\u001B[0m: qwert@\u001B[1;34mEmail\u001B[0m.com \u001B[1;34memail\u001B[0m is good");
        assertThat(actual.get(2).getValue()).isEqualTo("Line 3 number one cuatomer\u001B[1;34memail\u001B[0m: qwert@\u001B[1;34mEmail\u001B[0m.com \u001B[1;34memail\u001B[0m is good \u001B[1;34memaiL\u001B[0m");
    }

    @Test
    void shouldProcessRespectCase1() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-7.log"));
        final List<Highlight> highlights = newArrayList(new Highlight("email", Color.BLUE));
        final Logger logger = mock(Logger.class);

        final Parameters params = Parameters.builder()
                .caseSensitive(true)
                .build();

        // when
        final List<Pair<String, String>> actual = new FileProcessor(params, logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(8);
        assertThat(actual.get(0).getValue()).isEqualTo("Line 2 number one cuatomer\u001B[1;34memail\u001B[0m: qwert@domain.com");
        assertThat(actual.get(1).getValue()).isEqualTo("Line 3 number one cuatomer\u001B[1;34memail\u001B[0mwork: qwert@domain.com");
        assertThat(actual.get(2).getValue()).isEqualTo("Line 5 number two cuatomer \u001B[1;34memail\u001B[0m: qwert@domain.com");
        assertThat(actual.get(3).getValue()).isEqualTo("\u001B[1;34memail\u001B[0m");
        assertThat(actual.get(4).getValue()).isEqualTo("EMAIL\u001B[1;34memail\u001B[0m");
        assertThat(actual.get(5).getValue()).isEqualTo("New 9 \u001B[1;34memail\u001B[0m");
        assertThat(actual.get(6).getValue()).isEqualTo("\u001B[1;34memail\u001B[0m 11 new");
        assertThat(actual.get(7).getValue()).isEqualTo(" \u001B[1;34memail\u001B[0m");
    }

    @Test
    void shouldProcessRespectCase2() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-7.log"));
        final List<Highlight> highlights = newArrayList(new Highlight("EMAIL", Color.BLUE));
        final Logger logger = mock(Logger.class);

        final Parameters params = Parameters.builder()
                .caseSensitive(true)
                .build();

        // when
        final List<Pair<String, String>> actual = new FileProcessor(params, logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(6);
        assertThat(actual.get(0).getValue()).isEqualTo("\u001B[1;34mEMAIL\u001B[0m");
        assertThat(actual.get(1).getValue()).isEqualTo("\u001B[1;34mEMAIL\u001B[0memail");
        assertThat(actual.get(2).getValue()).isEqualTo("\u001B[1;34mEMAILEMAIL\u001B[0m");
        assertThat(actual.get(3).getValue()).isEqualTo("New 10 \u001B[1;34mEMAIL\u001B[0m");
        assertThat(actual.get(4).getValue()).isEqualTo("\u001B[1;34mEMAIL\u001B[0m 11 new");
        assertThat(actual.get(5).getValue()).isEqualTo(" \u001B[1;34mEMAIL\u001B[0m");
    }

    @Test
    void shouldProcessRespectCase3() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-7.log"));
        final List<Highlight> highlights = newArrayList(new Highlight("Email", Color.BLUE));
        final Logger logger = mock(Logger.class);

        final Parameters params = Parameters.builder()
                .caseSensitive(true)
                .build();

        // when
        final List<Pair<String, String>> actual = new FileProcessor(params, logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(3);
        assertThat(actual.get(0).getValue()).isEqualTo("Line 1 number one cuatomer\u001B[1;34mEmail\u001B[0m: qwert@domain.com");
        assertThat(actual.get(1).getValue()).isEqualTo("Line 4 number one cuatomer\u001B[1;34mEmail\u001B[0mWork: qwert@domain.com");
        assertThat(actual.get(2).getValue()).isEqualTo("Line 6 number two cuatomer \u001B[1;34mEmail\u001B[0m: qwert@domain.com");
    }

    @Test
    void shouldProcessRespectCaseWordsOnly() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-9.log"));
        final List<Highlight> highlights = newArrayList(new Highlight("Email", Color.BLUE));
        final Logger logger = mock(Logger.class);

        final Parameters params = Parameters.builder()
                .caseSensitive(true)
                .wordsOnly(true)
                .build();

        // when
        final List<Pair<String, String>> actual = new FileProcessor(params, logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(4);
        assertThat(actual.get(0).getValue()).isEqualTo("Line 2 \u001B[1;34mEmail\u001B[0m");
        assertThat(actual.get(1).getValue()).isEqualTo("Line 4 \u001B[1;34mEmail\u001B[0m exist");
        assertThat(actual.get(2).getValue()).isEqualTo("Line 6 \"\u001B[1;34mEmail\u001B[0m\"");
        assertThat(actual.get(3).getValue()).isEqualTo("Line 8 \"\u001B[1;34mEmail\u001B[0m\" exist");
    }

    @Test
    void shouldProcessCaseIgnoreWordsOnly() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-9.log"));
        final List<Highlight> highlights = newArrayList(new Highlight("EmaiL", Color.BLUE));
        final Logger logger = mock(Logger.class);

        final Parameters params = Parameters.builder()
                .wordsOnly(true)
                .build();

        // when
        final List<Pair<String, String>> actual = new FileProcessor(params, logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(8);
        assertThat(actual.get(0).getValue()).isEqualTo("Line 1 \u001B[1;34memail\u001B[0m");
        assertThat(actual.get(1).getValue()).isEqualTo("Line 2 \u001B[1;34mEmail\u001B[0m");
        assertThat(actual.get(2).getValue()).isEqualTo("Line 3 \u001B[1;34memail\u001B[0m exist");
        assertThat(actual.get(3).getValue()).isEqualTo("Line 4 \u001B[1;34mEmail\u001B[0m exist");
        assertThat(actual.get(4).getValue()).isEqualTo("Line 5 '\u001B[1;34memail\u001B[0m'");
        assertThat(actual.get(5).getValue()).isEqualTo("Line 6 \"\u001B[1;34mEmail\u001B[0m\"");
        assertThat(actual.get(6).getValue()).isEqualTo("Line 7 '\u001B[1;34memail\u001B[0m' exist");
        assertThat(actual.get(7).getValue()).isEqualTo("Line 8 \"\u001B[1;34mEmail\u001B[0m\" exist");
    }

    @Test
    void shouldProcessRespectCaseWordsOnlyWithExcluded() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-10.log"));
        final List<Highlight> highlights = newArrayList(
                new Highlight("included", Color.BLUE),
                new Highlight("excluded", true, null)
        );
        final Logger logger = mock(Logger.class);

        final Parameters params = Parameters.builder()
                .wordsOnly(true)
                .caseSensitive(true)
                .build();

        // when
        final List<Pair<String, String>> actual = new FileProcessor(params, logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(3);
        assertThat(actual.get(0).getValue()).isEqualTo("Line 3 \u001B[1;34mincluded\u001B[0m Excluded");
        assertThat(actual.get(1).getValue()).isEqualTo("Line 19 \u001B[1;34mincluded\u001B[0m Excludedin");
        assertThat(actual.get(2).getValue()).isEqualTo("Line 21 \u001B[1;34mincluded\u001B[0m excludedin");
    }

    @Test
    void shouldProcessRespectCaseWithExcluded() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-10.log"));
        final List<Highlight> highlights = newArrayList(
                new Highlight("included", Color.BLUE),
                new Highlight("excluded", true, null)
        );
        final Logger logger = mock(Logger.class);

        final Parameters params = Parameters.builder()
                .caseSensitive(true)
                .build();

        // when
        final List<Pair<String, String>> actual = new FileProcessor(params, logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(7);
        assertThat(actual.get(0).getValue()).isEqualTo("Line 3 \u001B[1;34mincluded\u001B[0m Excluded");
        assertThat(actual.get(1).getValue()).isEqualTo("Line 6 \u001B[1;34mincluded\u001B[0mExcluded");
        assertThat(actual.get(2).getValue()).isEqualTo("Line 10 \u001B[1;34mincluded\u001B[0min Excluded");
        assertThat(actual.get(3).getValue()).isEqualTo("Line 12 \u001B[1;34mincluded\u001B[0min Excluded");
        assertThat(actual.get(4).getValue()).isEqualTo("Line 14 \u001B[1;34mincluded\u001B[0min Excludedin");
        assertThat(actual.get(5).getValue()).isEqualTo("Line 15 \u001B[1;34mincluded\u001B[0min Excludedin");
        assertThat(actual.get(6).getValue()).isEqualTo("Line 19 \u001B[1;34mincluded\u001B[0m Excludedin");
    }

    @Test
    void shouldProcessWordOnlyWithExcluded() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-10.log"));
        final List<Highlight> highlights = newArrayList(
                new Highlight("included", Color.BLUE),
                new Highlight("excluded", true, null)
        );
        final Logger logger = mock(Logger.class);

        final Parameters params = Parameters.builder()
                .wordsOnly(true)
                .build();

        // when
        final List<Pair<String, String>> actual = new FileProcessor(params, logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(4);
        assertThat(actual.get(0).getValue()).isEqualTo("Line 18 \u001B[1;34mIncluded\u001B[0m Excludedin");
        assertThat(actual.get(1).getValue()).isEqualTo("Line 19 \u001B[1;34mincluded\u001B[0m Excludedin");
        assertThat(actual.get(2).getValue()).isEqualTo("Line 20 \u001B[1;34mIncluded\u001B[0m excludedin");
        assertThat(actual.get(3).getValue()).isEqualTo("Line 21 \u001B[1;34mincluded\u001B[0m excludedin");
    }
}
