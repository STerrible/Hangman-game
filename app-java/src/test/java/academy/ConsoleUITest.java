package academy;

import static org.assertj.core.api.Assertions.assertThat;

import academy.engine.Difficulty;
import academy.engine.GameState;
import academy.engine.WordRepository;
import academy.io.ConsoleUI;
import org.junit.jupiter.api.Test;

public class ConsoleUITest {

    @Test
    void multiCharInput_doesNotChangeState() {
        // Берём случайное слово из репозитория
        WordRepository.Pick pick = WordRepository.pickRandom(WordRepository.Category.FRUITS, Difficulty.EASY);

        // Создаём игровую сессию
        GameSession session = new GameSession(pick, 6);
        GameState state = session.state();

        String beforeMasked = state.masked();
        int beforeWrong = state.wrongAttempts();

        // Проверяем, что ввод >1 символа не меняет состояние
        boolean changed = ConsoleUI.processInputLine(session, "ab");
        assertThat(changed).isFalse();
        assertThat(state.masked()).isEqualTo(beforeMasked);
        assertThat(state.wrongAttempts()).isEqualTo(beforeWrong);
    }
}
