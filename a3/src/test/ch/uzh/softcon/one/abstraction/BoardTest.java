package ch.uzh.softcon.one.abstraction;

import ch.uzh.softcon.one.turn.Turn;
import ch.uzh.softcon.one.utils.MoveStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Turn turn;
    private Board boardInstance;

    @BeforeEach
    void setUp() {
        boardInstance = Board.getInstance();
        boardInstance.cleanBoard();
    }

    @Test
    void placePieceNotPossible() {
        try {
            Piece piece = new Piece(Player.RED);
            boardInstance.initialize();
            boardInstance.placePiece(1, 1, piece);
        } catch (Exception e) {
            fail("Placing piece on occupied tile is not handled");
        }
    }

    @Test
    void generateOriginalBoard() {
        Piece[][] extractedBoard = null;
        try {
            Constructor<Board> boardCtor = Board.class.getDeclaredConstructor();
            boardCtor.setAccessible(true);
            Method createOriginalBoard = boardCtor.newInstance().getClass()
                    .getDeclaredMethod("createOriginalBoard");
            createOriginalBoard.setAccessible(true);
            createOriginalBoard.invoke(Board.getInstance());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            fail(e);
        }
        Piece[][] expected = new Piece[][]{
            {null, new Piece(Player.RED), null, new Piece(Player.RED), null, new Piece(Player.RED), null, new Piece(Player.RED)},
            {new Piece(Player.RED), null, new Piece(Player.RED), null, new Piece(Player.RED), null, new Piece(Player.RED), null},
            {null, new Piece(Player.RED), null, new Piece(Player.RED), null, new Piece(Player.RED), null, new Piece(Player.RED)},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {new Piece(Player.WHITE), null, new Piece(Player.WHITE), null, new Piece(Player.WHITE), null, new Piece(Player.WHITE), null},
            {null, new Piece(Player.WHITE), null, new Piece(Player.WHITE), null, new Piece(Player.WHITE), null, new Piece(Player.WHITE)},
            {new Piece(Player.WHITE), null, new Piece(Player.WHITE), null, new Piece(Player.WHITE), null, new Piece(Player.WHITE), null}
        };
        try {
            Field board = Board.class.getDeclaredField("board");
            board.setAccessible(true);
            extractedBoard = (Piece[][]) board.get(Board.getInstance());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
        for (int i = 0; i < 8; ++i) for (int j = 0; j < 8; ++j) if (expected[j][i] != extractedBoard[i][j]) assertTrue(expected[j][i].getColor() == extractedBoard[i][j].getColor() && expected[j][i].isKing() == extractedBoard[i][j].isKing());
    }

    @Test
    void hasNoPiecesRedTrue() {
        boardInstance.placePiece(6, 7, new Piece(Player.WHITE));
        assertTrue(boardInstance.hasNoPieces(Player.RED));
    }

    @Test
    void hasNoPiecesRedFalse() {
        boardInstance.placePiece(1, 1, new Piece(Player.RED));
        assertFalse(boardInstance.hasNoPieces(Player.RED));
    }

    @Test
    void hasNoPiecesWhiteTrue() {
        boardInstance.placePiece(1, 1, new Piece(Player.RED));
        assertTrue(boardInstance.hasNoPieces(Player.WHITE));
    }

    @Test
    void hasNoPiecesWhiteFalse() {
        boardInstance.placePiece(6, 7, new Piece(Player.WHITE));
        assertFalse(boardInstance.hasNoPieces(Player.WHITE));
    }

    @Test
    void getPiece() {
        Piece piece = new Piece(Player.RED);
        boardInstance.placePiece(1, 1, piece);
        assertEquals(boardInstance.getPiece(1, 1), piece);
    }

    @Test
    void getPieceNull() {
        assertNull(boardInstance.getPiece(1, 1));
    }

    @Test
    void cleanBoardRedPieceCountZero() {
        assertTrue(boardInstance.hasNoPieces(Player.RED));
    }

    @Test
    void cleanBoardWhitePieceCountZero() {
        assertTrue(boardInstance.hasNoPieces(Player.WHITE));
    }

    @Test
    void cleanBoardAllPiecesNull() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (boardInstance.getPiece(x, y) != null)
                    fail("Cleaned board contains pieces");
            }
        }
    }

    @Test
    void checkSize() {
        assertEquals(8, boardInstance.size());
    }

    @Test
    void removePieceRed() {
        boardInstance.placePiece(1, 1, new Piece(Player.RED));
        boardInstance.removePiece(1, 1);
        assertNull(boardInstance.getPiece(1, 1));
    }

    @Test
    void removePieceWhite() {
        boardInstance.placePiece(1, 1, new Piece(Player.WHITE));
        boardInstance.removePiece(1, 1);
        assertNull(boardInstance.getPiece(1, 1));
    }

    @Test
    void removePieceNull() {
        try {
            boardInstance.removePiece(1, 1);
        } catch (Exception e) {
            fail("Removing null piece is not handled");
        }
    }

    @Test
    void movePieceCheckDestination() {
        Piece piece = new Piece(Player.RED);
        boardInstance.placePiece(1, 1, piece);
        turn = new Turn(1, 1, 2, 2);
        boardInstance.movePiece(turn);
        assertEquals(boardInstance.getPiece(2, 2), piece);
    }

    @Test
    void movePieceCheckOriginNull() {
        Piece piece = new Piece(Player.RED);
        boardInstance.placePiece(1, 1, piece);
        turn = new Turn(1, 1, 2, 2);
        boardInstance.movePiece(turn);
        assertNull(boardInstance.getPiece(1, 1));
    }
}
