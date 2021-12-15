package ch.uzh.softcon.four.card;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class CardDeck {

    private final Stack<Card> cards;
    private static CardDeck instance;

    // TODO: Set useful value!
    private final int THRESHOLD = 26;
    
    private CardDeck() {
        this.cards = new Stack<>();
    }

    public void fillDeck(int count) {
        for (int i = 0; i < count; i++) {
            addSet(new CardSet());
        }
        shuffle();
    }

    public static synchronized CardDeck getInstance() {
        if (instance == null)
            instance = new CardDeck();
        return instance;
    }

    private void addSet(CardSet cardSet) {
        for (int i = 0; i < cardSet.size(); i++) {
            this.cards.push(cardSet.drawCards()[i]);
        }
    }

    public Card drawCard() {
        Card poppedCard = cards.pop();
        if (this.cards.size() < THRESHOLD)
            addSet(new CardSet());
        return poppedCard;
    }

    public int size() {
        return this.cards.size();
    }

    private void shuffle() {
        Collections.shuffle(this.cards);
    }
}
