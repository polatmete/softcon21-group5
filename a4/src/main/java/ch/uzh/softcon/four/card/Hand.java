package ch.uzh.softcon.four.card;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final List<Card> cards;
    private int points;

    public Hand() {
        cards = new ArrayList<>();
        points = 0;
    }

    public void addCard(Card card) {
        cards.add(card);
        points += card.getValue();
    }

    public List<Card> cards() {
        return cards;
    }

    public int points() {
        return points;
    }
}