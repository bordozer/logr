package logr.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public class StrUtils {

    private static final String CONTAINS_PATTERN = "((?<=[.*]?)|(?<=^))%s%s((?=[/*]?)|(?=$))";
    private static final String ENDS_WITH_PATTERN = "((?<=[.*]?)|(?<=^))%s%s((?=[\\n])|(?=$))";
    private static final String ENDS_WITH_WORD_PATTERN = "((?<=[\\s:.,\"'])|(?<=^))(?i)%s%s((?=[\\n])|(?=$))";
    private static final String WORD_PATTERN = "((?<=[\\s:.,\"'])|(?<=^))%s%s((?=[\\s:.,\"'])|(?=$))";

    public static String formatRowNumber(final Integer number, final Integer maxRowNumber) {
        final int numberLength = String.valueOf(number).length();
        final int maxLength = String.valueOf(maxRowNumber).length();
        return String.format("%s%s", ".".repeat(maxLength - numberLength), number);
    }

    public static Pair<List<String>, List<String>> splitIgnoreCase(final String text, final String separator, final boolean isCaseSensitive, final boolean isWordsOnly) {
        final String template = isWordsOnly ? WORD_PATTERN : CONTAINS_PATTERN;
        final String pattern = pattern(template, separator, isCaseSensitive);
        final List<String> collect = Pattern.compile(pattern).matcher(text).results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
        final String[] split = text.split(pattern);
        final List<String> list = Arrays.stream(split).collect(Collectors.toList());
        return Pair.of(list, collect);
    }

    public static boolean isContains(final String text, final String substring, final boolean isCaseSensitive, final boolean isWordsOnly) {
        final String template = isWordsOnly ? WORD_PATTERN : CONTAINS_PATTERN;
        return Pattern.compile(pattern(template, substring, isCaseSensitive)).matcher(text).find();
    }

    public static boolean endsWith(final String text, final String substring, final boolean isCaseSensitive, final boolean isWordsOnly) {
        final String template = isWordsOnly ? ENDS_WITH_WORD_PATTERN : ENDS_WITH_PATTERN;
        return Pattern.compile(pattern(template, substring, isCaseSensitive)).matcher(text).find();
    }

    private static String pattern(final String pattern, final String separator, final boolean isCaseSensitive) {
        final String sensitive = isCaseSensitive ? "" : "(?i)";
        return String.format(pattern, sensitive, separator);
    }
}
