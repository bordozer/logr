package logr;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexUtils {

    public static List<String> split(final String text, final String keyword) {
        final var result = new ArrayList<String>();
        var last = 0;

        for (int i = 0; i <= text.length(); i++) {
            for (int j = i + 1; j <= text.length(); j++) {
                final var substring = text.substring(i, j);
                if (Pattern.compile(keyword).matcher(substring).matches()) {
                    final var candidate = text.substring(last, i);
                    if (StringUtils.isNoneEmpty(candidate)) {
                        result.add(candidate);
                        last = i;
                    }
                    i = j + 1;
                }
            }
        }

        return result;
    }
}
