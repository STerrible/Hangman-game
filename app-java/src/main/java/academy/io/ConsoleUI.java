package academy.io;

import academy.engine.Difficulty;
import academy.engine.WordRepository.Category;
import academy.engine.GameState;
import academy.engine.WordRepository;
import academy.render.HangmanRenderer;

import java.util.Scanner;

public final class ConsoleUI {

    public static void runInteractive() {
        Scanner sc = new Scanner(System.in);

        WordRepository.Category category = promptCategory(sc);

        Difficulty difficulty = promptDifficulty(sc);

        int maxAttempts = switch (difficulty) {
            case EASY -> 10;
            case MEDIUM ->8;
            case HARD -> 6;
        };

        WordRepository.Pick pick = WordRepository.pickRandom(category, difficulty);
        String secret = pick.word();
        String hint = pick.hint();
        System.out.printf("Category: %s, Difficulty: %s, word length %d%n", pick.category(), pick.difficulty(), secret.length());

        GameState state = new GameState(secret, maxAttempts);

        boolean hintUsed = false;

        while (!state.isWon() && !state.isLost()) {
            System.out.println("Word: " + state.masked());
            System.out.println(HangmanRenderer.render(state.wrongAttempts(), difficulty));
            System.out.println("Remaining attempts: " + state.remainingAttempts());

            System.out.print("Enter a letter or enter 'hint': ");
            String line = sc.nextLine().trim();

            // сообщение об ошибках
            if (line.isEmpty()) {
                System.out.println("(!!!) You entered nothing, try again");
                continue;
            }

            // обработка команды hint (без учёта регистра)
            if (line.equalsIgnoreCase("hint")) {
                if (hintUsed) {
                    System.out.println("Подсказка уже использована.");
                } else {
                    System.out.println("Подсказка: " + hint);
                    hintUsed = true;
                }
                // подсказка не тратит попытку, переходим к следующей итерации
                continue;
            }

            if (line.length() > 1) {
                System.out.println("(!!!) Enter a single letter, try again");
                continue;
            }

            char letter = line.charAt(0);

            // Проверка: только английские буквы
            if (!Character.isLetter(letter) || (letter < 'A' || (letter > 'Z' && letter < 'a') || letter > 'z')) {
                System.out.println("Please, enter only english letters (A–Z).");
                continue;
            }

            if (state.arlreadyGuessed((letter))) {
                System.out.println("(!!!) You already guessed this letter. Try another one.");
                continue;
            }

            state.guess(letter);
        }

        System.out.println("Final: " + state.masked());
        System.out.println(HangmanRenderer.render(state.wrongAttempts(), difficulty));
        if (state.isWon()) System.out.println("You won!");
        else System.out.println("You lost!");
    }

    private static Category promptCategory(Scanner sc) {
        System.out.println("Choose category: IT, ANIMALS, FRUITS (Enter = random):");
        String input = sc.nextLine().trim().toUpperCase();
        if (input.isEmpty()) {
            // random
            Category[] arr = Category.values();
            return arr[(int) (Math.random() * arr.length)];
        }
        try {
            return Category.valueOf(input);
        } catch (IllegalArgumentException ex) {
            System.out.println("Unknown category. Enter category name again.");
            return promptCategory(sc);
        }
    }

    private static Difficulty promptDifficulty(Scanner sc) {
        System.out.println("Choose difficulty: 1 or EASY, 2 or MEDIUM, 3 or HARD (or press enter for random):" + "\n");
        String input = sc.nextLine().trim().toUpperCase();
        return switch (input) {
            case "EASY" -> Difficulty.EASY;
            case "1" -> Difficulty.EASY;
            case "MEDIUM" -> Difficulty.MEDIUM;
            case "2" -> Difficulty.MEDIUM;
            case "HARD" -> Difficulty.HARD;
            case "3" -> Difficulty.HARD;
            default -> {
                Difficulty[] values = Difficulty.values();
                int idx = (int) (Math.random() * values.length);
                yield values[idx];
            }
        };
    }
}
