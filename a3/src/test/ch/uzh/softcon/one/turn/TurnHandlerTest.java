package ch.uzh.softcon.one.turn;

import ch.uzh.softcon.one.abstraction.Board;
import ch.uzh.softcon.one.abstraction.GameHandling;
import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.support.ReflectionSupport;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class TurnHandlerTest {

    private Turn turn;
    private Board boardInstance;

    @BeforeEach
    void setUp() {
        boardInstance = Board.getInstance();
        boardInstance.cleanBoard();
    }

    @Test
    void runTurnSequenceCheckTransformIsNeededRed() {
        boardInstance.placePiece(5, 6, new Piece(Player.RED));
        turn = new Turn(5, 6, 6, 7);

        boolean result = false;
        try {
            Method register = GameHandling.class.getDeclaredMethod("registerObservers");
            register.setAccessible(true);
            register.invoke(null);
            GameHandling.changePlayer(Player.RED);

            Method transformMethod = TurnHandler.class.getDeclaredMethod("checkTransformNeeded", Turn.class);
            transformMethod.setAccessible(true);
            result = (boolean) transformMethod.invoke(TurnHandler.class, turn);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            fail();
        }

        assertTrue(result);
    }

    @Test
    void runTurnSequenceCheckTransformNotNeededWhite() {
        boardInstance.placePiece(3, 3, new Piece(Player.WHITE));
        turn = new Turn(3, 3, 4, 2);

        boolean result = true;
        try {
            Method register = GameHandling.class.getDeclaredMethod("registerObservers");
            register.setAccessible(true);
            register.invoke(null);
            GameHandling.changePlayer(Player.WHITE);

            Method transformMethod = TurnHandler.class.getDeclaredMethod("checkTransformNeeded", Turn.class);
            transformMethod.setAccessible(true);
            result = (boolean) transformMethod.invoke(TurnHandler.class, turn);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            fail();
        }

        assertFalse(result);
    }

    @Test
    void runTurnSequenceXYZ() {
    }
}