package ch.uzh.softcon.one.utils;

import ch.uzh.softcon.one.abstraction.Board;
import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardLoaderTest {

    @BeforeEach
    void setUp() {
        Board.getInstance().cleanBoard();
    }

    @Test
    void loadBoardInitialFile() {
        try {
            assertTrue(BoardLoader.loadBoard("initialBoard.csv"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void loadBoardInitialFileMultiMultiJump() {
        assertThrows(Exception.class, () -> BoardLoader.loadBoard("testBoard_active_multi.csv"));
    }

    @Test
    void loadBoardInitialFileCorruptActivePlayer() {
        assertThrows(Exception.class, () -> BoardLoader.loadBoard("testBoard_FAIL.csv"));
    }

    @Test
    void loadBoardInitialFileWhiteAndEmptyLine() {
        try {
            assertTrue(BoardLoader.loadBoard("testBoard_WHITE.csv"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void loadBoardNonExistentFile() {
        try {
            assertFalse(BoardLoader.loadBoard(""));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void loadBoardEmptyFileName() {
        try {
            assertTrue(BoardLoader.loadBoard(null));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void saveBoard() {
        Board.getInstance().placePiece(1,0, new Piece(Player.RED));
        Board.getInstance().placePiece(2,1, new Piece(Player.WHITE));
        Piece king = new Piece(Player.RED);
        king.promote();
        Board.getInstance().placePiece(4,1, king);
        BoardLoader.saveBoard("testBoard_save");
        String actual = null;
        File file = new File("testBoard_save.csv").getAbsoluteFile();
        if (file.exists()) {
            try {
                actual = Files.readString(file.toPath()).replace("\r\n", "\n");
            } catch (IOException e) {
                fail();
            }
        }
        String expected = """
                [   ],[R_P],[   ],[   ],[   ],[   ],[   ],[   ],
                [   ],[   ],[W_P],[   ],[R_K],[   ],[   ],[   ],
                [   ],[   ],[   ],[   ],[   ],[   ],[   ],[   ],
                [   ],[   ],[   ],[   ],[   ],[   ],[   ],[   ],
                [   ],[   ],[   ],[   ],[   ],[   ],[   ],[   ],
                [   ],[   ],[   ],[   ],[   ],[   ],[   ],[   ],
                [   ],[   ],[   ],[   ],[   ],[   ],[   ],[   ],
                [   ],[   ],[   ],[   ],[   ],[   ],[   ],[   ],
                activePlayer:""";
        file.deleteOnExit();
        assert actual != null;
        assertEquals(expected, actual);
    }
}