package ch.uzh.softcon.four;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.Card.Suit;
import ch.uzh.softcon.four.card.Card.Rank;
import ch.uzh.softcon.four.player.Player;


public class Main {
    private static Player[] players = new Player[5];
    private static int difficulty;

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
        // enter names of players
        // difficulty level: [e]asy: 1 deck / [m]edium: 3 decks / [h]]ard: 6 decks
        // initialize decks
    }

    private static void loop() {
        /*do {
            Give each player and dealer cars.. dealar as to reveal first card (?)
            For each player ask for bet (or leave by typing "leave"). After bet show players cards and let him hit or stay
            After every player is done reveal each players and dealers cards.
            Pay out player twice his bet (since player already payed in the beginning)
            Kick out player without money and ask whether player wants to join
        } while (gameGoesOn);

         */

        Card card = new Card(Suit.SPADES, Rank.ACE);
    }
}
