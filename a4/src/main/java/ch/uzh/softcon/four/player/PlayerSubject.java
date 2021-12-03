package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.Hand;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerSubject {

    private List<Hand> hands;

    public PlayerSubject() {
        hands = new ArrayList<>();
        hands.add(new Hand());
    }

    //TODO check modifier
    protected List<Hand> hands() {
        return hands;
    }

    //TODO check modifier
    protected void clearHands() {
        hands = new ArrayList<>();
    }
}
