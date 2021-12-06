package ch.uzh.softcon.four.card;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final List<Card> cards;
    private boolean hidden;
    private int points;

    public Hand() {
        this.cards = new ArrayList<>();
        this.hidden = true;
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
        this.hidden = false;
        for (Card card : this.cards) {
            this.points += card.getRank().getValue();
            card.reveal();
        }
    }

    public int size() {
        return this.cards.size();
    }

    //TODO getter?
    public int points() {
        if (this.hidden) {
            return 0;
        }
        return this.points;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                ", points=" + points +
                '}';
    }
}