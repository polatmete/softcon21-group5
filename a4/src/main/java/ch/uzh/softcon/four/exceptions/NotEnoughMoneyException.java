package ch.uzh.softcon.four.exceptions;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException() {
        super("Player does not have enough money to bet this amount!");
    }

}
