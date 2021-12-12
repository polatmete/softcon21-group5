package ch.uzh.softcon.four.exceptions.card;

public class CardsNotEqualRankException extends Exception {

    public CardsNotEqualRankException() {
        super("Cards on hand are not equal!");
    }

}
