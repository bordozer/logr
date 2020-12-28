package showme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Color {
    YELLOW_BOLD_BRIGHT(Logger.YELLOW_BOLD_BRIGHT),
    CYAN_BOLD_BRIGHT(Logger.CYAN_BOLD_BRIGHT),
    BLUE_BOLD_BRIGHT(Logger.BLUE_BOLD_BRIGHT),
    PURPLE_BOLD_BRIGHT(Logger.PURPLE_BOLD_BRIGHT),
    GREEN_BOLD_BRIGHT(Logger.GREEN_BOLD_BRIGHT),
    YELLOW_BRIGHT(Logger.YELLOW_BRIGHT),
    CYAN_BRIGHT(Logger.CYAN_BRIGHT),
    BLUE_BRIGHT(Logger.BLUE_BRIGHT),
    PURPLE_BRIGHT(Logger.PURPLE_BRIGHT),
    GREEN_BRIGHT(Logger.GREEN_BRIGHT),
    YELLOW(Logger.YELLOW_BOLD),
    CYAN(Logger.CYAN_BOLD),
    BLUE(Logger.BLUE_BOLD),
    PURPLE(Logger.PURPLE_BOLD),
    GREEN(Logger.GREEN_BOLD)
    ;

    private final String value;
}
