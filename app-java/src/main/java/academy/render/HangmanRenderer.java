package academy.render;

public final class HangmanRenderer {
    private static final String[] STAGES = {
        """



        _______
        """,
        """


        |
        |
        |
        _______
        """,
        """
        O
        |
        |
        |
        _______
        """,
        """
        O
        |
       /|\\
        |
        _______
        """,
        """
        O
        |
       /|\\
       /
        _______
        """,
        """
        O
        |
       /|\\
       / \\
        _______
        """
    };

    private HangmanRenderer() {}

    public static String render(int wrongAttempts) {
        int idx = Math.min(wrongAttempts, STAGES.length - 1);
        return STAGES[idx];
    }
}
