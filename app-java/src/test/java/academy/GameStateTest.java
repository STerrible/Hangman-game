package academy;

import academy.engine.GameState;
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

        // guess lowercase 'a' should reveal 'A' at position 0 (case-insensitive)
        boolean res = s.guess('a');
        assertThat(res).isTrue();
        assertThat(s.masked()).isEqualTo("A****");

        // guess uppercase 'P' should reveal both p's
        res = s.guess('P');
        assertThat(res).isTrue();
        assertThat(s.masked()).isEqualTo("App**");
    }

    @Test
    void wrongAttempts_increments_and_remainingAttempts() {
        GameState s = new GameState("dog", 3);
        assertThat(s.remainingAttempts()).isEqualTo(3);

        // wrong guess
        boolean ok = s.guess('x');
        assertThat(ok).isFalse();
        assertThat(s.wrongAttempts()).isEqualTo(1);
        assertThat(s.remainingAttempts()).isEqualTo(2);

        // correct guess does not increment
        ok = s.guess('d');
        assertThat(ok).isTrue();
        assertThat(s.wrongAttempts()).isEqualTo(1);
        assertThat(s.remainingAttempts()).isEqualTo(2);
    }

    @Test
    void isWon_and_isLost_behaviour() {
        GameState s = new GameState("ab", 2);

        // guess one correct
        s.guess('a');
        assertThat(s.isWon()).isFalse();
        assertThat(s.isLost()).isFalse();

        // wrong guess #1
        s.guess('x');
        assertThat(s.isLost()).isFalse();

        // wrong guess #2 -> reached maxAttempts
        s.guess('y');
        assertThat(s.isLost()).isTrue();

        // new state for win
        GameState t = new GameState("hi", 5);
        t.guess('h');
        t.guess('i');
        assertThat(t.isWon()).isTrue();
    }

    @Test
    void alreadyGuessed_preventsDoubleCounting() {
        GameState s = new GameState("abc", 3);
        assertThat(s.alreadyGuessed('a')).isFalse();
        s.guess('a');
        assertThat(s.alreadyGuessed('a')).isTrue();

        // guessing again should not increment wrongAttempts (and should be treated as no-op)
        int wrongBefore = s.wrongAttempts();
        s.guess('a'); // repeated
        assertThat(s.wrongAttempts()).isEqualTo(wrongBefore);
    }
}
