package logr;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StrUtils {

    public static String formatRowNumber(final Integer number, final Integer maxRowNumber) {
        final int numberLength = String.valueOf(number).length();
        final int maxLength = String.valueOf(maxRowNumber).length();
        return String.format("%s%s", ".".repeat(maxLength - numberLength), number);
    }

    public static String[] splitIgnoreCase(final String text, final String separator) {
        return text.split(String.format("((?<=[.*]?)|(?<=^))(?i)%s((?=[/*]?)|(?=$))", separator));
    }
}
