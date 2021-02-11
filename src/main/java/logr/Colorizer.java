package logr;

import java.util.List;
import java.util.stream.Collectors;

public final class Colorizer {

    public static String buildColorizedString(final List<LineFragment> fragments) {
        return fragments.stream()
                .map(fragment -> {
                    if (fragment.getColor() == null) {
                        return fragment.getText();
                    }
                    return String.format("%s%s%s", fragment.getColor().getValue(), fragment.getText(), Logger.RESET);
                })
                .collect(Collectors.joining());
    }
}
