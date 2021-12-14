package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.Hand;

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

    public void splitHand(Hand hand, int bet) {
        if (hand == null) {
            //TODO: NullHandException?
            return;
        }
        if (amountHands() == 4) {
            //TODO: MaxHandSplitException?
            return;
        }
        if (!hasHand(hand)) {
            //TODO: NoSuchHandException?
            return;
        }
        if (hand.size() != 2) {
            //TODO: HandWrongSizeException?
            return;
        }
        if (hand.getCard(0).getRank() != hand.getCard(1).getRank()) {
            //TODO: CardsNotEqualRankException?
            return;
        }
        removeHand(hand);
        for (int i = 0; i <= 1; i++) {
            Card card = hand.getCard(i);
            Hand newHand = new Hand();
            newHand.addCard(card);
            addHand(newHand);
        }
    }

    public void pay(int money) {
        this.money += money;
    }

    public void bet(int money) {
        if (money > this.money) {
            //TODO: NoMoneyException
            return;
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
