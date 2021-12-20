package ch.uzh.softcon.four.card.scoreboard;

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
