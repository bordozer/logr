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
    private final boolean isHelpRequire = false;
    @Builder.Default
    private final boolean isCaseSensitive = false;
    @Builder.Default
    private final List<String> keywords = List.of();
}
