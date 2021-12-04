package ch.uzh.softcon.four.player;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.Hand;

public class Player extends PlayerSubject {

    private int money;
    private final String name;

    public Player(String name) {
        super();
        money = 100;
        this.name = name;
    }

    public void giveCard(Card card, Hand hand) {
        if (hand == null) {
            //TODO: NullHandException
            return;
        }
        if (!super.getHands().contains(hand)) {
            //TODO: NoSuchHandException
            return;
        }
        super.getHands().get(super.getHands().indexOf(hand)).addCard(card);
    }

    public void splitHand(Hand hand) {
        if (hand == null) {
            //TODO: NullHandException
            return;
        }
        if (hand.getCards().size() != 2) {
            //TODO: HandWrongSizeException
            return;
        }
        if (hand.getCards().get(0).getRank() != hand.getCards().get(1).getRank()) {
            //TODO: CardsNotEqualRankException
            return;
        }
        Card card1 = hand.getCards().get(0);
        Card card2 = hand.getCards().get(1);
        Hand hand1 = new Hand();
        hand1.addCard(card1);
        Hand hand2 = new Hand();
        hand2.addCard(card2);

        super.getHands().remove(hand);
        super.getHands().add(hand1);
        super.getHands().add(hand2);
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
