package academy;

import academy.engine.GameState;
import academy.engine.WordRepository.Pick;

/**
 * GameSession — контейнер одной игровой сессии.
 * Хранит выбранный Pick (word + hint + category + difficulty),
 * внутренний GameState и флаг использования подсказки.
 */
public final class GameSession {

    private final Pick pick;
    private final GameState state;
    private boolean hintUsed = false;

    public GameSession(Pick pick, int maxAttempts) {
        if (pick == null) throw new IllegalArgumentException("pick must not be null");
        this.pick = pick;
        this.state = new GameState(pick.word(), maxAttempts);
    }

    /** Возвращает Pick (слово + подсказка + метаданные). */
    public Pick pick() {
        return pick;
    }

    /** Возвращает внутреннее состояние игры. */
    public GameState state() {
        return state;
    }

    /** Проверяет — использована ли подсказка в этой сессии. */
    public boolean isHintUsed() {
        return hintUsed;
    }

    /** Пометить подсказку как использованную и вернуть подсказку. */
    public String useHint() {
        if (hintUsed) return null;
        hintUsed = true;
        return pick.hint();
    }

    /** Быстрый доступ к секретному слову. */
    public String secret() {
        return pick.word();
    }

    /** Максимально допустимое число попыток в сессии */
    public int maxAttempts() {
        return state.getMaxAttempts();
    }
}
