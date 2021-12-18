package ch.uzh.softcon.four.card;

import ch.uzh.softcon.four.exceptions.card.CardHiddenException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private Card card;

    @Test
    void getSuitReturnsCorrectSuit() {
        card = new Card(Card.Suit.HEARTS, Card.Rank.NINE);
        assertDoesNotThrow(() -> {
            card.getSuit();
            assertEquals(Card.Suit.HEARTS, card.getSuit(), "The suit is not right!");
            }, "This should not throw a CardHiddenException!"
        );
    }

    @Test
    void getRankReturnsCorrectRank() {
        card = new Card(Card.Suit.HEARTS, Card.Rank.NINE);
        assertDoesNotThrow(() -> {
                    card.getRank();
                    assertEquals(Card.Rank.NINE, card.getRank(), "The rank is not right!");
                }, "This should not throw a CardHiddenException!"
        );
    }

    @Test
    void hideDoesIndeedHideTheCard() {
        card = new Card(Card.Suit.HEARTS, Card.Rank.NINE);
        card.hide();
        assertThrows(CardHiddenException.class,
                () -> card.getRank(),
                "Shoulda thrown a CardHiddenException old sport.");
    }

    @Test
    void revealDoesIndeedRevealTheCard() {
        card = new Card(Card.Suit.HEARTS, Card.Rank.NINE);

        try {
            // This test case will be dependent on the hide() test if we would not do this little trick.
            Field hiddenField = card.getClass().getDeclaredField("hidden");
            hiddenField.setAccessible(true);
            hiddenField.setBoolean(card, true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }
        assertThrows(CardHiddenException.class,
                () -> card.getRank(),
                "You failed to use reflection, shame on you.");

        card.reveal();
        assertDoesNotThrow(() -> {
            card.getRank();
        }, "You threw up on the revelation, disgusting.");
    }
}