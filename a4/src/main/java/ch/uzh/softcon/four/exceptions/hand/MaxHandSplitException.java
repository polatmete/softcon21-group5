package ch.uzh.softcon.four.exceptions.hand;

public class MaxHandSplitException extends Exception {

    public MaxHandSplitException() {
        super("Cannot have more than 4 hands!");
    }
}
