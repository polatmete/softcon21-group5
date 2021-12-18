package ch.uzh.softcon.four.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckTest {

    private CardDeck deck;

    @BeforeEach
    void setUp() {
        try {
            Field cardDeckInstance = CardDeck.getInstance().getClass().getDeclaredField("instance");
            cardDeckInstance.setAccessible(true);
            cardDeckInstance.set(deck, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }
        deck = CardDeck.getInstance();
    }

    @Test
    void getInstanceIsNonNullCardDeckInstance() {
        assertEquals(deck.getClass(), CardDeck.class, "Deck is not an instance of CardDeck.");
        assertNotNull(deck, "Deck is null, why though?");
    }

    @Test
    void drawCardReturnsCardFromDeck() {
        deck.fillDeck(3);
        assertNotNull(deck.drawCard(), "Wrong card.");
    }

//    @Test
//    void drawCardOnThresholdRepopulatesDeck() {
//        // TODO: Create new deck, draw cards until threshold is reached, compare before and after?
//    }

    @Test
    void sizeOfDeckIsCorrect() {
        deck.fillDeck(3);
        assertEquals(156, deck.size(), "Deck size is wrong.");
    }
}