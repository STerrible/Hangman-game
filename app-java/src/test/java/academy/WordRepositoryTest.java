package academy;

import academy.engine.Difficulty;
import academy.engine.WordRepository;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class WordRepositoryTest {

    @Test
    void pickRandom_returns_nonNullPick() {
        WordRepository.Pick p = WordRepository.pickRandom(WordRepository.Category.FRUITS, Difficulty.EASY);
        assertThat(p).isNotNull();
        assertThat(p.word()).isNotBlank();
        assertThat(p.difficulty()).isEqualTo(Difficulty.EASY);
        assertThat(p.category()).isEqualTo(WordRepository.Category.FRUITS);
        assertThat(p.hint()).isNotNull(); // hint present
    }

    // run a few times to make sure randomness still returns valid picks
    @RepeatedTest(20)
    void pickRandom_various_combinations_noErrors() {
        for (Difficulty d : Difficulty.values()) {
            for (WordRepository.Category c : WordRepository.Category.values()) {
                WordRepository.Pick p = WordRepository.pickRandom(c, d);
                assertThat(p.word()).isNotBlank();
                assertThat(p.category()).isEqualTo(c);
                assertThat(p.difficulty()).isEqualTo(d);
                assertThat(p.hint()).isNotNull();
            }
        }
    }
}
