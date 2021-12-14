package ch.uzh.softcon.four.logic;

import ch.uzh.softcon.four.card.CardDeck;
import ch.uzh.softcon.four.player.Dealer;
import ch.uzh.softcon.four.player.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private static final Dealer dealer = new Dealer();
    private static final Player[] players = new Player[5];
    private static CardDeck deck;
    private static Map<String, Integer> bets = new HashMap<>();
    private static final Scanner scn = new Scanner(System.in);

    public static void initialize() { //Ugly put ok for first try
        // TODO Clear console?
        System.out.println("Welcome to BJ");
        // TODO Scoreboard.print
        // TODO Fancy Anleitung mit Befehlen. Einfafcherheitshalber alles mit Zahlen?
        /* [0] Stay
         * [1] Hit
         * [2] Split */

         int difficulty;

        do {
            System.out.print("Difficulty levels\n[1] Easy\n[2] Medium\n[3] Hard\nChoose a level: ");
            difficulty = scn.nextInt(); // TODO Check whether it is a int
        } while (difficulty < 1 || difficulty > 3);

        if (difficulty == 2) difficulty = 3;
        else if (difficulty == 3) difficulty = 6;

        System.out.println();
        takeNewPlayers(5);

        // initialize decks
        deck = CardDeck.getInstance();
        deck.fillDeck(difficulty);
    }

    private static void takeNewPlayers(int maxPlayers) {
        System.out.print("How many players want to join the table? ");
        Scanner scn = new Scanner(System.in);
        int countPlayers = scn.nextInt(); // Todo check first if int.. otherwise crash
        while (countPlayers < 0 || countPlayers > maxPlayers) {
            System.out.print("\nPlease insert a number between 0 and " + maxPlayers + ": ");
            countPlayers = scn.nextInt();
        }

        // enter names of players
        for (int i = 0; i < countPlayers; ++i) {
            int nextAvailableSeat = 0;
            while (nextAvailableSeat < 5 && players[nextAvailableSeat] != null) ++nextAvailableSeat;
            System.out.print("Player " + (nextAvailableSeat+1) + " please insert your name: ");
            players[nextAvailableSeat] = new Player(scn.next());
        }
    }

    public static void startNewRound() {
        dealer.giveCard(deck.drawCard());
        dealer.giveCard(deck.drawCard());
    }

    public static boolean checkPlayerLeft() {
        boolean playerLeft = false;
        for (Player p : players) {
            if (p != null) {
                playerLeft = true;
                break;
            }
        }
        return playerLeft;
    }

    public static void takeBets(int playerIndex) {
        if (players[playerIndex] == null) return;
        if (bets.get(players[playerIndex].getName()) == null) {
            boolean retry;
            int bet = 0;
            do {
                retry = false;
                System.out.print(players[playerIndex].getName() + " enter your bet or type \"leave\" to leave: ");
                String tmp = scn.next();
                if (tmp.equals("leave")) {
                    players[playerIndex] = null;
                    System.out.println();
                    return;
                } else {
                    try {
                        bet = Integer.parseInt(tmp);
                    } catch (Exception e) {
                        retry = true;
                        System.out.println("Could not recognize your input. Try again.");
                    }
                }
            } while (retry);

            bets.put(players[playerIndex].getName(), bet);
        }
        try {
            players[playerIndex].bet(bets.get(players[playerIndex].getName()));
        } catch (Exception ex) {
            // Remove bet
            bets.put(players[playerIndex].getName(), null);
            System.out.println("It looks like you have not enough money. Try to bet less.");
        }
    }

    public static void play(int playerIndex, int handIndex) {
        if (players[playerIndex] == null) return;
        // Give player 2 cards only on first call.
        if(players[playerIndex].amountHands() <= 1) {
            distributeCards(playerIndex, handIndex);
            distributeCards(playerIndex, handIndex);
        }
        String move;
        // Ask player for move
        do {
            System.out.print("Moves\n[0] Stay\n[1] Hit\n[2] Split\n" + players[playerIndex].getName() + ", please enter your move for your " + (handIndex + 1) + ". hand : ");
            move = scn.next();
            if (move.equals("1")) {
                distributeCards(playerIndex, handIndex);
                if (players[playerIndex].getHand(handIndex).points() == 21) break;
                if (players[playerIndex].getHand(handIndex).points() > 21) {
                    // ToDo set points to -1 when over 21
                    //players[playerIndex].getHand(handIndex).setPoints(-1);
                    break;
                }
            } else if (move.equals("2")) {
                try {
                    players[playerIndex].splitHand(players[playerIndex].getHand(handIndex), bets.get(players[playerIndex].getName()));
                    // For every split do play for current hand and new (splipped) hand
                    play(playerIndex, handIndex);
                    play(playerIndex, handIndex+1);
                    return;
                }
                catch (Exception ex) {
                    System.out.println("Could not split your hand because of " + ex.getMessage() + ". Please try again.");
                }
            } else if (!move.equals("0")) {
                System.out.println("Could not recognize your input. Try again.");
            }
        } while (!move.equals("0"));
        System.out.println();
    }

    public static void playDealer() {
        while (dealer.getHand(0).points() < 17) dealer.giveCard(deck.drawCard());
        // ToDo set points to -1 when over 21
        //if (dealer.getHand(0).points() > 21) dealer.getHand(0).setPoints(-1);
    }

    public static void distributeCards(int playerIndex, int handIndex) {
        players[playerIndex].giveCard(deck.drawCard(), players[playerIndex].getHand(handIndex));
    }

    public static void evaluate() {
        for (Player p : players) {
            if (p == null) continue;
            int bet = bets.get(p.getName());
            int winAmount = 0;
            for (int i = 0; i < p.amountHands(); ++i) {
                if (p.getHand(i).points() > dealer.getHand(0).points()) {
                    p.pay(bet);
                    winAmount += bet;
                }
                else if (p.getHand(i).points() == dealer.getHand(0).points()) {
                    p.pay(bet);
                }
                else {
                    winAmount -= bet;
                }
            }
            System.out.println("In this round " + p.getName() + " achieved CHF " + winAmount + ".-");
        }
        System.out.println();
    }

    public static void conclude() {
        bets = new HashMap<>();
        dealer.clearHands();

        int availableSeats = 0;
        for (int i = 0; i < 5; ++i) {
            if (players[i] != null && players[i].balance() > 0) players[i].clearHands();
            else {
                players[i] = null;
                ++availableSeats;
            }
        }
        System.out.print("New game new luck.");
        if (availableSeats > 0) {
            System.out.println(" There are " + availableSeats + " seats available. ");
            takeNewPlayers(availableSeats);
        }
        else System.out.println(" Unfortunately there are no seats available currently. The game will continue.");
    }
}