package logr.model;

import com.google.common.io.Resources;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class CommonUtils {

    @SneakyThrows
    public static String readResource(final String name) {
        final URL url = Resources.getResource(name);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public static File readResourceFile(final String fileName) {
        final URL resource = CommonUtils.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException(String.format("Resource dies not exist: \"%s\"", fileName));
        }
        return new File(resource.toURI());
    }
}
