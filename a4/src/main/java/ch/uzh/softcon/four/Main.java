package ch.uzh.softcon.four;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.Card.Rank;
import ch.uzh.softcon.four.card.Card.Suit;
import ch.uzh.softcon.four.card.CardDeck;
import ch.uzh.softcon.four.card.CardSet;
import ch.uzh.softcon.four.player.Dealer;
import ch.uzh.softcon.four.card.Hand;
import ch.uzh.softcon.four.player.Player;
import java.util.Scanner;


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
        System.out.println("Welcome to BJ");
        // Scoreboard.print
        // how many players? max 5(?)
        System.out.print("How many players want to join the table? ");
        Scanner scn = new Scanner(System.in);
        int countPlayers = scn.nextInt();
        while (countPlayers < 0 || countPlayers > 5) {
            System.out.print("\nPlease insert a number between 0 and 5: ");
            countPlayers = scn.nextInt();
        }

        for (int i = 0; i < countPlayers; ++i) {
            System.out.print("\nPlayer " + (i+1) + "please insert your name: ");
            players[i] = new Player(scn.next());
        }
        // enter names of players
        // difficulty level: [e]asy: 1 deck / [m]edium: 3 decks / [h]ard: 6 decks

        do {
            System.out.println("\nChoose a level:\n[1] Easy\n[2] Medium\n[3] Hard");
            difficulty = scn.nextInt();
        } while (difficulty < 1 || difficulty > 3);

        if (difficulty == 2) difficulty = 3;
        else if (difficulty == 3) difficulty = 6;

        //difficulty = 6;
        // initialize decks
        deck = new CardDeck();
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
            if (!playerLeft) break;

            dealer.giveCard(deck.drawCard());
            dealer.giveCard(deck.drawCard());

            for (Player p : players) {
                p.giveCard(deck.drawCard(), p.getHand(0));
                p.giveCard(deck.drawCard(), p.getHand(0));
                p.bet(10);
            }




            /*Give each player and dealer cards.. dealar as to reveal first card (?)
            For each player ask for bet (or leave by typing "leave"). After bet show players cards and let him hit or stay
            After every player is done reveal each players and dealers cards.
            Pay out player twice his bet (since player already payed in the beginning)
            Kick out player without money and ask whether player wants to join*/
            break; //ToDo remove after it works
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

        p.giveCard(card, p.getHand(0));
        p.giveCard(c2, p.getHand(0));
        p.splitHand(p.getHand(0));

        //for (Hand hand : p.getHand(0)) {
        //    System.out.println(hand);
        //}
        System.out.println(p.getHand(0).getCard(0));
        System.out.println(p.getHand(0).getCard(1));
    }
}
