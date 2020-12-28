package showme;

import com.google.gson.Gson;
import lombok.SneakyThrows;

public final class JsonUtils {

    @SneakyThrows
    static String toJson(final Object object) {
        return new Gson().toJson(object);
    }
}
