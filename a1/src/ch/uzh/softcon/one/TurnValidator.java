package ch.uzh.softcon.one;

import ch.uzh.softcon.one.Turn.Status;

public class TurnValidator {

    public static Status validateMove(Turn turn, boolean mustJump) {
        int fromX = turn.from.x(); int toX = turn.to.x();
        int fromY = turn.from.y(); int toY = turn.to.y();

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
                return toY > fromY ? turn.status : Status.ILLEGAL_TURN;
            } else {
                return toY < fromY ? turn.status : Status.ILLEGAL_TURN;
            }
        }

        //Turn destination is not one diagonal or two diagonal if an enemy sits on the first diagonal
        if (mustJump) {
            if (!isJumpPossible(toX, toY, turn.activePlayer)) {
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
    public static boolean isJumpPossible(int x, int y, Player p) {
        if (p == Player.RED || Board.getPiece(x, y).isKing) {
            if (!isOutsideBoard(x + 1, y + 1) && !isOutsideBoard(x + 2, y + 2)) {
                if (Board.getPiece(x + 1, y + 1) != null
                        && Board.getPiece(x + 1, y + 1).color != p) {
                    if (Board.getPiece(x + 2, y + 2) == null) {
                        return true;
                    }
                }
            }
            if (!isOutsideBoard(x - 1, y + 1) && !isOutsideBoard(x - 2, y + 2)) {
                if (Board.getPiece(x - 1, y + 1) != null
                        && Board.getPiece(x - 1, y + 1).color != p) {
                    if (Board.getPiece(x - 2, y + 2) == null) {
                        return true;
                    }
                }
            }
        }

        if (p == Player.WHITE || Board.getPiece(x, y).isKing) {
            if (!isOutsideBoard(x + 1, y - 1) && !isOutsideBoard(x + 2, y - 2)) {
                if (Board.getPiece(x + 1, y - 1) != null
                        && Board.getPiece(x + 1, y - 1).color != p) {
                    if (Board.getPiece(x + 2, y - 2) == null) {
                        return true;
                    }
                }
            }
            if (!isOutsideBoard(x - 1, y - 1) && !isOutsideBoard(x - 2, y - 2)) {
                if (Board.getPiece(x - 1, y - 1) != null
                        && Board.getPiece(x - 1, y - 1).color != p) {
                    if (Board.getPiece(x - 2, y - 2) == null) {
                        return true;
                    }
                }
            }
        }

        return false;
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
