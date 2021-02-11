package logr;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorUtils {

    public static String getMessage(final Throwable ex) {
        final var message = ex.getMessage();
        if (StringUtils.isNoneBlank(message)) {
            return message;
        }
        final var exception = ex.getClass().getName();
        final var stackTrace = ex.getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            final var traceElement = stackTrace[0];
            return String.format("%s at %s line %s", exception, traceElement.getClassName(), traceElement.getLineNumber());
        }
        return exception;
    }

    public static String getStackTrace(final Throwable ex) {
        return ExceptionUtils.getStackTrace(ex);
    }
}
