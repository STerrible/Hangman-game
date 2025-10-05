package academy.io;

import academy.engine.Difficulty;
import academy.engine.GameState;
import academy.render.HangmanRenderer;

import java.util.Scanner;

public final class ConsoleUI {

    public static void runInteractive(String secret) {
        Scanner sc = new Scanner(System.in);

        Difficulty difficulty = promptDifficulty(sc);

        int maxAttempts = switch (difficulty) {
            case EASY -> 10;
            case MEDIUM ->8;
            case HARD -> 6;
        };

        GameState state = new GameState(secret, maxAttempts);

        while (!state.isWon() && !state.isLost()) {
            System.out.println("Word: " + state.masked());
            System.out.println(HangmanRenderer.render(state.wrongAttempts(), difficulty));
            System.out.println("Remaining attempts: " + state.remainingAttempts());

            System.out.print("Enter a letter: ");
            String line = sc.nextLine().trim();

            // сообщение об ошибках
            if (line.isEmpty()) {
                System.out.println("(!!!) You entered nothing, try again");
                continue;
            }

            if (line.length() > 1) {
                System.out.println("(!!!) Enter a single letter, try again");
                continue;
            }

            char letter = line.charAt(0);

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
