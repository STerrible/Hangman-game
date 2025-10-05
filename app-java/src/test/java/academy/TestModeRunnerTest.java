package academy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestModeRunnerTest {
    @Test
    void shouldThrowOnNulls() {
        assertThrows(IllegalArgumentException.class, () -> TestModeRunner.run(null, "a")); // passed
        assertThrows(IllegalArgumentException.class, () -> TestModeRunner.run("a", null)); // passed
        assertThrows(IllegalArgumentException.class, () -> TestModeRunner.run("", "a")); // passed
//        assertThrows(IllegalArgumentException.class, () -> TestModeRunner.run("ananas", "ananas")); // not passed
        assertThrows(IllegalArgumentException.class, () -> TestModeRunner.run(" ", "a")); // passed
    }
}

