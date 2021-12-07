package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;

public class Dealer extends PlayerSubject {

    public Dealer() {
        super();
    }

    public void giveCard(Card card) {
        if (super.amountHands() == 1) {
            card.hide();
        }
        super.getHand(0).addCard(card);
    }
}
