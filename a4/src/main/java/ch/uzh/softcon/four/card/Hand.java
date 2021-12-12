package ch.uzh.softcon.four.card;

import ch.uzh.softcon.four.exceptions.card.CardHiddenException;
import ch.uzh.softcon.four.exceptions.card.NullCardException;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final List<Card> cards;
    private int points;

    public Hand() {
        this.cards = new ArrayList<>();
        this.points = 0;
    }

    public Card getCard(int cardIdx) throws NullCardException {
        if (this.cards.size() <= cardIdx) {
            throw new NullCardException();
        }
        return this.cards.get(cardIdx);
    }

    public void addCard(Card card) {
        this.cards.add(card);
        try {
            this.points += card.getRank().getValue();
        } catch (CardHiddenException e) {
            //nothing
        }
    }

    public void reveal() {
        this.points = 0;
        for (Card card : this.cards) {
            card.reveal();
            try {
                this.points += card.getRank().getValue();
            } catch (CardHiddenException e) {
                //nothing
            }
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