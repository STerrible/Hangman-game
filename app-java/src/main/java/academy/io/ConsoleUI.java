package academy.io;

import academy.engine.GameState;
import academy.render.HangmanRenderer;

import java.util.Scanner;

public final class ConsoleUI {

    public static void runInteractive(String secret, int maxAttempts) {
        GameState state = new GameState(secret, maxAttempts);
        Scanner sc = new Scanner(System.in);

        while (!state.isWon() && !state.isLost()) {
            System.out.println("Word: " + state.masked());
            System.out.println(HangmanRenderer.render(state.remainingAttempts()));
            System.out.println("Remaining attempts: " + state.remainingAttempts());
            System.out.print("Enter a letter: ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            if (line.length() > 1) {
                System.out.println("Enter a single letter, try again.");
                continue;
            }
            state.guess(line.charAt(0));
        }

        System.out.println("Final: " + state.masked());
        if (state.isWon()) System.out.println("You won!");
        else System.out.println("You lost!");
    }
}
