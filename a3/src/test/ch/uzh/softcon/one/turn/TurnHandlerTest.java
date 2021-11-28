package ch.uzh.softcon.one.turn;

import ch.uzh.softcon.one.abstraction.Board;
import ch.uzh.softcon.one.abstraction.GameHandling;
import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class TurnHandlerTest {

    private Turn turn;
    private Board boardInstance;

    @BeforeAll
    static void init() {
        try {
            Method register = GameHandling.class.getDeclaredMethod("registerObservers");
            register.setAccessible(true);
            register.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            fail(e.getMessage());
        }
    }

    @BeforeEach
    void setUp() {
        boardInstance = Board.getInstance();
        boardInstance.cleanBoard();
    }

    @Test
    void checkWinRed() {
        GameHandling.changePlayer(Player.RED);
        boardInstance.placePiece(3, 4, new Piece(Player.RED));
        boolean result = false;

        try {
            Method winMethod = TurnHandler.class.getDeclaredMethod("checkWin");
            winMethod.setAccessible(true);
            result = (boolean) winMethod.invoke(TurnHandler.class);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            fail(e.getMessage());
        }

        assertTrue(result, "Player Red should have won!");
    }

    @Test
    void checkWinWhite() {
        GameHandling.changePlayer(Player.WHITE);
        boardInstance.placePiece(3, 4, new Piece(Player.WHITE));
        boolean result = false;

        try {
            Method winMethod = TurnHandler.class.getDeclaredMethod("checkWin");
            winMethod.setAccessible(true);
            result = (boolean) winMethod.invoke(TurnHandler.class);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            fail(e.getMessage());
        }

        assertTrue(result, "Player White should have won!");
    }

    @Test
    void checkWinRedBecauseWhiteStuck() {
        GameHandling.changePlayer(Player.RED);
        boardInstance.placePiece(0, 1, new Piece(Player.WHITE));
        boardInstance.placePiece(1, 0, new Piece(Player.RED));
        boolean result = false;

        try {
            Method winMethod = TurnHandler.class.getDeclaredMethod("checkWin");
            winMethod.setAccessible(true);
            result = (boolean) winMethod.invoke(TurnHandler.class);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            fail(e.getMessage());
        }

        assertTrue(result, "Player Red should have won because White should be stuck!");
    }

    @Test
    void checkTransformIsNeededRed() {
        GameHandling.changePlayer(Player.RED);
        boardInstance.placePiece(5, 6, new Piece(Player.RED));
        turn = new Turn(5, 6, 6, 7);
        boolean result = false;

        try {
            Method transformMethod = TurnHandler.class.getDeclaredMethod("checkTransformNeeded", Turn.class);
            transformMethod.setAccessible(true);
            result = (boolean) transformMethod.invoke(TurnHandler.class, turn);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            fail();
        }

        assertTrue(result, "Transformation to king must occur");
    }

    @Test
    void checkTransformNotNeededWhite() {
        GameHandling.changePlayer(Player.WHITE);
        boardInstance.placePiece(3, 3, new Piece(Player.WHITE));
        turn = new Turn(3, 3, 4, 2);
        boolean result = true;

        try {
            Method transformMethod = TurnHandler.class.getDeclaredMethod("checkTransformNeeded", Turn.class);
            transformMethod.setAccessible(true);
            result = (boolean) transformMethod.invoke(TurnHandler.class, turn);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            fail();
        }

        assertFalse(result, "No transformation must occur.");
    }

    @Test
    void runTurnSequenceXYZ() {
    }
}