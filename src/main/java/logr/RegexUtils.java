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
        var remain = text;

        for (int i = 0; i <= remain.length(); i++) {
            for (int j = i + 1; j <= remain.length(); j++) {
                final var testee = remain.substring(i, j);
                if (Pattern.compile(keyword).matcher(testee).matches()) {
                    final var candidate = remain.substring(0, i);
                    if (StringUtils.isNotEmpty(candidate)) {
                        result.add(candidate);
                    }
                    remain = remain.substring(j);
                    result.addAll(split(remain, keyword));
                    return result;
                }
            }
        }
        result.add(text);

        return result;
    }
}
