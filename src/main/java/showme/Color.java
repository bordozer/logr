package showme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Color {
    BLACK(Logger.BLACK),
    RED(Logger.RED),
    GREEN(Logger.GREEN),
    YELLOW(Logger.YELLOW),
    BLUE(Logger.BLUE),
    PURPLE(Logger.PURPLE),
    CYAN(Logger.CYAN),
    WHITE(Logger.WHITE);

    private final String value;
}
