package ch.uzh.softcon.one.statecontrol;

import ch.uzh.softcon.one.abstraction.Board;
import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.turn.Turn;

import java.util.Map;
import java.util.Optional;

public class CommandTurn implements Command {

    public CommandTurn() {
    }

    @Override
    public void execute(Turn turn, Piece enemy) {
        Board.movePiece(turn);
        MoveStorage.push(turn, enemy);
    }

    @Override
    public void undo() {
        Map<Turn, Piece> lastMove = MoveStorage.pop();
        if (lastMove != null) {
            Optional<Turn> firstEntry = lastMove.keySet().stream().findFirst();
            if (firstEntry.isPresent()) {
                Turn oldMove = firstEntry.get();
                Turn undoMove = new Turn(oldMove.to().x(), oldMove.to().y(),
                        oldMove.from().x(), oldMove.from().y());
                Board.movePiece(undoMove);

                //Since the coordinates only get swapped for the undoMove,
                //it doesn't matter which coordinates we take to get the captured coordinates.
                if (lastMove.get(oldMove) != null) {
                    Piece captured = lastMove.get(oldMove);
                    int capturedX = (oldMove.from().x() + oldMove.to().x()) / 2;
                    int capturedY = (oldMove.from().y() + oldMove.to().y()) / 2;
                    Board.placePiece(capturedX, capturedY, captured);
                }
            }
        }
    }
}
