package ch.uzh.softcon.four.commands;

import ch.uzh.softcon.four.player.Player;
import ch.uzh.softcon.four.scoreboard.ScoreBoard;

public class CommandPrintScore implements Command {

    public CommandPrintScore() {
    }

    @Override
    public void execute(Player p) {
        ScoreBoard.printScore();
    }

    @Override
    public void undo() {
        //Maybe implement in the future?
    }

}
