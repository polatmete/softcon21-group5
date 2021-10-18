package ch.uzh.softcon.one;

import ch.uzh.softcon.one.Turn.Status;

public class TurnValidator {


    /**
     * Checks if the attempted turn, whether a jump or a move, is valid.
     * @param turn Current turn
     * @param hasToJump Does the player have to make a jump?
     * @return Returns the in which the move (provisionally) ends.
     */
    public static Status validateMove(Turn turn, boolean hasToJump) {
        int fromX = turn.from.x(); int toX = turn.to.x();
        int fromY = turn.from.y(); int toY = turn.to.y();

        // Player tries to move a non-existent piece or an enemy piece
        if (Board.getPiece(fromX, fromY) == null ||
                Board.getPiece(fromX, fromY).color != turn.activePlayer) {
            return Status.ILLEGAL_TURN;
        }

        // Turn would result in a position outside the field
        if (isOutsideBoard(toX, toY)) {
            return Status.ILLEGAL_TURN;
        }

        //Another piece is at the turn destination
        if (Board.getPiece(toX, toY) != null) {
            return Status.ILLEGAL_TURN;
        }

        // Attempt to move backwards but piece is not a king
        Piece piece = Board.getPiece(fromX, fromY);
        if (!piece.isKing) {
            if (turn.activePlayer == Player.RED) {
                if (toY < fromY) {
                    return Status.ILLEGAL_TURN;
                }
            } else {
                if (toY > fromY) {
                    return Status.ILLEGAL_TURN;
                }
            }
        }

        // A jump has to be performed but the turn is not a (possible) jump
        if (hasToJump) {
            if (Math.abs(fromX - toX) != 2
                    || Math.abs(fromY - toY) != 2) {
                return Status.JUMP_REQUIRED;
            }
            if (!isJumpPossible(fromX, fromY, toX, toY, turn.activePlayer)) {
                return Status.JUMP_REQUIRED;
            }
        // A move has to be performed but the turn is not a (possible) move
        } else {
            if (Math.abs(fromX - toX) != 1
                    || Math.abs(fromY - toY) != 1) {
                return Status.ILLEGAL_TURN;
            }
            if (!canMoveDiagonally(toX, toY)) {
                return Status.ILLEGAL_TURN;
            }
        }

        return turn.status;
    }

    /**
     * Checks if the desired coordinates are outside the board.
     * @param x Targeted x axis
     * @param y Targeted y axis
     * @return Is outside the board
     */
    public static boolean isOutsideBoard(int x, int y) {
        return x > 7 || x < 0 || y > 7 || y < 0;
    }

    /**
     * Checks if a jump in a desired direction is possible.
     * @param fromX From x axis
     * @param fromY From y axis
     * @param toX To x axis
     * @param toY To y axis
     * @param p Active player
     * @return Jump is possible
     */
    public static boolean isJumpPossible(int fromX, int fromY, int toX, int toY, Player p) {
        int enemyX = (fromX + toX) / 2;
        int enemyY = (fromY + toY) / 2;

        return !isOutsideBoard(enemyX, enemyY)
                && !isOutsideBoard(toX, toY)
                && Board.getPiece(enemyX, enemyY) != null
                && Board.getPiece(enemyX, enemyY).color != p
                && Board.getPiece(toX, toY) == null;
    }

    /**
     * Checks if a move in a desired direction is possible.
     * @param toX To x axis
     * @param toY To y axis
     * @return Diagonal move is possible
     */
    public static boolean canMoveDiagonally(int toX, int toY) {
        return !isOutsideBoard(toX, toY)
                && Board.getPiece(toX, toY) == null;
    }
}