package showme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Highlight {
    private final String keyword;
    private final Color color;
    private boolean excluded;

    public Highlight(final String keyword, final Color color, final boolean excluded) {
        this.keyword = keyword;
        this.color = color;
        this.excluded = excluded;
    }
}
