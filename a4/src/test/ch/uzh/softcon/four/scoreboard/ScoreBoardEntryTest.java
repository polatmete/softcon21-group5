package ch.uzh.softcon.four.scoreboard;

import ch.uzh.softcon.four.scoreboard.ScoreBoardEntry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardEntryTest {

    @Test
    void balanceTest() {
        ScoreBoardEntry s = new ScoreBoardEntry("test", 100);
        assertEquals(100, s.balance());
    }

    @Test
    void getNameTest() {
        ScoreBoardEntry s = new ScoreBoardEntry("test", 100);
        assertEquals("test", s.getName());
    }
}
