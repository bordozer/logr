package logr;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
public class Parameters {
    @Builder.Default
    private final boolean helpRequire = false;
    @Builder.Default
    private final boolean caseSensitive = false;
    @Builder.Default
    private final boolean wordsOnly = false;
    @Builder.Default
    private final List<String> keywords = List.of();
}
