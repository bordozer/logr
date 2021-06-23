package logr;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static logr.CommonUtils.readResource;
import static org.assertj.core.api.Assertions.assertThat;

public class RegexUtilsPerformanceTest {

    @Test
    @Disabled
    void should() {
        // given
        final var text = readResource("long-text.txt");
        final var keyword = "Osbourne";

        // when
        final var start = System.currentTimeMillis();
        final var result = RegexUtils.split(text, keyword);
        final var end = System.currentTimeMillis();

        // then
        assertThat(result).hasSize(25);
        assertThat(end - start).isLessThan(1500L);
    }
}
