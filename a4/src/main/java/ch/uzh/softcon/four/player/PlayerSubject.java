package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Hand;
import ch.uzh.softcon.four.exceptions.hand.NullHandException;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerSubject {

    private List<Hand> hands;

    public PlayerSubject() {
        this.hands = new ArrayList<>();
        this.hands.add(new Hand());
    }

    public Hand getHand(int handIdx) throws NullHandException {
        if (this.hands.size() <= handIdx) {
            throw new NullHandException();
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