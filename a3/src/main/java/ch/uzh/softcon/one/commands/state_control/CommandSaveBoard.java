package ch.uzh.softcon.one.commands.state_control;

import ch.uzh.softcon.one.commands.Command;
import ch.uzh.softcon.one.utils.BoardLoader;

public class CommandSaveBoard implements Command {

    public CommandSaveBoard() {
    }

    @Override
    public boolean execute() {
        BoardLoader.saveBoard();
        return true;
    }

    @Override
    public void undo() {
        //Maybe implement in the future?
    }
}
