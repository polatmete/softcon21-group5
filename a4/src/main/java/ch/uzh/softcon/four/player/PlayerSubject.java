package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Hand;
import ch.uzh.softcon.four.exceptions.hand.NullHandException;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class that provides methods for the player and the dealer objects.
 */
public abstract class PlayerSubject {

    /**
     * A list of all the hands a player owns.
     */
    private List<Hand> hands;

    public PlayerSubject() {
        this.hands = new ArrayList<>();
        this.hands.add(new Hand());
    }

    /**
     * Returns the hand of the player with a certain index.
     * @param handIdx is the umber of the targeted hand.
     * @return a {@code Hand} from the player at {@code handIdx}.
     * @throws NullHandException when the player does not have the hand with this index.
     */
    public Hand getHand(int handIdx) throws NullHandException {
        if (this.hands.size() <= handIdx) {
            throw new NullHandException();
        }
        return this.hands.get(handIdx);
    }

    /**
     * Removes all hands of a player and adds a fresh, empty one.
     */
    public void clearHands() {
        this.hands = new ArrayList<>();
        this.hands.add(new Hand());
    }

    /**
     * Directly provides the list of the hands.
     * Only for protected use in child classes.
     * @return the list of hands.
     */
    protected List<Hand> getHands() {
        return this.hands;
    }
}