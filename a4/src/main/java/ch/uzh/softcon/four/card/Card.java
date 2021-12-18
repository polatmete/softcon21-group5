package ch.uzh.softcon.four.card;

import ch.uzh.softcon.four.exceptions.card.CardHiddenException;

/**
 * A BlackJack card.
 */
public class Card {

    /**
     * The card's assigned suit.
     */
    private final Suit suit;
    /**
     * The card's assigned rank.
     */
    private final Rank rank;
    /**
     * Whether the card is hidden.
     */
    private boolean hidden;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.hidden = false;
    }

    /**
     * Get the card's suit.
     * @return the {@code Card.Suit} of the {@code Card}.
     * @throws CardHiddenException if the card may not be seen.
     */
    public Suit getSuit() throws CardHiddenException {
        if (this.hidden) {
            throw new CardHiddenException();
        }
        return this.suit;
    }

    /**
     * Get the card's rank.
     * @return the {@code Card.Rank} of the {@code Card}.
     * @throws CardHiddenException if the card may not be seen.
     */
    public Rank getRank() throws CardHiddenException {
        if (this.hidden) {
            throw new CardHiddenException();
        }
        return this.rank;
    }

    /**
     * Hide the card's contents.
     */
    public void hide() {
        this.hidden = true;
    }

    /**
     * Reveal the card's contents.
     */
    protected void reveal() {
        this.hidden = false;
    }

    /**
     * The card's suit.
     */
    public enum Suit {
        SPADES('S'),
        HEARTS('H'),
        DIAMONDS('D'),
        CLUBS('C');

        private final char letter;

        Suit(char letter) {
            this.letter = letter;
        }

        public char getLetter() {
            return this.letter;
        }
    }

    /**
     * The card's rank.
     */
    public enum Rank {
        ACE(11),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(10),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10);

        private final int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

//    @Override
//    public String toString() {
//        return "Card{" +
//                "suit=" + suit +
//                ", rank=" + rank +
//                ", hidden=" + hidden +
//                '}';
//    }
}