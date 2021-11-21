package ch.uzh.softcon.one.statecontrol;

import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.turn.Turn;

public interface Command {

    void execute(Turn turn, Piece enemy);
    void undo();
}
