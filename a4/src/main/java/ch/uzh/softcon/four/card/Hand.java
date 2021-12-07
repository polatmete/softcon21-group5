package ch.uzh.softcon.four.card;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final List<Card> cards;
    private int points;

    public Hand() {
        this.cards = new ArrayList<>();
        this.points = 0;
    }

    public Card getCard(int cardIdx) {
        if (this.cards.size() <= cardIdx) {
            //TODO NullCardException
            return null;
        }
        return this.cards.get(cardIdx);
    }

    public void addCard(Card card) {
        this.cards.add(card);
        //TODO: Replace with catch?
        if (card.getRank() != null) {
            this.points += card.getRank().getValue();
        }
    }

    public void reveal() {
        this.points = 0;
        for (Card card : this.cards) {
            card.reveal();
            this.points += card.getRank().getValue();
        }
    }

    public int size() {
        return this.cards.size();
    }

    //TODO getter?
    public int points() {
        return this.points;
    }
}