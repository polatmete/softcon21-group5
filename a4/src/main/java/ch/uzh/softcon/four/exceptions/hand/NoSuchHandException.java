package ch.uzh.softcon.four.exceptions.hand;

/**
 * Exception that is thrown when the player does not own the chosen hand.
 */
public class NoSuchHandException extends Exception {

    public NoSuchHandException() {
        super("Player does not own this hand!");
    }

}
