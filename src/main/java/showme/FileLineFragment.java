package showme;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.CheckForNull;

@Getter
@Builder
public class FileLineFragment {
    @NonNull
    private final String text;
    @CheckForNull
    private Color color;
}
