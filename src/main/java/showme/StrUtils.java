package showme;

public final class StrUtils {

    public static String formatRowNumber(final Integer number, final Integer maxRowNumber) {
        final var numberLength = String.valueOf(number).length();
        final var maxLength = String.valueOf(maxRowNumber).length();
        return String.format("%s%s", ".".repeat(maxLength - numberLength), number);
    }
}
