package logr;

import logr.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class FileUtilsTest {

    @Test
    void shouldReturnDir() {
        // given
        final String mask = "/home/blu/temp/1";

        // when
        final File dir = FileUtils.getDir(mask);

        // then
        assertThat(dir.isDirectory()).isEqualTo(true);
        assertThat(dir.getAbsolutePath()).isEqualTo("/home/blu/temp/1");
    }

    @Test
    void shouldReturnDir2() {
        // given
        final String mask = "/home/blu/temp/1/test";

        // when
        final File dir = FileUtils.getDir(mask);

        // then
        assertThat(dir.isDirectory()).isEqualTo(true);
        assertThat(dir.getAbsolutePath()).isEqualTo("/home/blu/temp/1");
    }

    @Test
    void shouldReturnDir4() {
        // given
        final String mask = "test";

        // when
        final File dir = FileUtils.getDir(mask);

        // then
        assertThat(dir.isDirectory()).isEqualTo(true);
        assertThat(dir.getPath()).isEqualTo(".");
    }

    @Test
    void shouldReturnDir3() {
        // given
        final String mask = "/home/blu/temp/1/*.txt";

        // when
        final File dir = FileUtils.getDir(mask);

        // then
        assertThat(dir.isDirectory()).isEqualTo(true);
        assertThat(dir.getAbsolutePath()).isEqualTo("/home/blu/temp/1");
    }

    @Test
    void shouldReturnMask() {
        // given
        final String mask = "/home/blu/temp/1";

        // when
        final String dir = FileUtils.getMask(mask);

        // then
        assertThat(dir).isEqualTo("*");
    }

    @Test
    void shouldReturnMask2() {
        // given
        final String mask = "/home/blu/temp/1/*.txt";

        // when
        final String dir = FileUtils.getMask(mask);

        // then
        assertThat(dir).isEqualTo("*.txt");
    }

    @Test
    void shouldReturnMask3() {
        // given
        final String mask = "/home/blu/temp/1/test";

        // when
        final String dir = FileUtils.getMask(mask);

        // then
        assertThat(dir).isEqualTo("test");
    }
}
