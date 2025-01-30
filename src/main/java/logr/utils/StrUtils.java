package logr.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class StrUtils {

    private static final String CONTAINS_PATTERN = "((?<=[.*]?)|(?<=^))%s%s((?=[/*]?)|(?=$))";
    private static final String ENDS_WITH_PATTERN = "((?<=[.*]?)|(?<=^))%s%s((?=[\\n])|(?=$))";

    public static String formatRowNumber(final Integer number, final Integer maxRowNumber) {
        final int numberLength = String.valueOf(number).length();
        final int maxLength = String.valueOf(maxRowNumber).length();
        return String.format("%s%s", ".".repeat(maxLength - numberLength), number);
    }

    public static String[] splitIgnoreCase(final String text, final String separator, final boolean isCaseSensitive) {
        return text.split(pattern(CONTAINS_PATTERN, separator, isCaseSensitive));
    }

    public static boolean isContains(final String text, final String substring, final boolean isCaseSensitive) {
        return Pattern.compile(pattern(CONTAINS_PATTERN, substring, isCaseSensitive)).matcher(text).find();
    }

    public static boolean endsWith(final String text, final String substring, final boolean isCaseSensitive) {
        return Pattern.compile(pattern(ENDS_WITH_PATTERN, substring, isCaseSensitive)).matcher(text).find();
    }

    private static String pattern(final String pattern, final String separator, final boolean isCaseSensitive) {
        final String sensitive = isCaseSensitive ? "" : "(?i)";
        return String.format(pattern, sensitive, separator);
    }
}
