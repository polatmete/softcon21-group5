package ch.uzh.softcon.four.exceptions.hand;

/**
 * Exception that is thrown when the chosen hand does not exist.
 */
public class NullHandException extends Exception {

    public NullHandException() {
        super("The hand does not exist!");
    }

}
