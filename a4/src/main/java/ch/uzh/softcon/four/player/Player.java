package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.CardDeck;
import ch.uzh.softcon.four.card.Hand;
import ch.uzh.softcon.four.exceptions.player.BrokeException;
import ch.uzh.softcon.four.exceptions.card.CardHiddenException;
import ch.uzh.softcon.four.exceptions.card.CardsNotEqualRankException;
import ch.uzh.softcon.four.exceptions.card.NullCardException;
import ch.uzh.softcon.four.exceptions.hand.HandWrongSizeException;
import ch.uzh.softcon.four.exceptions.hand.MaxHandSplitException;
import ch.uzh.softcon.four.exceptions.hand.NoSuchHandException;
import ch.uzh.softcon.four.exceptions.hand.NullHandException;

/**
 * A child class of PlayerSubject that provides further methods that only a player needs.
 */
public class Player extends PlayerSubject {

    /**
     * The name of the player.
     */
    private final String name;
    /**
     * The current balance of the player.
     */
    private int money;
    /**
     * The amount of the first bet that the player has done.
     */
    private int initialBet;

    public Player(String name) {
        super();
        this.name = name;
        this.money = 100;
        this.initialBet = 0;
    }

    /**
     * Method to give a card to specific hand of the player.
     * @param card is the new card.
     * @param hand is the targeted hand.
     * @throws NullHandException is thrown when the given hand does not exist.
     * @throws NoSuchHandException is thrown when the player does not own this specific hand.
     */
    public void giveCard(Card card, Hand hand) throws NullHandException, NoSuchHandException {
        if (hand == null) {
            throw new NullHandException();
        }
        if (!hasHand(hand)) {
            throw new NoSuchHandException();
        }
        hand.addCard(card);
    }

    /**
     * This method lets the player split his current hand when all the conditions are met.
     * @param hand is the specific hand to be split.
     * @throws BrokeException is thrown when the player does not have enough money for the split.
     * @throws NullHandException is thrown when the specific hand does not exist.
     * @throws MaxHandSplitException is thrown when the player already owns 4 hands.
     * @throws NoSuchHandException is thrown when the player does not own this specific hand.
     * @throws HandWrongSizeException is thrown when the current hand does not have exactly 2 cards.
     * @throws CardsNotEqualRankException is thrown when the two cards on hand do not have the same value.
     */
    public void splitHand(Hand hand) throws BrokeException, NullHandException, MaxHandSplitException,
            NoSuchHandException, HandWrongSizeException, CardsNotEqualRankException {
        if (hand == null) {
            throw new NullHandException();
        }
        if (amountHands() == 4) {
            throw new MaxHandSplitException();
        }
        if (!hasHand(hand)) {
            throw new NoSuchHandException();
        }
        if (hand.size() != 2) {
            throw new HandWrongSizeException();
        }
        try {
            if (hand.getCard(0).getRank().getValue() != hand.getCard(1).getRank().getValue()) {
                throw new CardsNotEqualRankException();
            }
            if (this.initialBet > this.money) {
                throw new BrokeException();
            }
            removeHand(hand);
            for (int i = 0; i <= 1; i++) {
                Card card = hand.getCard(i);
                Hand newHand = new Hand();
                newHand.addCard(card);
                newHand.addCard(CardDeck.getInstance().drawCard());
                addHand(newHand);
            }
            this.money -= initialBet;
        } catch (CardHiddenException | NullCardException e) {/* Cannot happen */}
    }

    /**
     * Adds money to the players balance.
     * @param money is the amount of money to be paid.
     */
    public void pay(int money) {
        this.money += money;
    }

    /**
     * Removes money from the player when he makes a bet.
     * @param money is the amount of money to be bet.
     * @throws BrokeException is thrown when the player does not have enough money to bet.
     */
    public void bet(int money) throws BrokeException {
        if (money > this.money) {
            throw new BrokeException();
        }
        this.money -= money;
        this.initialBet = money;
    }

    /**
     * Provides the current balance.
     * @return the int with the current amount of money.
     */
    public int getBalance() {
        return this.money;
    }

    /**
     * Provides the name of the player.
     * @return a string with the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Provides the amount of hands a player currently has.
     * @return an int with the amount of hands.
     */
    public int amountHands() {
        return super.getHands().size();
    }

    /**
     * Let's the user check whether the player owns a certain hand.
     * Only for private use in the player class.
     * @param hand is the specific hand to be checked.
     * @return a boolean whether the player owns that hand or not.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean hasHand(Hand hand) {
        return super.getHands().contains(hand);
    }

    /**
     * Let's the user add a certain hand to the player.
     * Only for private use in the player class.
     * @param hand is the hand to be added.
     */
    private void addHand(Hand hand) {
        super.getHands().add(hand);
    }

    /**
     * Let's the user remove a certain hand of the player.
     * Only for private use in the player class.
     * @param hand is the certain hand to be removed.
     */
    private void removeHand(Hand hand) {
        super.getHands().remove(hand);
    }
}