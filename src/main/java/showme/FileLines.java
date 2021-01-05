package showme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.File;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class FileLines {
    private final File file;
    private final List<FileRow> lines;

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class FileRow {
        private final Integer rowNumber;
        private final String line;
    }
}
