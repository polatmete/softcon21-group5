package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.exceptions.hand.NullHandException;

public class Dealer extends PlayerSubject {

    public Dealer() {
        super();
    }

    public void giveCard(Card card) {
        try {
            if (super.getHand(0).size() == 1) {
                card.hide();
            }
            super.getHand(0).addCard(card);
        } catch (NullHandException e) {/* Cannot happen */}
    }
}