package ch.uzh.softcon.four.commands;

import ch.uzh.softcon.four.player.Player;
import ch.uzh.softcon.four.scoreboard.ScoreBoard;

public class CommandSaveScore implements Command {

    public CommandSaveScore() {
    }

    @Override
    public void execute(Player p) {
        ScoreBoard.saveScore(p);
    }

    @Override
    public void undo() {
        //Maybe implement in the future?
    }
}
