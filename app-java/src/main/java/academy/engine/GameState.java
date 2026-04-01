package academy.engine;

import java.util.HashSet;
import java.util.Set;

public class GameState {
    private final String answer;
    private final int maxAttempts;
    private final Set<Character> guessed = new HashSet<>();
    private int wrongAttempts = 0;

    public GameState(String answer, int maxAttempts) {
        if (answer == null || answer.trim().isEmpty()) {
            throw new IllegalArgumentException("Answer must not be null or empty");
        }
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("maxAttempts must be > 0");
        }
        this.answer = answer.trim().toLowerCase();
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

    public GuessResult guess(char c) {
        char lower = Character.toLowerCase(c);
        if (guessed.contains(lower)) return GuessResult.ALREADY_GUESSED;
        guessed.add(lower);
        if (answer.indexOf(lower) >= 0) {
            return GuessResult.HIT;
        } else {
            wrongAttempts++;
            return GuessResult.MISS;
        }
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
