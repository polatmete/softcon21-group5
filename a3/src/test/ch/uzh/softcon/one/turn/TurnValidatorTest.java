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

import static ch.uzh.softcon.one.turn.TurnValidator.validateMove;
import static org.junit.jupiter.api.Assertions.*;

class TurnValidatorTest {

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
        turn = null;
    }

    @Test
    void checkValidateMoveAnotherPieceInMultiJump() {
        GameHandling.changePlayer(Player.RED);
        Piece redInMultiJump = new Piece(Player.RED);
        boardInstance.placePiece(7, 2, redInMultiJump);
        boardInstance.placePiece(6, 3, new Piece(Player.WHITE));
        boardInstance.placePiece(2, 2, new Piece(Player.RED));
        redInMultiJump.startMultiJump();

        turn = new Turn(2, 2, 3, 3);

        boolean result = validateMove(turn, true);
        assertFalse(result, "Another Piece should already be in a multiJump!");
    }

    @Test
    void checkValidateMoveInvalidJump() {
        GameHandling.changePlayer(Player.RED);
        Piece redInMultiJump = new Piece(Player.RED);
        boardInstance.placePiece(7, 2, redInMultiJump);
        boardInstance.placePiece(6, 3, new Piece(Player.WHITE));
        redInMultiJump.startMultiJump();

        turn = new Turn(7, 2, 6, 3);

        boolean result = validateMove(turn, true);
        assertFalse(result, "Move should be an exact jump with distance of 2 for each axis!");
    }
}