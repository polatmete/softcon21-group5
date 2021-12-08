package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.Hand;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerSubject {

    private List<Hand> hands;

    public PlayerSubject() {
        this.hands = new ArrayList<>();
        this.hands.add(new Hand());
    }

    public Hand getHand(int handIdx) {
        if (this.hands.size() <= handIdx) {
            //TODO: NullHandException?
            return null;
        }
        return this.hands.get(handIdx);
    }

    public void clearHands() {
        this.hands = new ArrayList<>();
        this.hands.add(new Hand());
    }

    protected List<Hand> getHands() {
        return this.hands;
    }
}