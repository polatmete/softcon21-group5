package ch.uzh.softcon.four;

import ch.uzh.softcon.four.commands.Command;
import ch.uzh.softcon.four.commands.CommandPrintScore;
import ch.uzh.softcon.four.logic.Game;
import ch.uzh.softcon.four.scoreboard.ScoreBoard;

public class Main {

    private static Command printScore;

    public static void main(String[] args) {
        printScore = new CommandPrintScore();
        Game.initialize(); // Set difficulty and players, initialize some variables
        System.out.println(); // New line for better design
        while (true) {
            for (int i = 0; i < 5; ++i) Game.takeBets(i); // Ask player for bet or to leave every time a new round begin
            if(!Game.checkPlayerLeft()) break; // Check whether a player is left after takeBets() since players can only leave at this point
            Game.startNewRound(); // Helper methode to give every player and the dealer 2 cards at the beginning
            for (int i = 0; i < 5; ++i) Game.play(i, 0); // Play with every player
            Game.playDealer(); // At the end dealer should draw cards
            Game.evaluate(); // Check for each player and hand who has won
            Game.conclude(); // Clear hands, kick out players without money, allow new players to join
        }
        System.out.println("\nIt seems like no one wants to play at this table any more.. Thank you for joining and bye for now ;)");
        printScore.execute(null);
    }
}