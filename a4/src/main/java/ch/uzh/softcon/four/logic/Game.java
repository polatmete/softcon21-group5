package ch.uzh.softcon.four.logic;

import ch.uzh.softcon.four.card.CardDeck;
import ch.uzh.softcon.four.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private static CardDeck deck;
    private static Map<String, Integer> bets = new HashMap<>();
    private static Scanner scn = new Scanner(System.in);

    public static void initDeck() {
        deck = CardDeck.getInstance();
    }

    public static void distributeCards(Player p) {
        p.giveCard(deck.drawCard(), p.getHand(0));
        //Todo
    }

    public static void takeBets(Player p) {
        int bet = -1;
        if (bets.get(p.getName()) == null) { //TODO Do better with while
            boolean retry = false;
            do {
                System.out.print(p.getName() + " enter your bet or type \"leave\" to leave: ");
                String tmp = scn.next();
                if (tmp.equals("l")) {
                    //TODO replace player in array with NULL maybe with exception?
                    return;
                } else {
                    try {
                        bet = Integer.parseInt(tmp);
                        retry = false;
                    } catch (Exception e) {
                        retry = true;
                    }
                }
            } while (retry);
        } else {
            bet = bets.get(p.getName()); // TODO Not double the bet.. bet should increase alway by initial bet..
            // NOTE FOR ME: You don't have to store the total amount of the bet... Just return the initial amount for each hand > dealers hand
        }

        // Add the bet to the current bet (relevant when splitting hand)
        int tmp = 0;
        if (bets.get(p.getName()) != null) tmp = bets.get(p.getName());
        bets.put(p.getName(), tmp + bet);
        p.bet(bet);
        //Todo
    }

    public static void play(Player p) {
        p.giveCard(deck.drawCard(), p.getHand(0));
        p.giveCard(deck.drawCard(), p.getHand(0));
        String move;

        do {
            System.out.print("Enter your move: ");
            move = scn.next();
            if (move.equals("1")) {
                distributeCards(p);
            } else if (move.equals("2")) {
                p.splitHand(p.getHand(0)); // TODO WRONG!!!
                takeBets(p);
                System.out.println(bets.get(p.getName()));
            }
        } while (!move.equals("0")); // TODO Error message (invalid move entered)
    }

    public static void evaluate(Player[] players) {
        //Todo
    }

    public static void conclude() {
        //Todo WINNER WINNER CHICKEN DINNER, NEW PLAYER?
    }
}