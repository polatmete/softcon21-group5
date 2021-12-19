package ch.uzh.softcon.four.exceptions.card;

public class NullCardException extends Exception {

    public NullCardException() {
        super("The card does not exist!");
    }

}
