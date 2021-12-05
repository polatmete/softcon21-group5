package ch.uzh.softcon.four.card;

public class Card {

    private final Suit suit;
    private final Rank rank;
    private boolean hidden;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.hidden = true;
    }

    public Suit getSuit() {
        if (hidden) {
            //TODO CardHiddenException
            return null;
        }
        return suit;
    }

    public Rank getRank() {
        if (hidden) {
            //TODO CardHiddenException
            return null;
        }
        return rank;
    }

    public void reveal() {
        this.hidden = false;
    }

    protected int getValue() {
        return rank.value;
    }

    public enum Suit {
        SPADES,
        HEARTS,
        DIAMONDS,
        CLUBS
    }

    public enum Rank {
        ACE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10);

        private final int value;

        Rank(int value) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", rank=" + rank +
                ", hidden=" + hidden +
                '}';
    }
}