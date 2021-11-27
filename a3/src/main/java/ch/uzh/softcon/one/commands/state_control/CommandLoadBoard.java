package ch.uzh.softcon.one.commands.state_control;

import ch.uzh.softcon.one.commands.Command;
import ch.uzh.softcon.one.utils.BoardLoader;

public class CommandLoadBoard implements Command {

    private final String file;

    public CommandLoadBoard(String fileName) {
        this.file = fileName;
    }

    @Override
    public boolean execute() {
        return BoardLoader.loadBoard(file);
    }

    @Override
    public void undo() {
        //Maybe implement in the future?
    }
}
