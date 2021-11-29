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
        try {
            return BoardLoader.loadBoard(file);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void undo() {
        //Maybe implement in the future?
    }
}
