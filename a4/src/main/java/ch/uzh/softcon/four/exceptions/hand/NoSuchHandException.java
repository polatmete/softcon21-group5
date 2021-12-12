package ch.uzh.softcon.four.exceptions.hand;

public class NoSuchHandException extends Exception {

    public NoSuchHandException() {
        super("Player does not own this hand!");
    }

}
