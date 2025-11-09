package academy;

import academy.engine.Difficulty;
import academy.io.ConsoleUI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import static java.util.Objects.nonNull;

@Command(name = "Application Example", version = "Example 1.0", mixinStandardHelpOptions = true)
public class Application implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static final ObjectReader YAML_READER =
        new ObjectMapper(new YAMLFactory()).findAndRegisterModules().reader();
    private static final Predicate<String[]> IS_TESTING_MODE = words -> nonNull(words) && words.length == 2;

    @Option(
        names = {"-s", "--font-size"},
        description = "Font size")
    int fontSize;

    @Parameters(
        paramLabel = "<word>",
        description = "Words pair for testing mode")
    private String[] words;

    @Option(
        names = {"-c", "--config"},
        description = "Path to YAML config file")
    private File configPath;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        AppConfig config = loadConfig();

        // если передано ровно 1 слово — это ошибка запуска
        if (words != null && words.length == 1) {
            throw new picocli.CommandLine.ParameterException(
                new picocli.CommandLine(this),
                "Non-interactive mode expects exactly two args: <secret> <attempt>. " +
                    "Run without args for interactive mode."
            );
        }
        if (words != null && words.length > 2) {
            throw new picocli.CommandLine.ParameterException(
                new picocli.CommandLine(this), "Non-interactive mode expects exactly two args: <secret> <attempt>, no more");}

        // если пользователь не указал сложность, то выбираем случайную
        if (config.difficulty() == null) {
            config = new AppConfig(config.fontSize(), config.words(), Difficulty.random());
        }
        LOGGER.atInfo().addKeyValue("config", config).log("Config content");

        // ... logic
        if (IS_TESTING_MODE.test(config.words())) {
            LOGGER.atInfo().log("Non-interactive testing mode enabled");
            // Использование вызова движка игры вместо hardcode тестовых данных
            var secret = config.words()[0];
            var attempt = config.words()[1];

            //реализация тестового режима
            String out = TestModeRunner.run(secret, attempt);
            System.out.println(out);
        } else {
            LOGGER.atInfo().log("Interactive mode enabled");
            // тут будут ConsoleUI и GameEngine

            ConsoleUI.runInteractive();
        }
    }

    private AppConfig loadConfig() {
        if (configPath == null) return new AppConfig(fontSize, words, null);

        try {
            return YAML_READER.readValue(configPath, AppConfig.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
