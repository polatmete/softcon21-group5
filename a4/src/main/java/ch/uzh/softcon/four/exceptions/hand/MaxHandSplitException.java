package ch.uzh.softcon.four.exceptions.hand;

/**
 * Exception that is thrown when the player already has 4 hands.
 */
public class MaxHandSplitException extends Exception {

    public MaxHandSplitException() {
        super("Cannot have more than 4 hands!");
    }
}
