package ch.uzh.softcon.one;

import ch.uzh.softcon.one.Turn.Status;

public class TurnValidator {

    public static Status validateMove(Turn turn, boolean mustJump) {
        int fromX = turn.from.x(); int toX = turn.to.x();
        int fromY = turn.from.y(); int toY = turn.to.y();

        //Player tries to move enemy pieces
        if (Board.getPiece(fromX, fromY) != null &&
                Board.getPiece(fromX, fromY).color != turn.activePlayer) {
            return Status.ILLEGAL_TURN;
        }

        //Turn would result in a position outside the field
        if (isOutsideBoard(toX, toY)) {
            return Status.ILLEGAL_TURN;
        }

        //Another piece is at the turn destination
        //or the chosen "piece" does not exist
        if (Board.getPiece(toX, toY) != null
                || Board.getPiece(fromX, fromY) == null) {
            return Status.ILLEGAL_TURN;
        }

        //Attempt to move backwards but piece is not a king
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

        //Turn destination is not one diagonal or two diagonal if an enemy sits on the first diagonal
        if (mustJump) {
            if (Math.abs(fromX - toX) != 2
                    || Math.abs(fromY - toY) != 2) {
                return Status.JUMP_REQUIRED;
            }
            if (!isJumpPossible(fromX, fromY, toX, toY, turn.activePlayer)) {
                return Status.JUMP_REQUIRED;
            }
        } else if (!canMoveDiagonally(turn)) {
            return Status.ILLEGAL_TURN;
        }

        return turn.status;
    }

    /**
     * Enemy on diagonal
     * one tile diagonally behind the enemy must be free
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

    public static boolean isOutsideBoard(int x, int y) {
        return x > 7 || x < 0 || y > 7 || y < 0;
    }

    private static boolean canMoveDiagonally(Turn turn) {
        int toX = turn.from.x();
        int toY = turn.from.y();

        if (turn.activePlayer == Player.RED || Board.getPiece(toX, toY).isKing) {
            if (!isOutsideBoard(toX + 1, toY + 1)) {
                if (Board.getPiece(toX + 1, toY + 1) == null) return true;
            }
            if (!isOutsideBoard(toX - 1, toY + 1)) {
                if (Board.getPiece(toX - 1, toY + 1) == null) return true;
            }
        }

        if (turn.activePlayer == Player.WHITE || Board.getPiece(toX, toY).isKing) {
            if (!isOutsideBoard(toX + 1, toY - 1)) {
                if (Board.getPiece(toX + 1, toY - 1) == null) return true;
            }
            if (!isOutsideBoard(toX - 1, toY - 1)) {
                if (Board.getPiece(toX - 1, toY - 1) == null) return true;
            }
        }

        return false;
    }

}
