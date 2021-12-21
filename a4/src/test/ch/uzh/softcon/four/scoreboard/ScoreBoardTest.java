package ch.uzh.softcon.four.scoreboard;

import ch.uzh.softcon.four.player.Player;
import ch.uzh.softcon.four.scoreboard.ScoreBoard;
import ch.uzh.softcon.four.scoreboard.ScoreBoardEntry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

    @BeforeEach
    void setUp() {
        ScoreBoard.resetScoreBoard(true);
    }

    @AfterEach
    void cleanUp() {
        ScoreBoard.resetScoreBoard(true);
    }

    @Test
    void saveScoreTest() {
        Player p = new Player("test");
        ScoreBoard.saveScore(p);

        for (int i = 0; i < ScoreBoard.size(); i++) {
            ScoreBoardEntry s = ScoreBoard.getScoreBoardEntry(i);
            if (i == 0) {
                assertEquals(100, s.balance());
                assertEquals("test", s.getName());
            } else {
                assertNull(s);
            }
            i++;
        }
    }

    @Test
    void resetScoreBoardTest() {
        Player p = new Player("test");
        ScoreBoard.saveScore(p);

        ScoreBoard.resetScoreBoard(false);

        for (int i = 0; i < ScoreBoard.size(); i++) {
            ScoreBoardEntry s = ScoreBoard.getScoreBoardEntry(i);
            assertNull(s);
        }
    }

    @Test
    void loadScoreTest() {

        // Fill scoreboard with one entry and update CSV

        Player p = new Player("test");
        ScoreBoard.saveScore(p);

        // Reset scoreboard, but don't reset CSV

        ScoreBoard.resetScoreBoard(false);

        // Load score from CSV via printScore() method, which calls loadScore()

        ScoreBoard.printScore();

        // Check if first element is there, and if all other entries are null

        for (int i = 0; i < ScoreBoard.size(); i++) {
            ScoreBoardEntry s = ScoreBoard.getScoreBoardEntry(i);
            if (i == 0) {
                assertEquals(100, s.balance());
                assertEquals("test", s.getName());
            } else {
                assertNull(s);
            }
            i++;
        }
    }

}
