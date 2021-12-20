package ch.uzh.softcon.four.exceptions.hand;

/**
 * Exception that is thrown when the hand has any other amount than two cards while trying to split.
 */
public class HandWrongSizeException extends Exception {

    public HandWrongSizeException() {
        super("Can not split a hand with another amount than 2 cards!");
    }

}
