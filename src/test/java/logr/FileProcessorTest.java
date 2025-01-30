package logr;

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

        // when
        final List<Pair<String, String>> actual = new FileProcessor(logger).process(files, highlights);

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
    void shouldProcessIgnoreCase() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-7.log"));
        final List<Highlight> highlights = newArrayList(new Highlight("email", Color.BLUE));
        final Logger logger = mock(Logger.class);

        // when
        final List<Pair<String, String>> actual = new FileProcessor(logger).process(files, highlights);

        // then
        assertThat(actual).hasSize(10);
        assertThat(actual.get(0).getValue()).isEqualTo("Line number one cuatomer\u001B[1;34memail\u001B[0m: qwert@domain.com");
        assertThat(actual.get(1).getValue()).isEqualTo("Line number one cuatomer\u001B[1;34memail\u001B[0m: qwert@domain.com");
        assertThat(actual.get(2).getValue()).isEqualTo("Line number one cuatomer\u001B[1;34memail\u001B[0mwork: qwert@domain.com");
        assertThat(actual.get(3).getValue()).isEqualTo("Line number one cuatomer\u001B[1;34memail\u001B[0mWork: qwert@domain.com");
        assertThat(actual.get(4).getValue()).isEqualTo("Line number two cuatomer \u001B[1;34memail\u001B[0m: qwert@domain.com");
        assertThat(actual.get(5).getValue()).isEqualTo("Line number two cuatomer \u001B[1;34memail\u001B[0m: qwert@domain.com");
        assertThat(actual.get(6).getValue()).isEqualTo("\u001B[1;34memail\u001B[0m");
        assertThat(actual.get(7).getValue()).isEqualTo("\u001B[1;34memail\u001B[0m");
        assertThat(actual.get(7).getValue()).isEqualTo("\u001B[1;34memail\u001B[0m");
        assertThat(actual.get(7).getValue()).isEqualTo("\u001B[1;34memail\u001B[0m");
    }
}
