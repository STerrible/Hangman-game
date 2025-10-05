package academy.engine;

public enum Difficulty {
    EASY, MEDIUM, HARD;

    // Выбирает случайный уровень сложности
    public static Difficulty random() {
        Difficulty[] values = values();
        return values[(int) (Math.random() * values.length)];
    }
}
