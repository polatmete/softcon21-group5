package ch.uzh.softcon.one.abstraction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void getColorRed() {
        Piece redPiece = new Piece(Player.RED);

        assertEquals(Player.RED, redPiece.getColor(), "Why is this piece not red?");
    }

    @Test
    void isNotKing() {
        Piece freshPiece = new Piece(Player.RED);

        assertFalse(freshPiece.isKing(), "Piece should be pawn");
    }

    @Test
    void promoteToKing() {
        Piece aspiringKing = new Piece(Player.RED);

        aspiringKing.promote();

        assertTrue(aspiringKing.isKing(), "Promotions usually turn pawn into king");
    }

    @Test
    void isNotInMultiJump() {
        Piece jumpyPiece = new Piece(Player.RED);

        assertFalse(jumpyPiece.isInMultiJump());
    }

    @Test
    void activeMultiJump() {
        Piece multiPiece = new Piece(Player.RED);

        multiPiece.startMultiJump();

        assertTrue(Piece.activeMultiJump());
    }

    @Test
    void startMultiJump() {
        Piece multiPiece = new Piece(Player.RED);

        multiPiece.startMultiJump();

        assertTrue(multiPiece.isInMultiJump());
    }

    @Test
    void endMultiJump() {
        Piece multiPiece = new Piece(Player.RED);

        multiPiece.startMultiJump();
        multiPiece.endMultiJump();

        assertFalse(multiPiece.isInMultiJump());
    }
}