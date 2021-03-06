package ch.uzh.softcon.four.logic;

import ch.uzh.softcon.four.card.Card;
import ch.uzh.softcon.four.card.CardDeck;
import ch.uzh.softcon.four.card.Hand;
import ch.uzh.softcon.four.commands.CommandPrintScore;
import ch.uzh.softcon.four.commands.CommandSaveScore;
import ch.uzh.softcon.four.exceptions.card.CardHiddenException;
import ch.uzh.softcon.four.exceptions.card.NullCardException;
import ch.uzh.softcon.four.exceptions.hand.NoSuchHandException;
import ch.uzh.softcon.four.exceptions.hand.NullHandException;
import ch.uzh.softcon.four.player.Dealer;
import ch.uzh.softcon.four.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {

    private static Dealer dealer = new Dealer();
    private static Player[] players = new Player[5];
    private static CardDeck deck;
    private static Map<Player, Integer> bets = new HashMap<>();
    private static Scanner scn = new Scanner(System.in);

    public static void initialize() { // Set difficulty and players, initialize some variables
        System.out.println("Welcome to Black Jack!");
        new CommandPrintScore().execute(null);

        // Get difficulty level of game and map it to # sets used for deck
        int difficulty;
        do {
            System.out.print("Difficulty levels\n[1] Easy\n[2] Medium\n[3] Hard\nChoose a level: ");
            try {
                String tmp = scn.nextLine();
                difficulty = Integer.parseInt(tmp);  // Not nice but at least we can catch wrong inputs from the user
                if (difficulty < 1 || difficulty > 3) System.err.println("Could not recognize your input. Please " +
                        "enter a number between 1 and 3.\n");
            } catch (Exception ex) {
                System.err.println("Could not recognize your input. Please enter a number between 1 and 3.\n");
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
            System.out.print("There are " + maxPlayers + " seats available. How many players want to join the table?: ");
            try {
                String tmp = scn.nextLine();
                countPlayers = Integer.parseInt(tmp); // Not nice but at least we can catch wrong inputs from the user
                if (countPlayers < 0 || countPlayers > maxPlayers) System.err.println("Could not recognize your input." +
                        " Please enter a number between 0 and " + maxPlayers + ".\n");
            } catch (Exception ex) {
                System.err.println("Could not recognize your input. Please enter a number between 0 and " + maxPlayers + ".\n");
                countPlayers = -1;
            }
        } while (countPlayers < 0 || countPlayers > maxPlayers);

        // Ask for names of the new players
        for (int i = 0; i < countPlayers; ++i) {
            int nextAvailableSeat = 0;
            while (nextAvailableSeat < 5 && players[nextAvailableSeat] != null) ++nextAvailableSeat;
            System.out.print("Player " + (nextAvailableSeat+1) + " please enter your name (max 9 characters): ");
            String name = scn.nextLine();
            // only take first 9 chars to not mess up table
            players[nextAvailableSeat] = new Player(name.substring(0, Math.min(9, name.length())));
            if (name.contains("maettuu")) {
                System.out.println("Welcome to the game, \u001B[94m" + name + "\u001B[0m! Lovely to see our MVP today!");
                players[nextAvailableSeat].pay(50);
            }
            if (name.contains("flash1232") || name.contains("jemaie")
                    || name.contains("polatmete") || name.contains("alstefa")) {
                System.out.println("Welcome to the game, \u001B[93m" + name + "\u001B[0m! Lovely to see a VIP today!");
                players[nextAvailableSeat].pay(25);
            }
        }
    }

    public static void startNewRound() { // Helper methode to give every player and the dealer 2 cards at the beginning
        for (int i = 0; i < 5; ++i) { // Give every player 2 cards at the beginning
            if (players[i] == null) continue;
            distributeCards(i, 0);
            distributeCards(i, 0);
        }
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
            System.out.print(IOFormatter.formatOutput("\nTurn: " + players[playerIndex].getName(),
                    true, "Please enter your bet or type \"leave\" to leave: "));
            String tmpInput = scn.nextLine();
            if (tmpInput.equals("leave")) { // Leave when user enters "leave"
                new CommandSaveScore().execute(players[playerIndex]); // Check whether player made it in the scoreboard
                players[playerIndex] = null;
                System.out.println(); // New line for better design
                return;
            } else { // Otherwise, try to convert string to int
                try {
                    bet = Integer.parseInt(tmpInput);
                    if (bet <= 0) throw new Exception("Invalid input!");
                } catch (Exception ex) { // If input is no int then ask player again for input
                    retry = true;
                    System.err.println("Could not recognize your input. Please try again.");
                }
            }
        } while (retry);

        try {
            players[playerIndex].bet(bet);
            bets.put(players[playerIndex], bet); // Save bets for later
        } catch (Exception ex) { // If bet could not be made (e.g. to less money)
            System.err.println("Your bet could not be placed: " + ex.getMessage() + " Please try again.");
            takeBets(playerIndex);
        }
    }

    public static void play(int playerIndex, int handIndex) { // Here, the main gameplay happens
        try {
            // Since in main for every "seat" this method is executed we have to skip the empty seats
            // If player already got 21 with the first 2 cards also skip
            if (players[playerIndex] == null || players[playerIndex].getHand(0).points() == 21) return;
        } catch (NullHandException e) {/* */}

        // Ask player for move
        String move;
        do {
            System.out.print(IOFormatter.formatOutput("\nTurn: " + players[playerIndex].getName() +
                    " (" + (handIndex + 1) + ". hand)", true, "Please enter your move [0|1|2]: "));
            move = scn.nextLine();
            if (move.equals("1")) {
                distributeCards(playerIndex, handIndex);
                try {
                    Hand hand = players[playerIndex].getHand(handIndex);
                    int points = hand.points();
                    //Aces can be either worth 1 or 11 points. When the player decides every ace on his hand is worth 1,
                    //we need to subtract 10 of the total points for each ace. If he counts it as 11, it doesn't matter.
                    for (int i = 0; i < hand.size(); i++) {
                        if (hand.getCard(i).getRank() == Card.Rank.ACE) {
                            points -= 10;
                        }
                    }
                    if (points >= 21) { // Auto exit when hand >= 21 points
                        System.out.println(IOFormatter.formatOutput("\nTurn: " + players[playerIndex].getName()
                                + " (" + (handIndex + 1) + ". hand)", true, "You got " + points +
                                " points on this hand and therefore cannot hit or split any more."));
                        break;
                    }
                } catch (NullHandException | NullCardException | CardHiddenException ignored) {/* */}
            } else if (move.equals("2")) {
                try {
                    players[playerIndex].splitHand(players[playerIndex].getHand(handIndex));
                    // For every split run play for current hand and new (split) hand, which is appended at the end
                    play(playerIndex, handIndex);
                    play(playerIndex, players[playerIndex].amountHands() - 1); // Run play for last (newest) hand
                    return;
                }
                catch (Exception ex) {
                    System.err.println("Your hand could not be split: " + ex.getMessage() + " Please try again.");
                }
            }
            // We don't have to check for 0 since 0 ist just skip. Instead, we check whether input is something invalid. If so show error message
            else if (!move.equals("0")) {
                System.err.println("Could not recognize your input. Please enter a number between 0 and 2.");
            }
        } while (!move.equals("0")); // While input not 0 (hit) ask for input
        System.out.println(); // New line for better design
    }

    public static void playDealer() { // Dealer magic. First reveal both cards, then as long as dealer hands < 17 draw card
        try {
            dealer.getHand(0).reveal();
            int points = dealer.getHand(0).points();
            while (points < 17) {
                Card drawnCard = deck.drawCard();
                dealer.giveCard(drawnCard);
                points += drawnCard.getRank().getValue();
                for (int j = 0; j < dealer.getHand(0).size(); j++) {
                    if (points > 21 && drawnCard.getRank() == Card.Rank.ACE) {
                        points -= 10;
                    }
                }
            }
        } catch (NullHandException | CardHiddenException ignored) { }
        System.out.println(IOFormatter.formatOutput("\nTurn: Dealer", true,
                "This round has ended. Evaluation:\n"));
    }

    private static void distributeCards(int playerIndex, int handIndex) { // Helper method to give cards
        try {
            players[playerIndex].giveCard(deck.drawCard(), players[playerIndex].getHand(handIndex));
        } catch (NullHandException | NoSuchHandException ignored) { }
    }

    public static void evaluate() { // Check for each player and hand who has won
        int dealerHandPoints; // Get points of dealer
        try {
            dealerHandPoints = dealer.getHand(0).points();
            for (int i = 0; i < dealer.getHand(0).size(); i++) {
                if (dealerHandPoints > 21 && dealer.getHand(0).getCard(i).getRank() == Card.Rank.ACE) {
                    dealerHandPoints -= 10;
                }
            }
            // If dealers points > 21 set it to -1. This makes comparing later on much easier
            if (dealerHandPoints > 21) dealerHandPoints = -1;
            for (Player p : players) {
                if (p == null) continue;
                int bet = bets.get(p);
                int winAmount = 0;
                for (int i = 0; i < p.amountHands(); ++i) {
                    int playerHandPoints = p.getHand(i).points(); // Get points for each hand of player
                    for (int j = 0; j < p.getHand(i).size(); j++) {
                        if (playerHandPoints > 21 && p.getHand(i).getCard(j).getRank() == Card.Rank.ACE) {
                            playerHandPoints -= 10;
                        }
                    }
                    // If hand points > 21 set it to -2. This makes comparing later on much easier. -2 because if dealer and player gets > 21 player still looses
                    if (playerHandPoints > 21) playerHandPoints = -2;

                    // Compare points
                    if (playerHandPoints < dealerHandPoints) winAmount -= bet;
                    else if (playerHandPoints == dealerHandPoints) p.pay(bet); // Player already paid. On draw give player his money back
                    else {
                        p.pay(2*bet); // Player already paid. Give player twice his bet back such that he makes + his bet
                        winAmount += bet;
                    }
                }
                if (winAmount > 0) System.out.println("In this round " + p.getName() + " won $" + winAmount + ".");
                else if (winAmount == 0) System.out.println("In this round " + p.getName() + " neither won nor lost.");
                else {
                    winAmount = Math.abs(winAmount);
                    System.out.println("In this round " + p.getName() + " lost $" + winAmount + ".");
                }
            }
            System.out.println();
        } catch (NullHandException | NullCardException | CardHiddenException ignored) { }
    }

    public static void conclude() { // Clear hands, kick out players without money, allow new players to join
        bets = new HashMap<>(); // Delete all bets
        dealer.clearHands(); // Clear the hand of the dealer

        int availableSeats = 0;
        for (int i = 0; i < 5; ++i) {
            if (players[i] != null && players[i].getBalance() > 0) players[i].clearHands(); // Clear hands of all players
            else { // Kick player with no money
                if (players[i] != null) {
                    System.out.println(players[i].getName() + " had no money anymore and was kicked out.");
                    new CommandSaveScore().execute(players[i]); // Check whether player made it in the scoreboard
                }
                players[i] = null;
                ++availableSeats;
            }
        }
        System.out.println("\n=== New game new luck ===");
        if (availableSeats > 0) takeNewPlayers(availableSeats); // Check whether seats are free and ask new players to join
        else {
            System.out.print("Unfortunately there are no seats available currently.\nPress enter to continue the game... ");
            scn.nextLine(); // Don't really care about the input, enter is important
        }
    }

    protected static Player[] getPlayers() {
        return players;
    }

    protected static Dealer getDealer() {
        return dealer;
    }
}