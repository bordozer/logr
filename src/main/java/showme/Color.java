package showme;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Color {
    YELLOW(Logger.YELLOW),
    PURPLE(Logger.PURPLE),
    CYAN(Logger.CYAN),
    WHITE(Logger.WHITE),
    BLUE(Logger.BLUE),
    GREEN(Logger.GREEN);

    private final String value;
}
