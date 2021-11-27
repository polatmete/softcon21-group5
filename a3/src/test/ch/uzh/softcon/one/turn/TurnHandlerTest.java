package ch.uzh.softcon.one.turn;

import ch.uzh.softcon.one.abstraction.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnHandlerTest {

    private Turn turn;
    private Board boardInstance;

    @BeforeEach
    void setUp() {

    }

    @Test
    void runTurnSequenceCheckTransformNeeded() {
        turn = new Turn(5, 7, 6, 8);

        TurnHandler.runTurnSequence(turn);

        //assertEquals();
    }

    @Test
    void runTurnSequenceCheckTransformNotNeeded() {
    }

    @Test
    void runTurnSequenceXYZ() {
    }
}