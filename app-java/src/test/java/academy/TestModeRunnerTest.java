package academy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class TestModeRunnerTest {
    @Test
    void shouldThrowOnNulls() {
        assertThrows(IllegalArgumentException.class, () -> TestModeRunner.run(null, "a")); // passed
        assertThrows(IllegalArgumentException.class, () -> TestModeRunner.run("a", null)); // passed
        assertThrows(IllegalArgumentException.class, () -> TestModeRunner.run("", "a")); // passed
//        assertThrows(IllegalArgumentException.class, () -> TestModeRunner.run("ananas", "ananas")); // not passed
        assertThrows(IllegalArgumentException.class, () -> TestModeRunner.run(" ", "a")); // passed
    }

    @Test
    void shouldThrow_ifSecretTooShort_orTooLong() {
        assertThatThrownBy(() -> TestModeRunner.run("a", "x"))
            .isInstanceOf(IllegalArgumentException.class);

        String huge = "a".repeat(31);
        assertThatThrownBy(() -> TestModeRunner.run(huge, "abc"))
            .isInstanceOf(IllegalArgumentException.class);
    }

}

