package showme;

import com.google.gson.Gson;
import lombok.SneakyThrows;

public class Logger {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    @SneakyThrows
    public static void info(final Object object) {
        System.out.println(new Gson().toJson(object));
    }

    public static void info(final String message) {
        System.out.println(message);
    }

    public static void error(final String message) {
        System.out.println(String.format("%s%s%s", ANSI_RED, message, ANSI_RESET));
    }
}
