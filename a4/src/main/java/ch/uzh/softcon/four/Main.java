package ch.uzh.softcon.four;

import ch.uzh.softcon.four.logic.Game;
//TODO import ch.uzh.softcon.four.scoreboard.ScoreBoard;

public class Main {
    public static void main(String[] args) {
        Game.initialize(); // Set difficulty and players, initialize some variables
        System.out.println(); // New line for better design
        while (true) {
            Game.startNewRound(); // First, give dealer 2 cards

            boolean quitGame = false;
            for (int i = 0; i < 5; ++i) {
                Game.takeBets(i); // Ask player for bet or to leave every time a new round begins

                quitGame = !Game.checkPlayerLeft(); // Check whether a player is left after takeBets() since players can only leave at this point
                if(quitGame) break; // If all players left end game

                Game.play(i, 0); // Play with every player
            }
            if (quitGame) break; // If all players left end game

            Game.playDealer(); // At the end dealer should draw cards
            Game.evaluate(); // Check for each player and hand who has won
            Game.conclude(); // Clear hands, kick out players without money, allow new players to join
        }
        System.out.print("\nIt seems like no one wants to play at this table any more.. Tank you for joining and bye for now ;)");
        // TODO ScoreBoard.printScore();
    }
}
