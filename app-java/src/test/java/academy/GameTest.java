package academy;

import academy.engine.GameState;
import academy.engine.GuessResult;
import academy.io.ConsoleUI;
import org.junit.jupiter.api.Test;
import academy.engine.WordRepository;
import academy.engine.Difficulty;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    private static GameSession fixedSession() {
        WordRepository.Pick pick = new WordRepository.Pick(
            "apple",
            "test-hint",
            Difficulty.EASY,
            WordRepository.Category.IT
        );
        return new GameSession(pick, 3);
    }

    // по новой прогоню тесты,
    @Test
    void processInputLine_updates_state_on_hit_and_miss_and_ignores_garbage() {
        GameSession session = fixedSession();
        GameState s = session.state();

        // старт
        assertThat(s.masked()).isEqualTo("*****");
        assertThat(s.wrongAttempts()).isEqualTo(0);
        assertThat(s.remainingAttempts()).isEqualTo(3);

        // мусор (>1 символ) -> состояния не меняется
        boolean changed = ConsoleUI.processInputLine(session, "ab");
        assertThat(changed).isFalse();
        assertThat(s.masked()).isEqualTo("*****");
        assertThat(s.wrongAttempts()).isEqualTo(0);

        // не буква -> игнор, состояния не меняется
        changed = ConsoleUI.processInputLine(session, "1");
        assertThat(changed).isFalse();
        assertThat(s.masked()).isEqualTo("*****");
        assertThat(s.wrongAttempts()).isEqualTo(0);

        // MISS: неверная буква увеличивает ошибки
        GuessResult r = ConsoleUI.processInputLineResult(session, "z");
        assertThat(r).isEqualTo(GuessResult.MISS);
        assertThat(s.masked()).isEqualTo("*****");
        assertThat(s.wrongAttempts()).isEqualTo(1);
        assertThat(s.remainingAttempts()).isEqualTo(2);

        // HIT: верная буква обновляет маску, ошибки не растут
        r = ConsoleUI.processInputLineResult(session, "A"); // регистр не важен
        assertThat(r).isEqualTo(GuessResult.HIT);
        assertThat(s.masked()).isEqualTo("a****");
        assertThat(s.wrongAttempts()).isEqualTo(1);
        assertThat(s.remainingAttempts()).isEqualTo(2);

        // повтор той же буквы -> ALREADY_GUESSED, без изменений
        int wrongBefore = s.wrongAttempts();
        String maskedBefore = s.masked();
        r = ConsoleUI.processInputLineResult(session, "a");
        assertThat(r).isEqualTo(GuessResult.ALREADY_GUESSED);
        assertThat(s.masked()).isEqualTo(maskedBefore);
        assertThat(s.wrongAttempts()).isEqualTo(wrongBefore);
    }

    @Test
    void hint_does_not_consume_attempts() {
        var pick = WordRepository.pickRandom(WordRepository.Category.FRUITS, Difficulty.EASY);
        GameSession session = new GameSession(pick, 3);
        GameState s = session.state();

        int wrongBefore = s.wrongAttempts();

        // первая подсказка сработает
        assertThat(ConsoleUI.processInputLineResult(session, "hint")).isNull();
        assertThat(s.wrongAttempts()).isEqualTo(wrongBefore);

        // повторная подсказка не изменяет состояние
        assertThat(ConsoleUI.processInputLineResult(session, "hint")).isNull();
        assertThat(s.wrongAttempts()).isEqualTo(wrongBefore);

        // валидный ход по-прежнему работает
        GuessResult r = ConsoleUI.processInputLineResult(session, String.valueOf(pick.word().charAt(0)));
        assertThat(r == GuessResult.HIT || r == GuessResult.MISS).isTrue();
    }
}
