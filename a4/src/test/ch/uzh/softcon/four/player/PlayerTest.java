package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.CardDeck;
import ch.uzh.softcon.four.card.Hand;
import ch.uzh.softcon.four.exceptions.card.CardsNotEqualRankException;
import ch.uzh.softcon.four.exceptions.hand.HandWrongSizeException;
import ch.uzh.softcon.four.exceptions.hand.MaxHandSplitException;
import ch.uzh.softcon.four.exceptions.hand.NoSuchHandException;
import ch.uzh.softcon.four.exceptions.hand.NullHandException;
import ch.uzh.softcon.four.exceptions.player.BrokeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ch.uzh.softcon.four.card.Card.*;

class PlayerTest {

    static CardDeck deckInstance;
    Player testPlayer;
    Hand testHand;
    static Card testCard;

    @BeforeAll
    static void beforeAll() {
        deckInstance = CardDeck.getInstance();
        deckInstance.fillDeck(1);
        testCard = new Card(Suit.HEARTS, Rank.ACE);
    }

    @BeforeEach
    void setUp() {
        testPlayer = new Player("testSubject");
        testHand = testPlayer.getHands().get(0);
        testHand.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
    }

    @Test
    void giveCard() {
        assertDoesNotThrow(() -> {

            testPlayer.giveCard(testCard, testHand);
            testPlayer.getHand(0).getCard(1);

        }, "Should not generate exceptions?!");

        assertThrows(NullHandException.class, () ->
                testPlayer.giveCard(testCard, null),
                "This should throw a NullHandException!");

        assertThrows(NoSuchHandException.class, () ->
                testPlayer.giveCard(testCard, new Player("new").getHand(0)),
                "This should throw a NoSuchHandException!");
    }

    @Test
    void splitHand() {
        testHand.addCard(testCard);

        assertDoesNotThrow(() -> {

            testPlayer.splitHand(testHand);
            testPlayer.getHand(1);

        }, "Should not generate exceptions?!");

        assertThrows(NullHandException.class, () -> {

            testPlayer.splitHand(null);

        },"This should throw a NullHandException!");

        assertThrows(MaxHandSplitException.class, () -> {

            testPlayer.getHands().add(new Hand());
            testPlayer.getHands().add(new Hand());
            testPlayer.splitHand(testPlayer.getHand(3));

        },"This should throw a MaxHandSplitException!");

        assertThrows(NoSuchHandException.class, () -> {

            testPlayer.clearHands();
            Hand anotherHand = new Hand();
            testPlayer.splitHand(anotherHand);

        },"This should throw a NoSuchHandException!");

        assertThrows(HandWrongSizeException.class, () -> {

            testPlayer.splitHand(testPlayer.getHand(0));

        },"This should throw a HandWrongSizeException!");

        assertThrows(CardsNotEqualRankException.class, () -> {

            testPlayer.giveCard(new Card(Suit.DIAMONDS, Rank.ACE), testPlayer.getHand(0));
            testPlayer.giveCard(new Card(Suit.DIAMONDS, Rank.KING), testPlayer.getHand(0));
            testPlayer.splitHand(testPlayer.getHand(0));

        },"This should throw a CardsNotEqualRankException!");

        assertThrows(BrokeException.class, () -> {

            testPlayer.clearHands();
            testPlayer.bet(75);
            testPlayer.giveCard(new Card(Suit.DIAMONDS, Rank.ACE), testPlayer.getHand(0));
            testPlayer.giveCard(new Card(Suit.CLUBS, Rank.ACE), testPlayer.getHand(0));
            testPlayer.splitHand(testPlayer.getHand(0));

        },"This should throw a BrokeException!");
    }

    @Test
    void pay() {
        testPlayer.pay(50);
        assertEquals(150, testPlayer.getBalance());
    }

    @Test
    void bet() {
        assertDoesNotThrow(() -> {

            testPlayer.bet(50);
            assertEquals(50, testPlayer.getBalance());

        }, "Should not generate exceptions?!");

        assertThrows(BrokeException.class, () ->
                testPlayer.bet(150),
                "This should throw a BrokeException!");
    }

    @Test
    void getName() {
        String name = testPlayer.getName();
        assertEquals("testSubject", name);
    }

    @Test
    void amountHands() {
        assertEquals(1, testPlayer.amountHands());
    }
}