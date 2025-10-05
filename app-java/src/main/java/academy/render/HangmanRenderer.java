package academy.render;

import academy.engine.Difficulty;

public final class HangmanRenderer {
    private static final String[] STAGES = {
        """
    """,
        """

         |
         |
         |
        ===
    """,
        """
         +
         |
         |
         |
        ===
    """,
        """
      ---+
         |
         |
         |
        ===
    """,
        """
     +---+
         |
         |
         |
        ===
    """,
        """
     +---+
     O   |
         |
         |
        ===
    """,
        """
     +---+
     O   |
     |   |
         |
        ===
    """,
        """
     +---+
     O   |
    /|   |
         |
        ===
    """,
        """
     +---+
     O   |
    /|\\  |
         |
        ===
    """,
        """
     +---+
     O   |
    /|\\  |
    /    |
        ===
    """,
        """
     +---+
     O   |
    /|\\  |
    / \\  |
        ===
    """
    };

    private HangmanRenderer() {}

    public static String render(int wrongAttempts, Difficulty difficulty) {
        int offset;
        switch (difficulty) {
            case EASY -> offset = 0;   // начинаем с нулевого этапа
            case MEDIUM -> offset = 2; // средняя сложность — этапы 4–10
            case HARD -> offset = 4;   // самая сложная — этапы 6–10
            default -> offset = 0;
        }
        int idx = Math.min(offset + wrongAttempts, STAGES.length - 1);
        return "\n" + STAGES[idx];
    }
}
