package showme;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.CheckForNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LineFragment {
    private final String text;
    @CheckForNull
    private Color color;

    public static LineFragment of(final String text) {
        return new LineFragment(text);
    }

    public LineFragment with(final Color color) {
        this.color = color;
        return this;
    }
}
