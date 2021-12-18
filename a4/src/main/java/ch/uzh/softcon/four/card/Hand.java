package ch.uzh.softcon.four.card;

import ch.uzh.softcon.four.exceptions.card.CardHiddenException;
import ch.uzh.softcon.four.exceptions.card.NullCardException;

import java.util.ArrayList;
import java.util.List;

/**
 * A player hand consisting of a number of cards in the BlackJack game.
 */
public class Hand {

    /**
     * A list of cards.
     */
    private final List<Card> cards;
    /**
     * The points value of the hand.
     */
    private int points;

    public Hand() {
        this.cards = new ArrayList<>();
        this.points = 0;
    }

    /**
     * Returns a card at a given position from the hand.
     * @param cardIdx the card index position in the hand.
     * @return a {@code Card} from the hand at {@code cardIdx}.
     * @throws NullCardException if the hand is empty.
     */
    public Card getCard(int cardIdx) throws NullCardException {
        if (this.cards.size() <= cardIdx) {
            throw new NullCardException();
        }
        return this.cards.get(cardIdx);
    }

    /**
     * Add a card to the hand.
     * @param card The card to add to the hand.
     */
    public void addCard(Card card) {
        this.cards.add(card);
        try {
            this.points += card.getRank().getValue();
        } catch (CardHiddenException e) {/* Don't do anything */}
    }

    /**
     * Reveal the hand.
     */
    public void reveal() {
        this.points = 0;
        for (Card card : this.cards) {
            card.reveal();
            try {
                this.points += card.getRank().getValue();
            } catch (CardHiddenException e) {/* Cannot happen */}
        }
    }

    /**
     * The amount of cards in the hand.
     * @return an {@code int} for the amount of cards in the hand.
     */
    public int size() {
        return this.cards.size();
    }

    /**
     * The points worth of the hand.
     * @return an {@code int} for the points value of the hand.
     */
    public int points() {
        return this.points;
    }
}