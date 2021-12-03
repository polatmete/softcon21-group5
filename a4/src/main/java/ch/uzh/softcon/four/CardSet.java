package ch.uzh.softcon.four;

public class CardSet {
    private Card[] cards;

    public CardSet(Card[] cards) {
        this.cards = cards;
    }

    public Card[] drawCards() {
        return this.cards;
    }

    public int size() {
        return this.cards.length;
    }
}
