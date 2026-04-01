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
        int baseStage = difficulty.stageOffset(); // теперь из enum
        int stageIndex = baseStage + wrongAttempts;

        if (stageIndex >= STAGES.length) {
            stageIndex = STAGES.length - 1;
        }

        return "\n" + STAGES[stageIndex];
    }
}
