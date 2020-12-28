package showme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Highlight {
    private final String keyword;
    private final Color color;
}
