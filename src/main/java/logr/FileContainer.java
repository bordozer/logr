package logr;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class FileContainer {

    private final File file;
    @Builder.Default
    private final List<FileRow> lines = Collections.emptyList();
    @Builder.Default
    private final Boolean directory = Boolean.FALSE;

    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class FileRow {
        private final Integer originalRowNumber;
        private final List<LineFragment> fragments;
    }
}
