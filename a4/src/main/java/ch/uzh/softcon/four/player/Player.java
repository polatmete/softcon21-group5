package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.Hand;
import ch.uzh.softcon.four.exceptions.NotEnoughMoneyException;
import ch.uzh.softcon.four.exceptions.card.CardHiddenException;
import ch.uzh.softcon.four.exceptions.card.CardsNotEqualRankException;
import ch.uzh.softcon.four.exceptions.card.NullCardException;
import ch.uzh.softcon.four.exceptions.hand.HandWrongSizeException;
import ch.uzh.softcon.four.exceptions.hand.MaxHandSplitException;
import ch.uzh.softcon.four.exceptions.hand.NoSuchHandException;
import ch.uzh.softcon.four.exceptions.hand.NullHandException;

public class Player extends PlayerSubject {

    private int money;
    private final String name;

    public Player(String name) {
        super();
        this.money = 100;
        this.name = name;
    }

    public void giveCard(Card card, Hand hand) {
        if (hand == null) {
            //TODO: NullHandException?
            return;
        }
        if (!hasHand(hand)) {
            //TODO: NoSuchHandException?
            return;
        }
        hand.addCard(card);
    }

    public void splitHand(Hand hand) throws NullHandException, MaxHandSplitException,
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
            if (hand.getCard(0).getRank() != hand.getCard(1).getRank()) {
                throw new CardsNotEqualRankException();
            }
            removeHand(hand);
            for (int i = 0; i <= 1; i++) {
                Card card = hand.getCard(i);
                Hand newHand = new Hand();
                newHand.addCard(card);
                addHand(newHand);
            }
        } catch (CardHiddenException | NullCardException e) {
            System.err.println(e.getMessage());
        }
    }

    public void pay(int money) {
        this.money += money;
    }

    public void bet(int money) throws NotEnoughMoneyException {
        if (money > this.money) {
            throw new NotEnoughMoneyException();
        }
        this.money -= money;
    }

    //TODO getter?
    public int balance() {
        return this.money;
    }

    //TODO getter? -> is final ..
    public String getName() {
        return this.name;
    }

    public int amountHands() {
        return super.getHands().size();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean hasHand(Hand hand) {
        return super.getHands().contains(hand);
    }

    private void addHand(Hand hand) {
        super.getHands().add(hand);
    }

    private  void removeHand(Hand hand) {
        super.getHands().remove(hand);
    }
}
