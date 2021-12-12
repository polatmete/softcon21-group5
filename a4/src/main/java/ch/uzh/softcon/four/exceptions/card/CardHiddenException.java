package ch.uzh.softcon.four.exceptions.card;

public class CardHiddenException extends Exception {

    public CardHiddenException() {
        super("The chosen card is hidden, cannot reveal its value!");
    }

}
