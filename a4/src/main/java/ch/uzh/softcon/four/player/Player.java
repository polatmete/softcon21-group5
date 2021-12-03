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

    public void giveCard(Card card, int handIdx) {
        if (super.hands().get(handIdx) == null) {
            //TODO: Replace with HandOverflowException
            return;
        }
        super.hands().get(handIdx).addCard(card);
    }

    public void splitHand() {
        //TODO implement
        super.hands().add(new Hand());
    }

    public void pay(int money) {
        this.money += money;
    }

    public void bet(int money) {
        this.money -= money;
    }

    public int balance() {
        return this.money;
    }

    public String getName() {
        return this.name;
    }
}
