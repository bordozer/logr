package showme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Color {
    YELLOW(Logger.YELLOW_BOLD),
    PURPLE(Logger.PURPLE_BOLD),
    CYAN(Logger.CYAN_BOLD),
    WHITE(Logger.WHITE_BOLD),
    BLUE(Logger.BLUE_BOLD),
    GREEN(Logger.GREEN_BOLD);

    private final String value;
}
