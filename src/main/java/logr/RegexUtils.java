package logr;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexUtils {

    public static List<Part> split(final String text, final String keyword) {
        final var result = new ArrayList<Part>();
        var remain = text;

        for (int i = 0; i <= remain.length(); i++) {
            for (int j = i + 1; j <= remain.length(); j++) {
                final var testee = remain.substring(i, j);
                if (Pattern.compile(keyword).matcher(testee).matches()) {
                    final var candidate = remain.substring(0, i);
                    if (StringUtils.isNotEmpty(candidate)) {
                        result.add(Part.text(candidate));
                    }
                    result.add(Part.keyword(testee));
                    remain = remain.substring(j);
                    result.addAll(split(remain, keyword));
                    return result;
                }
            }
        }
        if (StringUtils.isNotEmpty(remain)) {
            result.add(Part.text(remain));
        }

        return result;
    }

    @Getter
    @RequiredArgsConstructor
    @EqualsAndHashCode(of = {"text", "keyword"})
    public static class Part {
        private final String text;
        private final boolean keyword;

        public static Part text(final String text) {
            return new Part(text, false);
        }

        public static Part keyword(final String text) {
            return new Part(text, true);
        }
    }
}
