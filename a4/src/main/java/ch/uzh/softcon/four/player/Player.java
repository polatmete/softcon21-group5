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

public class Player extends PlayerSubject {

    private final String name;
    private int money;
    private int initialBet;

    public Player(String name) {
        super();
        this.name = name;
        this.money = 100;
        this.initialBet = 0;
    }

    public void giveCard(Card card, Hand hand) throws NullHandException, NoSuchHandException {
        if (hand == null) {
            throw new NullHandException();
        }
        if (!hasHand(hand)) {
            throw new NoSuchHandException();
        }
        hand.addCard(card);
    }

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

    public void pay(int money) {
        this.money += money;
    }

    public void bet(int money) throws BrokeException {
        if (money > this.money) {
            throw new BrokeException();
        }
        this.money -= money;
        this.initialBet = money;
    }

    public int getBalance() {
        return this.money;
    }

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