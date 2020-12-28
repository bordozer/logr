package showme;

import javax.annotation.CheckForNull;
import java.util.stream.Collectors;

public final class FileLineLogger {

    public static void log(final FileLine fileLine) {
        final var fragments = fileLine.getFragments();
        var line = fragments.stream()
                .map(FileLineLogger::asText)
                .collect(Collectors.joining());
        Logger.info(line);
    }

    private static String asText(final FileLineFragment frag) {
        final var text = frag.getText();
        @CheckForNull final var color = frag.getColor();
        if (color == null) {
            return text;
        }

        return String.format("%s%s%s", color.getValue(), text, Logger.RESET);
    }
}
