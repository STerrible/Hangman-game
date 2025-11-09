package academy.engine;

import java.util.Random;

public enum Difficulty {
    EASY(10, 0),
    MEDIUM(8, 2),
    HARD(6, 4);

    private final int maxAttempts;
    private final int stageOffset;

    Difficulty(int maxAttempts, int stageOffset) {
        this.maxAttempts = maxAttempts;
        this.stageOffset = stageOffset;
    }

    public int maxAttempts() {
        return maxAttempts;
    }

    public int stageOffset() {
        return stageOffset;
    }

    // Выбирает случайный уровень сложности
    public static Difficulty random() {
        Difficulty[] values = values();
        return values[new Random().nextInt(values.length)];
    }
}
