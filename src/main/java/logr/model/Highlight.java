package logr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.CheckForNull;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Highlight {
    private final String keyword;
    private boolean excluded;
    @CheckForNull
    private final Color color;
}
