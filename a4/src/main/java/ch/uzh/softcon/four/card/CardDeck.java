package ch.uzh.softcon.four.card;

import java.util.Collections;
import java.util.Stack;

public class CardDeck {

    private Stack<Card> cards;
    private static CardDeck instance;
    
    private CardDeck() {
        cards = new Stack<>();
        for (int i = 0; i < 6; i++) {
            addSet(new CardSet());
        }
        shuffle();
    }

    public static synchronized CardDeck getInstance() {
        if (instance == null)
            instance = new CardDeck();
        return instance;
    }

    public void addSet(CardSet cardSet) {
        for (int i = 0; i < cardSet.size(); i++) {
            this.cards.push(cardSet.drawCards()[i]);
        }
    }

    public Card drawCard() {
        return this.cards.pop();
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public int size() {
        return this.cards.size();
    }
}
