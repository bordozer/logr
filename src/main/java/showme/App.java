package showme;

public class App {

    public static void main(String[] args) {
        Logger.info(args);
        if (args == null || args.length == 0) {
            Logger.error("Define file mask");
            System.exit(1);
        }
        final var mask = args[0];
        final var files = FileUtils.getFiles(mask);
    }
}
