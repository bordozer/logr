package logr;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FileProcessorTest {

    @Test
    void shouldProcessFiles() {
        // given
        final var files = Collections.singletonList(CommonUtils.readResourceFile("file-5.txt"));
        final var highlights = newArrayList(new Highlight("two", Color.BLUE));
        final var logger = mock(Logger.class);

        // when
        new FileProcessor(logger).process(files, highlights);

        // then
        verify(logger, times(1)).fileInfo(anyString());
        verify(logger, times(11)).info(anyString());
        verify(logger, times(1)).summary(anyString());

        verify(logger).info("\u001B[48;5;96m.1\u001B[0m one \u001B[1;34mtwo\u001B[0m\n");
        verify(logger).info("\u001B[48;5;96m.2\u001B[0m \u001B[1;34mtwo\u001B[0m three \u001B[1;34mtwo\u001B[0m\n");
        verify(logger).info("\u001B[48;5;96m.3\u001B[0m three four \u001B[1;34mtwo\u001B[0m\n");
        verify(logger).info("\u001B[48;5;96m.4\u001B[0m four five \u001B[1;34mtwo\u001B[0m\n");
        verify(logger).info("\u001B[48;5;96m.5\u001B[0m five six \u001B[1;34mtwo\u001B[0m\n");
        verify(logger).info("\u001B[48;5;96m.6\u001B[0m one \u001B[1;34mtwo\u001B[0m\n");
        verify(logger).info("\u001B[48;5;96m.7\u001B[0m \u001B[1;34mtwo\u001B[0m \u001B[1;34mtwo\u001B[0m\n");
        verify(logger).info("\u001B[48;5;96m.8\u001B[0m three \u001B[1;34mtwo\u001B[0m\n");
        verify(logger).info("\u001B[48;5;96m.9\u001B[0m four \u001B[1;34mtwo\u001B[0m\n");
        verify(logger).info("\u001B[48;5;96m10\u001B[0m five \u001B[1;34mtwo\u001B[0m\n");
        verify(logger).info("\u001B[48;5;96m12\u001B[0m nine \u001B[1;34mtwo\u001B[0m\n");

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(logger).summary(captor.capture());
        final var value = captor.getValue();
        assertThat(value).startsWith("  Total: 11 line(s) in 1 file(s)");
    }
}
