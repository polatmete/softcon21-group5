package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;

public class Dealer extends PlayerSubject {

    public Dealer() {
        super();
    }

    private void giveCard(Card card) {
        super.hands().get(0).addCard(card);
    }
}
