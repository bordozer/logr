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

        for (int i = 0; i <= text.length(); i++) {
            for (int j = i + 1; j <= text.length(); j++) {
                final var substring = text.substring(i, j);
                if (Pattern.compile(keyword).matcher(substring).matches()) {
                    final var candidate = text.substring(0, i);
                    if (StringUtils.isNotEmpty(candidate)) {
                        result.add(candidate);
                        final var remain = text.substring(j);
                        result.addAll(split(remain, keyword));
                        return result;
                    }
                    i = j + 1;
                }
            }
        }
        result.add(text);

        return result;
    }
}
