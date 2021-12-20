package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.exceptions.hand.NullHandException;

/**
 * A child class of PlayerSubject that provides further methods that only the dealer needs.
 */
public class Dealer extends PlayerSubject {

    public Dealer() {
        super();
    }

    /**
     * Adds a card to the dealers hand. No specific hand is needed since the dealer only has one hand.
     * @param card is the specific card to be given to the dealer.
     */
    public void giveCard(Card card) {
        try {
            if (super.getHand(0).size() == 1) {
                card.hide();
            }
            super.getHand(0).addCard(card);
        } catch (NullHandException e) {/* Cannot happen */}
    }
}