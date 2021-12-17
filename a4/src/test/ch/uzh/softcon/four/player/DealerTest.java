package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ch.uzh.softcon.four.card.Card.*;

class DealerTest {

    Dealer testDealer;
    Card testCard1;
    Card testCard2;

    @BeforeEach
    void setUp() {
        testDealer = new Dealer();
        testCard1 = new Card(Suit.DIAMONDS, Rank.ACE);
        testCard2 = new Card(Suit.HEARTS, Rank.KING);
    }

    @Test
    void giveCard() {
        assertDoesNotThrow(() -> {

            testDealer.giveCard(testCard1);
            testDealer.giveCard(testCard2);
            assertEquals(testCard1, testDealer.getHand(0).getCard(0));
            assertEquals(testCard2, testDealer.getHand(0).getCard(1));

        }, "Should not generate exceptions?!");
    }
}