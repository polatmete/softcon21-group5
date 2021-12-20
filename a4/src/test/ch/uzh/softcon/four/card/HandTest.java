package ch.uzh.softcon.four.card;

import ch.uzh.softcon.four.exceptions.card.NullCardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ch.uzh.softcon.four.card.Card.*;

class HandTest {

    Hand testHand;
    Card testCard;

    @BeforeEach
    void setUp() {
        testHand = new Hand();
        testCard = new Card(Suit.DIAMONDS, Rank.ACE);
    }

    @Test
    void getCard() {
        testHand.addCard(testCard);
        assertDoesNotThrow(() -> assertEquals(
                testCard, testHand.getCard(0)),
                "Should not generate exceptions?!");

        assertThrows(NullCardException.class, () ->
                testHand.getCard(1),
                "This should throw a NullCardException!");
    }

    @Test
    void addCard() {
        assertDoesNotThrow(() -> {

            testHand.addCard(testCard);
            assertEquals(testCard, testHand.getCard(0));

        }, "Should not generate exceptions?!");
    }

    @Test
    void reveal() {
        testCard.hide();
        testHand.addCard(testCard);
        assertEquals(0, testHand.points());
        testHand.reveal();
        assertEquals(11, testHand.points());
    }

    @Test
    void size() {
        testHand.addCard(testCard);
        assertEquals(1, testHand.size());
    }

    @Test
    void points() {
        testHand.addCard(testCard);
        assertEquals(11, testHand.points());
    }
}