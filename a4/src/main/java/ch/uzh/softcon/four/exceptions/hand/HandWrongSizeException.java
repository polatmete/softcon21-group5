package ch.uzh.softcon.four.exceptions.hand;

public class HandWrongSizeException extends Exception {

    public HandWrongSizeException() {
        super("Can not split a hand with another amount than 2 cards!");
    }

}
