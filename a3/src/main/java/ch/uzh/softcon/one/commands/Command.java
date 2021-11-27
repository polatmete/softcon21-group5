package ch.uzh.softcon.one.commands;

public interface Command {

    boolean execute();
    void undo();
}
