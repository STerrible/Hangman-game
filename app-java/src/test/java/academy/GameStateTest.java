package academy;

import academy.engine.GameState;
import academy.engine.GuessResult;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameStateTest {

    @Test
    void constructor_shouldThrow_onEmptyAnswer() {
        assertThatThrownBy(() -> new GameState("", 5))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void masked_initially_allStars() {
        GameState s = new GameState("apple", 6);
        assertThat(s.masked()).isEqualTo("*****");
    }

    @Test
    void guess_reveals_letters_and_isCaseInsensitive() {
        GameState s = new GameState("Apple", 6);

        GuessResult res = s.guess('a');
        assertThat(res).isEqualTo(GuessResult.HIT);
        assertThat(s.masked()).isEqualTo("a****");

        res = s.guess('P');
        assertThat(res).isEqualTo(GuessResult.HIT);
        assertThat(s.masked()).isEqualTo("app**");
    }

    @Test
    void wrongAttempts_increments_and_remainingAttempts() {
        GameState s = new GameState("dog", 3);
        assertThat(s.remainingAttempts()).isEqualTo(3);

        // промах
        GuessResult r = s.guess('x');
        assertThat(r).isEqualTo(GuessResult.MISS);
        assertThat(s.wrongAttempts()).isEqualTo(1);
        assertThat(s.remainingAttempts()).isEqualTo(2);

        // попадание не увеличивает счётчик ошибок
        r = s.guess('d');
        assertThat(r).isEqualTo(GuessResult.HIT);
        assertThat(s.wrongAttempts()).isEqualTo(1);
        assertThat(s.remainingAttempts()).isEqualTo(2);
    }

    @Test
    void isWon_and_isLost_behaviour() {
        GameState s = new GameState("ab", 2);

        assertThat(s.guess('a')).isEqualTo(GuessResult.HIT);
        assertThat(s.isWon()).isFalse();
        assertThat(s.isLost()).isFalse();

        assertThat(s.guess('x')).isEqualTo(GuessResult.MISS);
        assertThat(s.isLost()).isFalse();

        // второй промах -> достигнут лимит попыток
        assertThat(s.guess('y')).isEqualTo(GuessResult.MISS);
        assertThat(s.isLost()).isTrue();

        GameState t = new GameState("hi", 5);
        assertThat(t.guess('h')).isEqualTo(GuessResult.HIT);
        assertThat(t.guess('i')).isEqualTo(GuessResult.HIT);
        assertThat(t.isWon()).isTrue();
    }

    @Test
    void alreadyGuessed_preventsDoubleCounting() {
        GameState s = new GameState("abc", 3);
        assertThat(s.alreadyGuessed('a')).isFalse();

        assertThat(s.guess('a')).isEqualTo(GuessResult.HIT);
        assertThat(s.alreadyGuessed('a')).isTrue();

        int wrongBefore = s.wrongAttempts();

        // повторная та же буква -> ALREADY_GUESSED, ошибок не прибавляется
        assertThat(s.guess('a')).isEqualTo(GuessResult.ALREADY_GUESSED);
        assertThat(s.wrongAttempts()).isEqualTo(wrongBefore);
    }
}
