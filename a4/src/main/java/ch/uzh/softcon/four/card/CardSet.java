package ch.uzh.softcon.four.card;

import ch.uzh.softcon.four.card.Card.Suit;
import ch.uzh.softcon.four.card.Card.Rank;

public class CardSet {

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

    public Card[] drawCards() {
        return this.cards;
    }

    public int size() {
        return this.cards.length;
    }
}
