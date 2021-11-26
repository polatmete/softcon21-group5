package ch.uzh.softcon.one.statecontrol;

import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.turn.Turn;

public interface Command {

    boolean execute();
    void undo();
}
