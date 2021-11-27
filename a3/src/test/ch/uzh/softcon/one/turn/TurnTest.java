package ch.uzh.softcon.one.turn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    private Turn turn;

    @BeforeEach
    void setUp() {
        turn = new Turn(1, 1, 2, 2);
    }

    @Test
    void from() {
        // Arrange
        Turn.TilePosition expected = new Turn.TilePosition(1, 1);

        // Act
        Turn.TilePosition result = turn.from();

        // Assert
        assertEquals(expected, result, "Turn.from() returns wrong result.");
    }

    @Test
    void to() {
        // Arrange
        Turn.TilePosition expected = new Turn.TilePosition(2, 2);

        // Act
        Turn.TilePosition result = turn.to();

        // Assert
        assertEquals(expected, result, "Turn.to() returns wrong result.");
    }
}