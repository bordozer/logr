package logr.model;

import lombok.Builder;
import lombok.Getter;

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

    public record FileRow(Integer originalRowNumber,
                          List<LineFragment> fragments) {
    }
}
