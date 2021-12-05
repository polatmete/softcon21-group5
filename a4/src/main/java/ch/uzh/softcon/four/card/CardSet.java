package ch.uzh.softcon.four.card;

import ch.uzh.softcon.four.card.Card.Suit;
import ch.uzh.softcon.four.card.Card.Rank;

import java.util.Arrays;

public class CardSet {

    private Card[] cards;

    public CardSet() {
        this.cards = new Card[52];
        int i = 0;
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                this.cards[i++] = new Card(s, r);
            }
        }
    }

    public Card[] drawCards() {
        Card[] drawnCards = Arrays.copyOf(this.cards, this.cards.length);
        this.cards = null;
        return drawnCards;
    }

    public int size() {
        return this.cards.length;
    }
}
