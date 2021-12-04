package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Hand;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerSubject {

    private List<Hand> hands;

    public PlayerSubject() {
        hands = new ArrayList<>();
        hands.add(new Hand());
    }

    //TODO: getter?
    public List<Hand> getHands() {
        return hands;
    }

    public void clearHands() {
        hands = new ArrayList<>();
    }
}
