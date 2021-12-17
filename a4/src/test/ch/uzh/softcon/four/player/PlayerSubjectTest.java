package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.Hand;
import ch.uzh.softcon.four.exceptions.card.NullCardException;
import ch.uzh.softcon.four.exceptions.hand.NullHandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ch.uzh.softcon.four.card.Card.*;

class PlayerSubjectTest {

    Player testPlayer;
    Hand testHand;

    @BeforeEach
    void setUp() {
        testPlayer = new Player("testSubject");
        testHand = testPlayer.getHands().get(0);
        testHand.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
    }

    @Test
    void getHand() {
        Hand hand;
        try {
            hand = testPlayer.getHand(0);
        } catch (NullHandException e) {
            hand = null;
        }
        assertEquals(testHand, hand);

        assertThrows(NullHandException.class, () ->
                testPlayer.getHand(1),
                "This should throw a NullHandException!");
    }

    @Test
    void clearHands() {
        assertThrows(NullCardException.class, () -> {

            testPlayer.clearHands();
            testPlayer.getHand(0).getCard(0);

        }, "This should throw a NullCardException!");
    }
}