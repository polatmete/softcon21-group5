package ch.uzh.softcon.four;

import ch.uzh.softcon.four.logic.Game;

public class Main {
    public static void main(String[] args) {
        Game.initialize();
        System.out.println("\n");
        while (true) {
            Game.startNewRound(); // First give dealer 2 cards

            boolean quitGame = false;
            for (int i = 0; i < 5; ++i) {
                Game.takeBets(i);

                quitGame = !Game.checkPlayerLeft();
                if(quitGame) break; // If all players left end game

                Game.play(i, 0);
            }
            if (quitGame) break;

            Game.playDealer();
            Game.evaluate();
            Game.conclude();
        }
        System.out.println("\nIt seems like no one wants to play at this table any more.. Tank you for joining and bye for now ;)");
        // TODO Print ScoreBoard
    }
}
