package ch.uzh.softcon.one.commands.theme_selector;

import ch.uzh.softcon.one.commands.Command;

public class NoCommand implements Command {

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public void undo() {

    }
}
