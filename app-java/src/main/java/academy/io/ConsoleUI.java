package academy.io;

import academy.GameSession;
import academy.engine.Difficulty;
import academy.engine.GuessResult;
import academy.engine.GameState;
import academy.engine.WordRepository;
import academy.engine.WordRepository.Category;
import academy.render.HangmanRenderer;

import java.util.Random;
import java.util.Scanner;

public final class ConsoleUI {

    private static final Random random = new Random();

    public static void runInteractive() {
        Scanner sc = new Scanner(System.in);

        Category category = promptCategory(sc);
        Difficulty difficulty = promptDifficulty(sc);

        int maxAttempts = difficulty.maxAttempts();

        WordRepository.Pick pick = WordRepository.pickRandom(category, difficulty);
        GameSession session = new GameSession(pick, maxAttempts);
        GameState state = session.state();

        String secret = pick.word();
        System.out.printf("Category: %s, Difficulty: %s, word length %d%n",
            pick.category(), pick.difficulty(), secret.length());

        while (!state.isWon() && !state.isLost()) {
            System.out.println("Word: " + state.masked());
            System.out.println(HangmanRenderer.render(state.wrongAttempts(), difficulty));
            System.out.println("Remaining attempts: " + state.remainingAttempts());

            System.out.print("Enter a letter or enter 'hint': ");
            String line = sc.nextLine().trim();

            if (line.isEmpty()) {
                System.out.println("(!!!) You entered nothing, try again");
                continue;
            }

            if (line.equalsIgnoreCase("hint")) {
                if (session.isHintUsed()) {
                    System.out.println("Hint already used.");
                } else {
                    String hint = session.useHint();
                    System.out.println(hint != null ? "Hint: " + hint : "No hint available");
                }
                continue;
            }

            if (line.length() != 1) {
                System.out.println("(!!!) Enter a single letter, try again");
                continue;
            }

            char letter = line.charAt(0);
            letter = Character.toLowerCase(letter);

            // Только английские буквы A–Z
            if ((letter < 'a' || letter > 'z')) {
                System.out.println("Please, enter only english letters (A–Z).");
                continue;
            }

            GuessResult r = state.guess(letter);
            switch (r) {
                case HIT -> System.out.println("Correct!");
                case MISS -> {
                    System.out.println("Wrong! Attempts left: " + state.remainingAttempts());
                    System.out.println(HangmanRenderer.render(state.wrongAttempts(), difficulty));
                }
                case ALREADY_GUESSED -> System.out.println("(!!!) You already guessed this letter. Try another one.");
            }
        }

        System.out.println("Final: " + state.masked() + " (answer: " + session.secret() + ")");
        System.out.println(HangmanRenderer.render(state.wrongAttempts(), difficulty));
        System.out.println(state.isWon() ? "You won!" : "You lost!");
    }

    private static Category promptCategory(Scanner sc) {
        System.out.println("Choose category: IT, ANIMALS, FRUITS (Enter = random):");
        String input = sc.nextLine().trim().toUpperCase();
        if (input.isEmpty()) {
            Category[] arr = Category.values();
            return arr[random.nextInt(arr.length)];
        }
        try {
            return Category.valueOf(input);
        } catch (IllegalArgumentException ex) {
            System.out.println("Unknown category. Enter category name again.");
            return promptCategory(sc);
        }
    }

    private static Difficulty promptDifficulty(Scanner sc) {
        while (true) {
            System.out.println("Choose difficulty: 1/EASY, 2/MEDIUM, 3/HARD (Enter = random):");
            String input = sc.nextLine().trim().toUpperCase();

            // Enter => случайно
            if (input.isEmpty()) {
                Difficulty[] values = Difficulty.values();
                int idx = (int) (Math.random() * values.length);
                return values[idx];
            }

            // Числа
            switch (input) {
                case "1" -> { return Difficulty.EASY; }
                case "2" -> { return Difficulty.MEDIUM; }
                case "3" -> { return Difficulty.HARD; }
            }

            // Слова
            switch (input) {
                case "EASY" -> { return Difficulty.EASY; }
                case "MEDIUM" -> { return Difficulty.MEDIUM; }
                case "HARD" -> { return Difficulty.HARD; }
            }

            // Всё остальное — ошибка и повторный запрос
            System.out.println("Unknown difficulty. Enter difficulty again.");
        }
    }


    /** возвращает GuessResult для UI-логики. */
    public static GuessResult processInputLineResult(GameSession session, String line) {
        if (line == null) return null;
        line = line.trim();
        if (line.isEmpty()) {
            System.out.println("(!!!) You entered nothing, try again");
            return null;
        }

        if (line.equalsIgnoreCase("hint")) {
            if (session.isHintUsed()) {
                System.out.println("Hint already used.");
            } else {
                String hint = session.useHint();
                System.out.println(hint != null ? "Hint: " + hint : "No hint available");
            }
            return null; // подсказка не тратит попытку
        }

        if (line.length() != 1) {
            System.out.println("(!!!) Enter a single letter, try again");
            return null;
        }

        char letter = line.charAt(0);
        if (!((letter >= 'A' && letter <= 'Z') || (letter >= 'a' && letter <= 'z'))) {
            System.out.println("Please, enter only english letters (A–Z).");
            return null;
        }

        return session.state().guess(letter);
    }

    public static boolean processInputLine(GameSession session, String line) {
        GuessResult r = processInputLineResult(session, line);
        return r == GuessResult.HIT || r == GuessResult.MISS;
    }
}
