package ch.uzh.softcon.four.logic;

import ch.uzh.softcon.four.card.CardDeck;
import ch.uzh.softcon.four.player.Dealer;
import ch.uzh.softcon.four.player.Player;
//TODO import ch.uzh.softcon.four.scoreboard.ScoreBoard;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private static final Dealer dealer = new Dealer();
    private static final Player[] players = new Player[5];
    private static CardDeck deck;
    private static Map<String, Integer> bets = new HashMap<>();
    private static final Scanner scn = new Scanner(System.in);

    public static void initialize() { // Set difficulty and players, initialize some variables
        System.out.println("Welcome to Black Jack!");
        // TODO ScoreBoard.printScore();

        // Get difficulty level of game and map it to # sets used for deck
        int difficulty;
        do {
            System.out.print("Difficulty levels\n[1] Easy\n[2] Medium\n[3] Hard\nChoose a level: ");
            try {
                String tmp = scn.nextLine();
                difficulty = Integer.parseInt(tmp);  // Not nice but at least we can catch wrong inputs from the user
                if (difficulty < 1 || difficulty > 3) System.out.println(IOFormatter.formatErrorMessage("Could not recognize your input. Please enter a number between 1 and 3.\n"));
            } catch (Exception ex) {
                System.out.println(IOFormatter.formatErrorMessage("Could not recognize your input. Please enter a number between 1 and 3.\n"));
                difficulty = 0;
            }
        } while (difficulty < 1 || difficulty > 3);

        // Mapping difficulty to # sets to be used
        if (difficulty == 2) difficulty = 3;
        else if (difficulty == 3) difficulty = 6;

        System.out.println(); // New line for better design
        takeNewPlayers(5); // Ask for players to join the table

        // Initialize decks
        deck = CardDeck.getInstance();
        deck.fillDeck(difficulty); // Fill deck with # sets chose above
    }

    private static void takeNewPlayers(int maxPlayers) {
        // Ask for # new players
        int countPlayers;
        do {
            System.out.print("How many players want to join the table?: ");
            try {
                String tmp = scn.nextLine();
                countPlayers = Integer.parseInt(tmp); // Not nice but at least we can catch wrong inputs from the user
                if (countPlayers < 0 || countPlayers > maxPlayers) System.out.println(IOFormatter.formatErrorMessage("Could not recognize your input. Please enter a number between 0 and " + maxPlayers + ".\n"));
            } catch (Exception ex) {
                System.out.println(IOFormatter.formatErrorMessage("Could not recognize your input. Please enter a number between 0 and " + maxPlayers + ".\n"));
                countPlayers = -1;
            }
        } while (countPlayers < 0 || countPlayers > maxPlayers);

        // Ask for names of the new players
        for (int i = 0; i < countPlayers; ++i) {
            int nextAvailableSeat = 0;
            while (nextAvailableSeat < 5 && players[nextAvailableSeat] != null) ++nextAvailableSeat;
            System.out.print("Player " + (nextAvailableSeat+1) + " please enter your name: ");
            players[nextAvailableSeat] = new Player(scn.nextLine());
        }
    }

    public static void startNewRound() { // Helper methode to give dealer 2 cards at the beginning
        dealer.giveCard(deck.drawCard());
        dealer.giveCard(deck.drawCard());
    }

    public static boolean checkPlayerLeft() { // Check whether players are left. Only used in main method
        boolean playerLeft = false;
        for (Player p : players) {
            if (p != null) {
                playerLeft = true;
                break;
            }
        }
        return playerLeft;
    }

    public static void takeBets(int playerIndex) { // Ask player for bet or to leave every time a new round begins
        if (players[playerIndex] == null) return; // Since in main for every "seat" this method is executed we have to skip the empty seats
        boolean retry;
        int bet = 0;
        do {
            retry = false;
            System.out.print(IOFormatter.formatOutput("\nTurn: " + players[playerIndex].getName(), true, "Please enter your bet or type \"leave\" to leave: "));
            String tmpInput = scn.nextLine();
            if (tmpInput.equals("leave")) { // Leave when user enters "leave"
                players[playerIndex] = null;
                System.out.println(); // New line for better design
                return;
            } else { // Otherwise, try to convert string to int
                try {
                    bet = Integer.parseInt(tmpInput);
                } catch (Exception ex) { // If input is no int then ask player again for input
                    retry = true;
                    System.out.println(IOFormatter.formatErrorMessage("Could not recognize your input. Please try again."));
                }
            }
        } while (retry);

        try {
            players[playerIndex].bet(bet);
            bets.put(players[playerIndex].getName(), bet); // Save bets for later
        } catch (Exception ex) { // If bet could not be made (e.g. to less money)
            System.out.println(IOFormatter.formatErrorMessage("Your bet could not be placed: " + ex.getMessage() + " Please try again."));
            takeBets(playerIndex);
        }
    }

    public static void play(int playerIndex, int handIndex) { // Here, the main gameplay happens
        if (players[playerIndex] == null) return;  // Since in main for every "seat" this method is executed we have to skip the empty seats

        if(players[playerIndex].amountHands() <= 1) { // Give player 2 cards only on first call
            distributeCards(playerIndex, handIndex);
            distributeCards(playerIndex, handIndex);
        }
        // Ask player for move
        String move;
        do {
            System.out.print(IOFormatter.formatOutput("\nTurn: " + players[playerIndex].getName() + ", " + (handIndex + 1) + ". hand", true, "Please enter your move: "));
            move = scn.nextLine();
            if (move.equals("1")) {
                distributeCards(playerIndex, handIndex);
                if (players[playerIndex].getHand(handIndex).points() >= 21) break; // Auto exit when hand > 21 points
            } else if (move.equals("2")) {
                try {
                    players[playerIndex].splitHand(players[playerIndex].getHand(handIndex), bets.get(players[playerIndex].getName()));
                    // For every split run play for current hand and new (split) hand, which is appended at the end
                    play(playerIndex, handIndex);
                    play(playerIndex, players[playerIndex].amountHands() - 1); // Run play for last (newest) hand
                    return;
                }
                catch (Exception ex) {
                    System.out.println(IOFormatter.formatErrorMessage("Your hand could not be split: " + ex.getMessage() + " Please try again."));
                }
            } else if (!move.equals("0")) { // We don't have to check for 0 since 0 ist just skip. Instead, we check whether input is something invalid. If so show error message
                System.out.println(IOFormatter.formatErrorMessage("Could not recognize your input. Please enter a number between 0 and 2."));
            }
        } while (!move.equals("0")); // While input not 0 (hit) ask for input
        System.out.println(); // New line for better design
    }

    public static void playDealer() { // Dealer magic. First reveal both cards, then as long as dealer hands < 17 draw card
        dealer.getHand(0).reveal();
        while (dealer.getHand(0).points() < 17) dealer.giveCard(deck.drawCard());
    }

    private static void distributeCards(int playerIndex, int handIndex) { // Helper method to give cards
        players[playerIndex].giveCard(deck.drawCard(), players[playerIndex].getHand(handIndex));
    }

    public static void evaluate() { // Check for each player and hand who has won
        int dealerHandPoints = dealer.getHand(0).points(); // Get points of dealer
        if (dealerHandPoints > 21) dealerHandPoints = -1; // If dealers points > 21 set it to -1. This makes comparing later on much easier
        for (Player p : players) {
            if (p == null) continue;
            int bet = bets.get(p.getName());
            int winAmount = 0;
            for (int i = 0; i < p.amountHands(); ++i) {
                int playerHandPoints = p.getHand(i).points(); // Get points for each hand of player
                if (playerHandPoints > 21) playerHandPoints = -2; // If hand points > 21 set it to -2. This makes comparing later on much easier. -2 because if dealer and player gets > 21 player still looses

                // Compare points
                if (playerHandPoints < dealerHandPoints) winAmount -= bet;
                else if (playerHandPoints == dealerHandPoints) p.pay(bet); // Player already paid. On draw give player his money back
                else {
                    p.pay(2*bet); // Player already paid. Give player twice his bet back such that he makes + his bet
                    winAmount += bet;
                }
            }
            System.out.println("In this round " + p.getName() + " achieved CHF " + winAmount + ".-");
        }
    }

    public static void conclude() { // Clear hands, kick out players without money, allow new players to join
        bets = new HashMap<>(); // Delete all bets
        dealer.clearHands(); // Clear the hand of the dealer

        int availableSeats = 0;
        for (int i = 0; i < 5; ++i) {
            if (players[i] != null && players[i].balance() > 0) players[i].clearHands(); // Clear hands of all players
            else { // Kick player with no money
                if (players[i] != null) System.out.println(players[i].getName() + " had no money left and was kicked out.");
                players[i] = null;
                ++availableSeats;
            }
        }
        System.out.print("\nNew game new luck.");
        if (availableSeats > 0) { // Check whether seats are free and ask new players to join
            System.out.println(" There are " + availableSeats + " seats available. ");
            takeNewPlayers(availableSeats);
        }
        else System.out.println(" Unfortunately there are no seats available currently. The game will continue.");
    }
}