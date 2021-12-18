package ch.uzh.softcon.four.card;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

/**
 * A card deck consisting of multiple cards from a card set for a BlackJack game.
 */
public class CardDeck {

    /**
     * A stack of cards.
     */
    private final Stack<Card> cards;
    /**
     * The minimum allowed card amount on the card deck.
     */
    private int threshold = 0;
    /**
     * The {@code CardDeck} instance.
     */
    private static CardDeck instance;
    
    private CardDeck() {
        this.cards = new Stack<>();
    }

    /**
     * Get or create and get a {@code CardDeck} instance.
     * @return a {@code CardDeck} instance.
     */
    public static synchronized CardDeck getInstance() {
        if (instance == null)
            instance = new CardDeck();
        return instance;
    }

    /**
     * Populate the deck with new CardSets. Takes an integer for the amount of CardSets to add.
     * @param amountSet the amount of {@code CardSets} to be added to the {@code CardDeck}.
     */
    public void fillDeck(int amountSet) {
        for (int i = 0; i < amountSet; i++) {
            addSet(new CardSet());
        }
        shuffle();
        threshold = amountSet * 26;
    }

    /**
     * Draw a single card from the deck.
     * @return a {@code Card} from the {@code CardDeck}.
     */
    public Card drawCard() {
        Card poppedCard = cards.pop();
        if (this.cards.size() <= threshold) {
            addSet(new CardSet());
            shuffle();
        }
        return poppedCard;
    }

    /**
     * The size of the card deck.
     * @return an {@code int} for the amount of cards on the deck.
     */
    public int size() {
        return this.cards.size();
    }

    /**
     * Add a {@code CardSet} to the deck.
     * @param cardSet the {@code CardSet} to add.
     */
    private void addSet(CardSet cardSet) {
        this.cards.addAll(Arrays.asList(cardSet.drawCards()));
    }

    /**
     * Shuffle the card deck.
     */
    private void shuffle() {
        Collections.shuffle(this.cards);
    }
}