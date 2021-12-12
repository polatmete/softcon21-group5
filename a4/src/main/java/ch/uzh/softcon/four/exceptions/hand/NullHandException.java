package ch.uzh.softcon.four.exceptions.hand;

public class NullHandException extends Exception {

    public NullHandException() {
        super("The hand does not exist!");
    }

}
