package ch.uzh.softcon.four.exceptions.player;

/**
 * Exception that is thrown when the player does not have enough money to bet or split.
 */
public class BrokeException extends Exception {

    public BrokeException() {
        super("Player does not have enough money to bet this amount!");
    }

}
