package academy.engine;

public class GameEngine {

    public static GameState startNew(String answer, int maxAttempts) {
        return new GameState(answer, maxAttempts);
    }
}
