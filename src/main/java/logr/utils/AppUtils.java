package logr.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppUtils {

    public static void exitApp(final String message) {
//        System.exit(1);
        throw new RuntimeException(message);
    }
}
