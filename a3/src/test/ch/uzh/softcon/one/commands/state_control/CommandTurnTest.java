package ch.uzh.softcon.one.commands.state_control;

import ch.uzh.softcon.one.abstraction.Board;
import ch.uzh.softcon.one.abstraction.GameHandling;
import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.turn.Turn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class CommandTurnTest {

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
        GameHandling.changePlayer(Player.RED);
    }

    @Test
    void checkExecute() {
        Piece piece = new Piece(Player.RED);
        boardInstance.placePiece(1, 1, piece);
        Turn turn = new Turn(1, 1, 2, 2);
        CommandTurn cmdTurn = new CommandTurn(turn, Player.RED, null);
        cmdTurn.execute();

        assertEquals(piece, boardInstance.getPiece(2, 2), "Piece is not at the destination!");
        assertNull(boardInstance.getPiece(1, 1));
    }

    @Test
    void checkUndo() {
        Piece red = new Piece(Player.RED);
        Piece enemy = new Piece(Player.WHITE);
        Piece white = new Piece(Player.WHITE);
        boardInstance.placePiece(4, 2, red);
        boardInstance.placePiece(5, 3, enemy);
        boardInstance.placePiece(7, 6, white);
        Turn turn1 = new Turn(4, 2, 6, 4);
        Turn turn2 = new Turn(7, 6, 6, 5);
        CommandTurn cmdTurn1 = new CommandTurn(turn1, Player.RED, enemy);
        cmdTurn1.execute();
        GameHandling.changePlayer(Player.WHITE);
        CommandTurn cmdTurn2 = new CommandTurn(turn2, Player.WHITE, null);
        cmdTurn2.execute();
        GameHandling.changePlayer(Player.RED);

        CommandTurn cmdUndo = new CommandTurn(null, null, null);
        cmdUndo.undo();

        System.out.println(boardInstance.getPiece(4, 2));
        System.out.println(red);

        assertEquals(red, boardInstance.getPiece(4, 2));
        assertNull(boardInstance.getPiece(6, 4));
        assertEquals(enemy, boardInstance.getPiece(5, 3));
        assertEquals(white, boardInstance.getPiece(7, 6));
        assertNull(boardInstance.getPiece(6, 5));
    }
}