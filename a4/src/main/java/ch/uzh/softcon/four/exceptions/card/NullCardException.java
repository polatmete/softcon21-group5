package ch.uzh.softcon.four.exceptions.card;

/**
 * Exception that is thrown when the chosen card does not exist.
 */
public class NullCardException extends Exception {

    public NullCardException() {
        super("The card does not exist!");
    }

}
