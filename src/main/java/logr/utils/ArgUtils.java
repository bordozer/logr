package logr.utils;

import logr.Logger;
import logr.Parameters;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ArgUtils {

    public static Parameters extractParameters(final String[] args, final Logger logger) {
        if (args.length == 0) {
            logger.info("Parameters required. run 'logr h' for help");
            System.exit(1);
        }
        if (args.length == 1 && args[0].equals("h")) {
            return Parameters.builder()
                    .isHelpRequire(true)
                    .build();
        }
        final String firstArg = args[1];
        final boolean isCaseSensitive = firstArg.equals("c");
        if (isCaseSensitive && args.length == 2) {
            logger.info("Parameters required. run 'logr h' for help");
            System.exit(1);
        }
        if (args.length == 2 && firstArg.length() < 3) {
            logger.info(String.format("Invalid parameter \"%s\". Run 'logr h' for help", firstArg));
            System.exit(1);
        }
        return Parameters.builder()
                .isHelpRequire(false)
                .isCaseSensitive(isCaseSensitive)
                .keywords(subArray(args, isCaseSensitive))
                .build();
    }

    private static List<String> subArray(final String[] args, final boolean isCaseSensitive) {
        final List<String> list = Arrays.stream(args).collect(Collectors.toList());
        final int fromIndex = isCaseSensitive ? 2 : 1;
        return list.subList(fromIndex, list.size());
    }
}
