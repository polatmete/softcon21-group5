package ch.uzh.softcon.one;

import ch.uzh.softcon.one.Turn.Status;

public class TurnHandler {

    /**
     * Starts the whole turn sequence with all checks and returns the status.
     * @param turn Current turn
     * @return Current turn status
     */
    public static Status runTurnSequence(Turn turn) {
        boolean jumpRequired = isJumpRequired(turn);

        // Validate the move or jump and return the status based on it
        Status validationResult = TurnValidator.validateMove(turn, jumpRequired);
        if (validationResult == Status.ILLEGAL_TURN) {
            return Status.ILLEGAL_TURN;
        }
        if (validationResult == Status.JUMP_REQUIRED) {
            return Status.JUMP_REQUIRED;
        }

        // Execute the move or jump
        executeTurn(turn);

        // Make a king out of the piece if it has reached the other side
        if (checkTransformNeeded(turn)) {
            Board.getPiece(turn.to.x(), turn.to.y()).setKing();
        }

        // If the player had to make a jump it is possible he must continue with a multi-jump.
        if (jumpRequired && isJumpRequired(turn)) {
            return Status.JUMP_AGAIN;
        }

        // Check whether a player has won
        if (checkWin(turn)) {
            Game.win(turn.activePlayer);
        }

        return Status.COMPLETED;
    }

    /**
     * Checks if the enemy is stuck or has no pieces left.
     * Determines a winner based on these checks.
     * @param turn Current turn
     * @return Current player has won
     */
    private static boolean checkWin(Turn turn) {
        if (isEnemyStuck(turn.activePlayer)) {
            return true;
        }
        if (turn.activePlayer == Player.RED) {
            return Board.pieceCountWhite == 0;
        } else {
            return Board.pieceCountRed == 0;
        }
    }

    /**
     * Checks if there are any jumps possible on the field for the current player.
     * If there is at least one possible jump, the player has to take it.
     * @param turn Current turn
     * @return Player has to take a jump
     */
    private static boolean isJumpRequired(Turn turn) {
        for (int i = 0; i < Board.size(); i++) {
            for (int j = 0; j < Board.size(); j++) {
                if (Board.getPiece(i, j) == null || Board.getPiece(i, j).color != turn.activePlayer) {
                    continue;
                }
                if (Board.getPiece(i, j).color == Player.RED || Board.getPiece(i, j).isKing) {
                    if (TurnValidator.isJumpPossible(i, j, i + 2, j + 2, turn.activePlayer)
                            || TurnValidator.isJumpPossible(i, j, i - 2, j + 2, turn.activePlayer)) {
                        return true;
                    }
                }
                if (Board.getPiece(i, j).color == Player.WHITE || Board.getPiece(i, j).isKing) {
                    if (TurnValidator.isJumpPossible(i, j, i + 2, j - 2, turn.activePlayer)
                            || TurnValidator.isJumpPossible(i, j, i - 2, j - 2, turn.activePlayer)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if a piece has reached the other side of the board.
     * @param turn Current turn
     * @return Reached the end of the board
     */
    private static boolean checkTransformNeeded(Turn turn) {
        if (turn.activePlayer == Player.RED) {
            return turn.to.y() == Board.size() - 1;
        } else {
            return turn.to.y() == 0;
        }
    }

    /**
     * Takes care of moving or jumping a piece and removes the eaten enemy piece.
     * @param turn Current turn
     */
    private static void executeTurn(Turn turn) {
        int enemyX = (turn.from.x() + turn.to.x()) / 2;
        int enemyY = (turn.from.y() + turn.to.y()) / 2;

        if (Math.abs(turn.from.x() - turn.to.x()) == 2 || Math.abs(turn.from.y() - turn.to.y()) == 2) {
            Board.removePiece(enemyX, enemyY);
        }
        Board.movePiece(turn);
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
                if (Board.getPiece(i, j) == null || Board.getPiece(i, j).color != enemy) {
                    continue;
                }
                if (TurnValidator.canMoveDiagonally(i + 1, j + 1)
                        || TurnValidator.isJumpPossible(i, j, i + 2, j + 2, enemy)
                        || TurnValidator.canMoveDiagonally(i + 1, j - 1)
                        || TurnValidator.isJumpPossible(i, j, i + 2, j - 2, enemy)
                        || TurnValidator.canMoveDiagonally(i - 1, j + 1)
                        || TurnValidator.isJumpPossible(i, j, i - 2, j + 2, enemy)
                        || TurnValidator.canMoveDiagonally(i - 1, j - 1)
                        || TurnValidator.isJumpPossible(i, j, i - 2, j - 2, enemy)) {
                    return false;
                }
            }
        }
        return true;
    }
}