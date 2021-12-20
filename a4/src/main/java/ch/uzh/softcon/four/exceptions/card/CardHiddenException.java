package ch.uzh.softcon.four.exceptions.card;

/**
 * Exception that is thrown when the chosen card is hidden.
 */
public class CardHiddenException extends Exception {

    public CardHiddenException() {
        super("The chosen card is hidden, cannot reveal its value!");
    }

}
