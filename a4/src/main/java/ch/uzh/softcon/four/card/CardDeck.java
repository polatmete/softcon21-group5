package ch.uzh.softcon.four.card;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class CardDeck {

    private final Stack<Card> cards;
    private int threshold = 0;
    private static CardDeck instance;
    
    private CardDeck() {
        this.cards = new Stack<>();
    }

    public static synchronized CardDeck getInstance() {
        if (instance == null)
            instance = new CardDeck();
        return instance;
    }

    public void fillDeck(int amountSet) {
        for (int i = 0; i < amountSet; i++) {
            addSet(new CardSet());
        }
        shuffle();
        threshold = amountSet * 26;
    }

    public Card drawCard() {
        Card poppedCard = cards.pop();
        if (this.cards.size() <= threshold) {
            addSet(new CardSet());
            shuffle();
        }
        return poppedCard;
    }

    public int size() {
        return this.cards.size();
    }

    private void addSet(CardSet cardSet) {
        this.cards.addAll(Arrays.asList(cardSet.drawCards()));
    }

    private void shuffle() {
        Collections.shuffle(this.cards);
    }
}