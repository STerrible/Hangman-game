package academy;

import academy.engine.Difficulty;
import academy.engine.WordRepository;
import academy.engine.WordRepository.Category;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class WordRepositoryTest {

    @Test
    void pickRandom_returns_valid_pick_for_each_category_and_difficulty() {
        for (Difficulty d : Difficulty.values()) {
            for (Category c : Category.values()) {
                WordRepository.Pick p = WordRepository.pickRandom(c, d);

                // базовые инварианты результата
                assertThat(p).isNotNull();
                assertThat(p.word()).isNotBlank();
                assertThat(p.hint()).isNotNull();

                // метаданные соответствуют запросу
                assertThat(p.difficulty()).isEqualTo(d);
                assertThat(p.category()).isEqualTo(c);
            }
        }
    }

    @RepeatedTest(20)
    void pickRandom_is_stable_and_never_returns_empty_values() {
        // один фиксированный набор - просто гоняем много раз
        Difficulty d = Difficulty.MEDIUM;
        Category c   = Category.IT;

        for (int i = 0; i < 100; i++) {
            WordRepository.Pick p = WordRepository.pickRandom(c, d);
            assertThat(p.word()).isNotBlank();
            assertThat(p.hint()).isNotNull();
            assertThat(p.difficulty()).isEqualTo(d);
            assertThat(p.category()).isEqualTo(c);
        }
    }

    @Test
    void hints_are_consistent_for_the_same_word() {
        Map<String, String> seen = new HashMap<>();

        for (Difficulty d : Difficulty.values()) {
            for (Category c : Category.values()) {
                for (int i = 0; i < 1000; i++) {
                    WordRepository.Pick p = WordRepository.pickRandom(c, d);
                    String w = p.word();
                    String h = p.hint();

                    assertThat(h).isNotNull();

                    String prev = seen.putIfAbsent(w, h);
                    if (prev != null) {
                        assertThat(h).isEqualTo(prev);
                    }
                }
            }
        }
    }
}
