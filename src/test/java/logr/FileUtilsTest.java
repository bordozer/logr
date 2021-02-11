package logr;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileUtilsTest {

    @Test
    void shouldReturnDir() {
        // given
        final var mask = "/home/blu/temp/1";

        // when
        final var dir = FileUtils.getDir(mask);

        // then
        assertThat(dir.isDirectory()).isEqualTo(true);
        assertThat(dir.getAbsolutePath()).isEqualTo("/home/blu/temp/1");
    }

    @Test
    void shouldReturnDir2() {
        // given
        final var mask = "/home/blu/temp/1/test";

        // when
        final var dir = FileUtils.getDir(mask);

        // then
        assertThat(dir.isDirectory()).isEqualTo(true);
        assertThat(dir.getAbsolutePath()).isEqualTo("/home/blu/temp/1");
    }

    @Test
    void shouldReturnDir4() {
        // given
        final var mask = "test";

        // when
        final var dir = FileUtils.getDir(mask);

        // then
        assertThat(dir.isDirectory()).isEqualTo(true);
        assertThat(dir.getPath()).isEqualTo(".");
    }

    @Test
    void shouldReturnDir3() {
        // given
        final var mask = "/home/blu/temp/1/*.txt";

        // when
        final var dir = FileUtils.getDir(mask);

        // then
        assertThat(dir.isDirectory()).isEqualTo(true);
        assertThat(dir.getAbsolutePath()).isEqualTo("/home/blu/temp/1");
    }

    @Test
    void shouldReturnMask() {
        // given
        final var mask = "/home/blu/temp/1";

        // when
        final var dir = FileUtils.getMask(mask);

        // then
        assertThat(dir).isEqualTo("*");
    }

    @Test
    void shouldReturnMask2() {
        // given
        final var mask = "/home/blu/temp/1/*.txt";

        // when
        final var dir = FileUtils.getMask(mask);

        // then
        assertThat(dir).isEqualTo("*.txt");
    }

    @Test
    void shouldReturnMask3() {
        // given
        final var mask = "/home/blu/temp/1/test";

        // when
        final var dir = FileUtils.getMask(mask);

        // then
        assertThat(dir).isEqualTo("test");
    }
}
