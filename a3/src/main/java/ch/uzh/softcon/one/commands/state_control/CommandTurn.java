package ch.uzh.softcon.one.commands.state_control;

import ch.uzh.softcon.one.abstraction.Board;
import ch.uzh.softcon.one.abstraction.GameHandling;
import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.commands.Command;
import ch.uzh.softcon.one.turn.Turn;
import ch.uzh.softcon.one.utils.MoveStorage;

import java.util.Map;
import java.util.Optional;

public class CommandTurn implements Command {

    private static Board boardInstance;

    private static boolean undone;
    private static boolean noMove;

    private final Turn turn;
    private final Player activePlayer;
    private final Piece capturedEnemy;

    public CommandTurn(Turn turn, Player activePlayer, Piece enemy) {
        this.turn = turn;
        this.activePlayer = activePlayer;
        this.capturedEnemy = enemy;
        boardInstance = Board.getInstance();
    }

    @Override
    public boolean execute() {
        boardInstance.movePiece(turn);
        MoveStorage.push(turn, activePlayer, capturedEnemy);
        undone = false;
        return true;
    }

    @Override
    public void undo() {
        if (!undone) {
            undoMove();
            undoMove();
        } else {
            GameHandling.setAndNotifyStatusChange("Player "
                    + GameHandling.activePlayer().toString().toLowerCase()
                    + ": A move has already been undone. Cannot undo more moves.");
        }
    }

    //Good luck reading & understanding this.
    public static void undoMove() {
        Map<Map<Turn, Player>, Piece> stackTop = MoveStorage.pop();
        if (stackTop != null) {
            noMove = false;
            Optional<Map<Turn, Player>> outerMap = stackTop.keySet().stream().findFirst();
            if (outerMap.isPresent()) {
                Map<Turn, Player> lastMove = outerMap.get();
                Optional<Turn> innerMap = lastMove.keySet().stream().findFirst();
                if (innerMap.isPresent()) {

                    //Create undo move and execute it
                    Turn oldMove = innerMap.get();
                    int oldXFrom = oldMove.from().x(); int oldYFrom = oldMove.from().y();
                    int oldXTo = oldMove.to().x(); int oldYTo = oldMove.to().y();
                    Turn undoMove = new Turn(oldXTo, oldYTo, oldXFrom, oldYFrom);
                    boardInstance.movePiece(undoMove);

                    //Since the coordinates only get swapped for the undoMove,
                    //it doesn't matter which coordinates we take to get the captured coordinates.
                    if (stackTop.get(lastMove) != null) {
                        Piece captured = stackTop.get(lastMove);
                        int capturedX = (oldXFrom + oldXTo) / 2;
                        int capturedY = (oldYFrom + oldYTo) / 2;
                        boardInstance.placePiece(capturedX, capturedY, captured);
                    }

                    //If a move was part of a multijump, the whole multijump shall be reverted
                    if (lastMove.get(oldMove) == MoveStorage.lastPlayer()) {
                        undoMove();
                    }

                    //Swap active player if necessary
                    if (GameHandling.activePlayer() != lastMove.get(oldMove)) {
                        GameHandling.changePlayer(
                                GameHandling.activePlayer() != Player.RED ? Player.RED : Player.WHITE);
                    }
                    undone = true;
                } else {
                    System.err.println("Cannot undo last move: Couldn't find entry. Stack my be corrupted.");
                }
            } else{
                System.err.println("Cannot undo last move: Couldn't find entry. Stack my be corrupted.");
            }
        } else {
            if (noMove) {
                GameHandling.setAndNotifyStatusChange("Player "
                        + GameHandling.activePlayer().toString().toLowerCase()
                        + ": A move must be made before it can be undone.");
            }
            noMove = true;
        }
    }
}