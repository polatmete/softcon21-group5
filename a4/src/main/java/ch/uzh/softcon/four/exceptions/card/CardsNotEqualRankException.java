package ch.uzh.softcon.four.exceptions.card;

/**
 * Exception that is thrown when the two cards on a players hand are not equal while trying to split.
 */
public class CardsNotEqualRankException extends Exception {

    public CardsNotEqualRankException() {
        super("Cards on hand are not equal!");
    }

}
