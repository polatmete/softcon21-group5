package ch.uzh.softcon.four.card;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> cards;
    private int points;

    public Hand() {
        cards = new ArrayList<>();
        points = 0;
    }

    public void addCard(Card card) {
        cards.add(card);
        points += card.getValue();
    }

    //TODO: getter? -> access specific index and return copy of that card: getCard(idx) & cardsOnHand():int (size)
    //TODO: -> is final ..
    public List<Card> getCards() {
        return cards;
    }

    //TODO getter?
    public int points() {
        return points;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                ", points=" + points +
                '}';
    }
}