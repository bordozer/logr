package logr;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.annotation.CheckForNull;

@Getter
@RequiredArgsConstructor
@ToString
public class Highlight {
    private final String keyword;
    @CheckForNull
    private final Color color;
    private boolean excluded;

    public Highlight(final String keyword, @CheckForNull final Color color, final boolean excluded) {
        this.keyword = keyword;
        this.color = color;
        this.excluded = excluded;
    }
}
