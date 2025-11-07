package logr.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FileUtilsTest {

    private static final String SYS_TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final String WORKING_DIR = "working-dir";

    @Test
    void shouldReturnDir() {
        // given
        final String mask = getDir("1");

        // when
        final File dir = FileUtils.getDir(mask);

        // then
        assertThat(dir.isDirectory()).isEqualTo(true);
        assertThat(dir.exists()).isTrue();
        assertThat(dir.getName()).isEqualTo("1");
    }

    @Test
    void shouldReturnDir2() {
        // given
        final String mask = getDir("1/test");

        // when
        final File dir = FileUtils.getDir(mask);

        // then
        assertThat(dir.isDirectory()).isEqualTo(true);
        assertThat(dir.exists()).isTrue();
        assertThat(dir.getName()).isEqualTo("test");
    }

    @Test
    void shouldReturnDir3() {
        // given
        final String mask = getDir("1");

        // when
        final File dir = FileUtils.getDir("%s/*.txt".formatted(mask));

        // then
        assertThat(dir.isDirectory()).isEqualTo(true);
        assertThat(dir.exists()).isTrue();
        assertThat(dir.getName()).isEqualTo("1");
    }

    @Test
    void shouldReturnMask() {
        // given
        final String mask = getDir("1");

        // when
        final String dir = FileUtils.getMask(mask);

        // then
        assertThat(dir).isEqualTo("*");
    }

    @Test
    void shouldReturnMask2() {
        // given
        final String mask = getDir("1");

        // when
        final String dir = FileUtils.getMask("%s/*.txt".formatted(mask));

        // then
        assertThat(dir).isEqualTo("*.txt");
    }

    @Test
    void shouldReturnMask3() {
        // given
        final String mask = getDir("1");

        // when
        final String dir = FileUtils.getMask("%s/test".formatted(mask));

        // then
        assertThat(dir).isEqualTo("test");
    }

    private static String getDir(final String path) {
        final String testDirName = UUID.randomUUID().toString();
        final String testingDirName = "%s/%s/%s/%s".formatted(SYS_TEMP_DIR, testDirName, WORKING_DIR, path);
        final File testDir = new File(testingDirName);
        if (!testDir.exists()) {
            final boolean created = testDir.mkdirs();
            new Logger().info("Test directory created: %s".formatted(created));
        }
        return testDir.getPath();
    }
}
