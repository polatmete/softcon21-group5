package ch.uzh.softcon.four.commands;

import ch.uzh.softcon.four.player.Player;

public interface Command {

    void execute(Player p);
    void undo();
}
