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
        if (!super.hasHand(hand)) {
            //TODO: NoSuchHandException?
            return;
        }
        hand.addCard(card);
    }

    public void splitHand(Hand hand) {
        if (hand == null) {
            //TODO: NullHandException?
            return;
        }
        if (super.amountHands() == 4) {
            //TODO: MaxHandSplitException?
            return;
        }
        if (!super.hasHand(hand)) {
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
        Card card1 = hand.getCard(0);
        Card card2 = hand.getCard(1);
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        hand1.addCard(card1);
        hand2.addCard(card2);

        super.removeHand(hand);
        super.addHand(hand1);
        super.addHand(hand2);
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
}
