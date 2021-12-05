package ch.uzh.softcon.four;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.Card.Rank;
import ch.uzh.softcon.four.card.Card.Suit;
import ch.uzh.softcon.four.card.CardDeck;
import ch.uzh.softcon.four.card.CardSet;
import ch.uzh.softcon.four.player.Dealer;
import ch.uzh.softcon.four.card.Hand;
import ch.uzh.softcon.four.player.Player;


public class Main {
    private static Dealer dealer = new Dealer();
    private static Player[] players = new Player[5];
    private static int difficulty;
    private static CardDeck deck;

    public static void main(String[] args) {
        // TODO: Implement
        initialize();
        loop();
    }

    private static void initialize() {
        // Clear console?
        // Welcome to BJ
        // Scoreboard.print
        // how many players? max 5(?)
        int countPlayers = 5;
        for (int i = 0; i < countPlayers; ++i) players[countPlayers] = new Player(("Player " + i+1));
        // enter names of players
        // difficulty level: [e]asy: 1 deck / [m]edium: 3 decks / [h]ard: 6 decks
        difficulty = 6;
        // initialize decks
        deck = new CardDeck(6);
    }

    private static void loop() {
        while(true) {
            // Check whether Players left in the room
            boolean playerLeft = false;
            for (int i = 0; i < 5; ++i) {
                if (players[i] != null) {
                    playerLeft = true;
                    break;
                }
            }
            if (playerLeft) break;

            dealer.giveCard(deck.drawCard());
            dealer.giveCard(deck.drawCard());

            for (Player p : players) {
                p.giveCard(deck.drawCard());
                p.giveCard(deck.drawCard());
            }


            /*Give each player and dealer cards.. dealar as to reveal first card (?)
            For each player ask for bet (or leave by typing "leave"). After bet show players cards and let him hit or stay
            After every player is done reveal each players and dealers cards.
            Pay out player twice his bet (since player already payed in the beginning)
            Kick out player without money and ask whether player wants to join*/
        }

        //TODO: remove, this is only for initial testing and small overview

        Card card = new Card(Suit.SPADES, Rank.ACE);
        Card c2 = new Card(Suit.CLUBS, Rank.ACE);

        Player p = new Player("BJ King");
        CardSet set = new CardSet();
        CardDeck deck = CardDeck.getInstance();
        // It's populated and shuffled right away now
        //deck.addSet(set);
        //deck.shuffle();

        p.giveCard(card, p.getHands().get(0));
        p.giveCard(c2, p.getHands().get(0));
        p.splitHand(p.getHands().get(0));

        for (Hand hand : p.getHands()) {
            System.out.println(hand);
        }
        System.out.println(p.getHands().get(0).getCards());
        System.out.println(p.getHands().get(1).getCards());
    }
}
