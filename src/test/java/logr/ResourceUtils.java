package logr;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.CheckForNull;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@UtilityClass
public class ResourceUtils {

    @SneakyThrows
    public static String readResource(final String name) {
        final ClassLoader classLoader = ResourceUtils.class.getClassLoader();
        try (@CheckForNull final InputStream is = classLoader.getResourceAsStream(name)) {
            if (is == null) {
                return StringUtils.EMPTY;
            }
            try (final InputStreamReader isr = new InputStreamReader(is);
                 final BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines()
                        .collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}
