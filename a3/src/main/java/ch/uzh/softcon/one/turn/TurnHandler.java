package ch.uzh.softcon.one.turn;

import ch.uzh.softcon.one.abstraction.Board;
import ch.uzh.softcon.one.abstraction.GameHandling;
import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.commands.Command;
import ch.uzh.softcon.one.commands.state_control.CommandTurn;

public class TurnHandler {

    /**
     * Starts the whole turn sequence with all checks and returns the status.
     * @param turn Current turn
     */
    public static void runTurnSequence(Turn turn) {
        boolean jumpRequired = isJumpRequired();

        // Validate the move or jump and return the status based on it
        if (!TurnValidator.validateMove(turn, jumpRequired)) return;

        // Execute the move or jump
        executeTurn(turn);

        // Check whether a player has won
        if (checkWin()) {
            GameHandling.win(GameHandling.activePlayer());
            return;
        }

        // Make a king out of the piece if it has reached the other side
        Piece activePiece = Board.getPiece(turn.to().x(), turn.to().y());
        if (checkTransformNeeded(turn)) {
            activePiece.promote();
            GameHandling.changePlayer(GameHandling.activePlayer() != Player.RED ? Player.RED : Player.WHITE);
            return;
        }

        // If the player had to make a jump it is possible he must continue with a multi-jump.
        if (jumpRequired) {
            activePiece.startMultiJump();
            if (isJumpRequired()) {
                GameHandling.setAndNotifyStatusChange("Player "
                        + GameHandling.activePlayer().toString().toLowerCase() + ": Another jump is required.");
                return;
            }
        }

        activePiece.endMultiJump();
        GameHandling.changePlayer(GameHandling.activePlayer() != Player.RED ? Player.RED : Player.WHITE);
    }

    /**
     * Takes care of moving or jumping a piece and removes the eaten enemy piece.
     * @param turn Current turn
     */
    private static void executeTurn(Turn turn) {
        Piece enemy = null;
        int enemyX = (turn.from().x() + turn.to().x()) / 2;
        int enemyY = (turn.from().y() + turn.to().y()) / 2;

        if (Math.abs(turn.from().x() - turn.to().x()) == 2 || Math.abs(turn.from().y() - turn.to().y()) == 2) {
            enemy = Board.getPiece(enemyX, enemyY);
            Board.removePiece(enemyX, enemyY);
        }
        Command move = new CommandTurn(turn, GameHandling.activePlayer(), enemy);
        move.execute();
    }

    /**
     * Checks if the enemy is stuck or has no pieces left.
     * Determines a winner based on these checks.
     * @return Current player has won
     */
    private static boolean checkWin() {
        if (isEnemyStuck(GameHandling.activePlayer())) {
            return true;
        }
        if (GameHandling.activePlayer() == Player.RED) {
            return Board.hasNoPieces(Player.WHITE);
        } else {
            return Board.hasNoPieces(Player.RED);
        }
    }

    /**
     * Checks if a piece has reached the other side of the board.
     * @param turn Current turn
     * @return Reached the end of the board
     */
    private static boolean checkTransformNeeded(Turn turn) {
        if (GameHandling.activePlayer() == Player.RED) {
            return turn.to().y() == Board.size() - 1;
        } else {
            return turn.to().y() == 0;
        }
    }

    /**
     * Checks if there are any jumps possible on the field for the current player.
     * If there is at least one possible jump, the player has to take it.
     * @return Player has to take a jump
     */
    private static boolean isJumpRequired() {
        Player p = GameHandling.activePlayer();
        for (int i = 0; i < Board.size(); i++) {
            for (int j = 0; j < Board.size(); j++) {
                if (Board.getPiece(i, j) == null || Board.getPiece(i, j).getColor() != p) {
                    continue;
                }
                if (Piece.activeMultiJump() && !Board.getPiece(i, j).isInMultiJump()) {
                    continue;
                }
                if (Board.getPiece(i, j).getColor() == Player.RED || Board.getPiece(i, j).isKing()) {
                    if (TurnValidator.isJumpPossible(i, j, i + 2, j + 2, p)
                            || TurnValidator.isJumpPossible(i, j, i - 2, j + 2, p)) {
                        return true;
                    }
                }
                if (Board.getPiece(i, j).getColor() == Player.WHITE || Board.getPiece(i, j).isKing()) {
                    if (TurnValidator.isJumpPossible(i, j, i + 2, j - 2, p)
                            || TurnValidator.isJumpPossible(i, j, i - 2, j - 2, p)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the enemy player cannot make any more moves.
     * @param p Active player
     * @return Enemy is stuck
     */
    private static boolean isEnemyStuck(Player p) {
        Player enemy = p == Player.RED ? Player.WHITE : Player.RED;

        for (int i = 0; i < Board.size(); i++) {
            for (int j = 0; j < Board.size(); j++) {
                if (Board.getPiece(i, j) == null || Board.getPiece(i, j).getColor() != enemy) {
                    continue;
                }
                if (Board.getPiece(i, j).getColor() == Player.RED || Board.getPiece(i, j).isKing()) {
                    if (TurnValidator.canMoveDiagonally(i + 1, j + 1)
                            || TurnValidator.isJumpPossible(i, j, i + 2, j + 2, enemy)
                            || TurnValidator.canMoveDiagonally(i - 1, j + 1)
                            || TurnValidator.isJumpPossible(i, j, i - 2, j + 2, enemy)){
                        return false;
                    }
                }
                if (Board.getPiece(i, j).getColor() == Player.WHITE || Board.getPiece(i, j).isKing()) {
                    if (TurnValidator.canMoveDiagonally(i + 1, j - 1)
                            || TurnValidator.isJumpPossible(i, j, i + 2, j - 2, enemy)
                            || TurnValidator.canMoveDiagonally(i - 1, j - 1)
                            || TurnValidator.isJumpPossible(i, j, i - 2, j - 2, enemy)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}