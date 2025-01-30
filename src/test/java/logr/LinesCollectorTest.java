package logr;

import logr.FileContainer.FileRow;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

class LinesCollectorTest {

    @Test
    void shouldIgnoreStringsWithoutKeywords() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-2.txt"));
        final List<Highlight> highlights = newArrayList(new Highlight("two", Color.YELLOW));
        final Parameters params = Parameters.builder()
                .isCaseSensitive(true)
                .build();

        // when
        final List<FileContainer> list = LinesCollector.collect(files, highlights, params);

        // then
        assertThat(list).hasSize(1);

        final FileContainer fl1 = list.get(0);

        final List<FileRow> lines = fl1.getLines();
        assertThat(lines).hasSize(2);

        final FileRow row1 = lines.get(0);
        assertThat(row1.getOriginalRowNumber()).isEqualTo(1);
        final List<LineFragment> fragments1 = row1.getFragments();
        assertThat(fragments1).hasSize(3);
        assertThat(fragments1.get(0)).isEqualTo(LineFragment.of("one "));
        assertThat(fragments1.get(1)).isEqualTo(LineFragment.of("two").with(Color.YELLOW));
        assertThat(fragments1.get(2)).isEqualTo(LineFragment.of(" three"));

        final FileRow row2 = lines.get(1);
        assertThat(row2.getOriginalRowNumber()).isEqualTo(2);
        final List<LineFragment> fragments2 = row2.getFragments();
        assertThat(fragments2).hasSize(2);
        assertThat(fragments2.get(0)).isEqualTo(LineFragment.of("two").with(Color.YELLOW));
        assertThat(fragments2.get(1)).isEqualTo(LineFragment.of(" five"));
    }

    @Test
    void shouldIgnoreExcludedStrings() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-4.txt"));
        final List<Highlight> highlights = newArrayList(new Highlight("two", Color.YELLOW), new Highlight("one", true, Color.YELLOW));
        final Parameters params = Parameters.builder()
                .isCaseSensitive(true)
                .build();

        // when
        final List<FileContainer> list = LinesCollector.collect(files, highlights, params);

        // then
        assertThat(list).hasSize(1);

        final FileContainer fl = list.get(0);

        final List<FileRow> lines = fl.getLines();
        assertThat(lines).hasSize(1);

        final FileRow row = lines.get(0);
        assertThat(row.getOriginalRowNumber()).isEqualTo(3);
        final List<LineFragment> fragments = row.getFragments();
        assertThat(fragments).hasSize(2);
        assertThat(fragments.get(0)).isEqualTo(LineFragment.of("two").with(Color.YELLOW));
        assertThat(fragments.get(1)).isEqualTo(LineFragment.of(" three"));
    }

    @Test
    void shouldProcessSpecialChars() {
        // given
        final List<File> files = Collections.singletonList(CommonUtils.readResourceFile("file-6.txt"));
        final List<Highlight> highlights = newArrayList(new Highlight("f36587a58cf2fd34", Color.YELLOW));
        final Parameters params = Parameters.builder()
                .isCaseSensitive(true)
                .build();

        // when
        final List<FileContainer> list = LinesCollector.collect(files, highlights, params);

        // then
        assertThat(list).hasSize(1);

        final FileContainer fl = list.get(0);

        final List<FileRow> lines = fl.getLines();
        assertThat(lines).hasSize(1);

        final FileRow row = lines.get(0);
        assertThat(row.getOriginalRowNumber()).isEqualTo(1);
        final List<LineFragment> fragments = row.getFragments();
        assertThat(fragments).hasSize(3);
        assertThat(fragments.get(0)).isEqualTo(LineFragment.of("Saving offer 100019500 event Øriggered, traceId=\""));
        assertThat(fragments.get(1)).isEqualTo(LineFragment.of("f36587a58cf2fd34").with(Color.YELLOW));
    }
}
