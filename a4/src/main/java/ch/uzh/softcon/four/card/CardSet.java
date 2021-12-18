package ch.uzh.softcon.four.card;

import ch.uzh.softcon.four.card.Card.Suit;
import ch.uzh.softcon.four.card.Card.Rank;

/**
 * A card set consisting of multiple cards for a BlackJack game.
 */
public class CardSet {

    /**
     * An array of cards.
     */
    private final Card[] cards;

    public CardSet() {
        this.cards = new Card[52];
        int i = 0;
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                this.cards[i] = new Card(s, r);
                i++;
            }
        }
    }

    /**
     * The size of the card set.
     * @return an {@code int} for the amount of cards in the set.
     */
    public int size() {
        return this.cards.length;
    }

    /**
     * Draw all cards from the set.
     * @return a {@code Card} array from the {@code CardSet}.
     */
    protected Card[] drawCards() {
        return this.cards;
    }
}