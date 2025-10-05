package academy.engine;

import java.util.HashSet;
import java.util.Set;

public class GameState {
    private final String answer;
    private final int maxAttempts;
    private final Set<Character> guessed = new HashSet<>();
    private int wrongAttempts = 0;

    public GameState(String answer, int maxAttempts) {
        if (answer == null || answer.isEmpty()) {
            throw new IllegalArgumentException("Answer must nut be null or empty");
        }
        this.answer = answer;
        this.maxAttempts = maxAttempts;
    }

    public String masked() {
        StringBuilder sb = new StringBuilder();
        for (char c : answer.toCharArray()) {
            if (guessed.contains(Character.toLowerCase(c))) sb.append(c);
            else sb.append('*');
        }
        return sb.toString();
    }

    public boolean isWon() {
        return masked().indexOf('*') < 0;
    }

    public boolean isLost() {
        return wrongAttempts >= maxAttempts;
    }

    public int remainingAttempts() {
        return maxAttempts - wrongAttempts;
    }

    public boolean guess(char c) {
        char lower = Character.toLowerCase(c);
        if (guessed.contains(lower)) return false;
        guessed.add(lower);
        boolean correct = answer.toLowerCase().indexOf(lower) >= 0;
        if (!correct) wrongAttempts++;
        return correct;
    }
    public int wrongAttempts() {
        return wrongAttempts;
    }
    public boolean alreadyGuessed(char c) {
        return guessed.contains(Character.toLowerCase(c));
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

}
