package ch.uzh.softcon.four.card;

import ch.uzh.softcon.four.exceptions.card.CardHiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CardSetTest {

    private CardSet cardSet;

    @BeforeEach
    void setUp() {
        cardSet = new CardSet();
    }

    @Test
    void drawCards() {
        Card[] expected = new Card[] {
                new Card(Card.Suit.SPADES, Card.Rank.ACE),
                new Card(Card.Suit.SPADES, Card.Rank.TWO),
                new Card(Card.Suit.SPADES, Card.Rank.THREE),
                new Card(Card.Suit.SPADES, Card.Rank.FOUR),
                new Card(Card.Suit.SPADES, Card.Rank.FIVE),
                new Card(Card.Suit.SPADES, Card.Rank.SIX),
                new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
                new Card(Card.Suit.SPADES, Card.Rank.EIGHT),
                new Card(Card.Suit.SPADES, Card.Rank.NINE),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.SPADES, Card.Rank.KING),

                new Card(Card.Suit.HEARTS, Card.Rank.ACE),
                new Card(Card.Suit.HEARTS, Card.Rank.TWO),
                new Card(Card.Suit.HEARTS, Card.Rank.THREE),
                new Card(Card.Suit.HEARTS, Card.Rank.FOUR),
                new Card(Card.Suit.HEARTS, Card.Rank.FIVE),
                new Card(Card.Suit.HEARTS, Card.Rank.SIX),
                new Card(Card.Suit.HEARTS, Card.Rank.SEVEN),
                new Card(Card.Suit.HEARTS, Card.Rank.EIGHT),
                new Card(Card.Suit.HEARTS, Card.Rank.NINE),
                new Card(Card.Suit.HEARTS, Card.Rank.TEN),
                new Card(Card.Suit.HEARTS, Card.Rank.JACK),
                new Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
                new Card(Card.Suit.HEARTS, Card.Rank.KING),

                new Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.TWO),
                new Card(Card.Suit.DIAMONDS, Card.Rank.THREE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR),
                new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.SIX),
                new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT),
                new Card(Card.Suit.DIAMONDS, Card.Rank.NINE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.TEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.JACK),
                new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),

                new Card(Card.Suit.CLUBS, Card.Rank.ACE),
                new Card(Card.Suit.CLUBS, Card.Rank.TWO),
                new Card(Card.Suit.CLUBS, Card.Rank.THREE),
                new Card(Card.Suit.CLUBS, Card.Rank.FOUR),
                new Card(Card.Suit.CLUBS, Card.Rank.FIVE),
                new Card(Card.Suit.CLUBS, Card.Rank.SIX),
                new Card(Card.Suit.CLUBS, Card.Rank.SEVEN),
                new Card(Card.Suit.CLUBS, Card.Rank.EIGHT),
                new Card(Card.Suit.CLUBS, Card.Rank.NINE),
                new Card(Card.Suit.CLUBS, Card.Rank.TEN),
                new Card(Card.Suit.CLUBS, Card.Rank.JACK),
                new Card(Card.Suit.CLUBS, Card.Rank.QUEEN),
                new Card(Card.Suit.CLUBS, Card.Rank.KING)
        };

        // Don't even ask me, okay?
        List<Boolean> equalityResult = IntStream.range(0, expected.length).mapToObj(i -> {
            try {
                return expected[i].getSuit().getLetter() == cardSet.drawCards()[i].getSuit().getLetter()
                        && expected[i].getRank() == cardSet.drawCards()[i].getRank();
            } catch (CardHiddenException e) {
                fail(e.getMessage());
            }
            return null;
        }).toList();

        assertTrue(equalityResult.stream().allMatch(t -> t), "The card sets are not identical.");
    }

    @Test
    void size() {
        assertEquals(52, cardSet.size(), "The card set size does not match.");
    }
}