package logr.utils;

import logr.model.Parameters;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class ArgUtils {

    private static final String HELP = "h";
    private static final String CASE = "c";
    private static final String WORD = "w";
    private static final Set<String> PARAMETERS = Set.of(CASE, WORD);

    public static Parameters extractParameters(final String[] args, final Logger logger) {
        if (args.length == 0) {
            // mask is missed
            AppUtils.exitApp("File mask parameters required. Run 'logr h' for help");
        }
        if (args.length == 1) {
            if (HELP.equals(args[0])) {
                return Parameters.builder()
                        .helpRequire(true)
                        .build();
            }
            // {mask} parameters and keywords or keywords are missed
            AppUtils.exitApp("At least one keyword of at least three characters is required.. Run 'logr h' for help");
        }
        final String firstArg = args[1];
        if (firstArg.length() >= 3) {
            // {mask} {keyword}
            // {mask} {keyword} {keyword}
            final List<String> keywords = subArray(args, false);
            return Parameters.builder()
                    .helpRequire(false)
                    .caseSensitive(false)
                    .wordsOnly(false)
                    .keywords(keywords)
                    .build();
        }
        if (args.length == 2) {
            // {mask} {parameters} keywords is missed
            AppUtils.exitApp("At least one keyword of at least three characters is required. Run 'logr h' for help");
        }
        // {mask} {parameters} {keyword}
        // {mask} {parameters} {keyword} {keyword}
        final boolean isValid = validateParameters(firstArg);
        if (!isValid) {
            AppUtils.exitApp("Unknown parameter. Run 'logr h' for help");
        }

        final boolean isHelp = isHelp(firstArg);
        final boolean isCaseSensitive = isCaseSensitive(firstArg);
        final boolean isWordOnly = isWordOnly(firstArg);
        final boolean hasParameters = isCaseSensitive || isWordOnly;
        final List<String> keywords = subArray(args, hasParameters);
        return Parameters.builder()
                .helpRequire(isHelp)
                .caseSensitive(isCaseSensitive)
                .wordsOnly(isWordOnly)
                .keywords(keywords)
                .build();
    }

    private static boolean validateParameters(final String parameters) {
        final List<String> sorted = Stream.of(parameters.split(""))
                .collect(Collectors.toList());
        return sorted.stream().anyMatch(PARAMETERS::contains);
    }

    private static boolean isHelp(final String firstArg) {
        return HELP.equals(firstArg);
    }

    private static boolean isCaseSensitive(final String firstArg) {
        return firstArg.contains(CASE);
    }

    private static boolean isWordOnly(final String firstArg) {
        return firstArg.contains(WORD);
    }

    private static List<String> subArray(final String[] args, final boolean hasParameters) {
        final List<String> list = Arrays.stream(args).collect(Collectors.toList());
        final int fromIndex = hasParameters ? 2 : 1;
        final List<String> keywords = list.subList(fromIndex, list.size());
        final List<String> toShortKeywords = keywords.stream()
                .filter(k -> k.length() < 3)
                .collect(Collectors.toList());
        if (!toShortKeywords.isEmpty()) {
            AppUtils.exitApp("Keyword cannot be less then three characters. Run 'logr h' for help");
        }
        return keywords;
    }
}
