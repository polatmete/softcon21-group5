package ch.uzh.softcon.four.player;

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

    public boolean hasHand(Hand hand) {
        return this.hands.contains(hand);
    }

    public int amountHands() {
        return this.hands.size();
    }

    public void addHand(Hand hand) {
        this.hands.add(hand);
    }

    public void clearHands() {
        this.hands = new ArrayList<>();
    }

    protected void removeHand(Hand hand) {
        this.hands.remove(hand);
    }
}