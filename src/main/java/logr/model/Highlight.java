package logr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.annotation.CheckForNull;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(of = {"keyword", "color"})
public class Highlight {
    private final String keyword;
    private boolean excluded;
    @CheckForNull
    private final Color color;
}
