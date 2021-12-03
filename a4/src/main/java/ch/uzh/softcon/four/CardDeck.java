package ch.uzh.softcon.four;

import java.util.Collections;
import java.util.Stack;

public class CardDeck {
    private Stack<Card> cards;

    private static CardDeck instance;

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

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public int size() {
        return this.cards.size();
    }
}
