package ch.uzh.softcon.four.exceptions.player;

public class BrokeException extends Exception {

    public BrokeException() {
        super("Player does not have enough money to bet this amount!");
    }

}
