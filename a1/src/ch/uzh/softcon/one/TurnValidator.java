package ch.uzh.softcon.one;

import ch.uzh.softcon.one.Turn.Status;

public class TurnValidator {

    public static Status validateMove(Turn turn) {
        int fromX = turn.from.x(); int toX = turn.to.x();
        int fromY = turn.from.y(); int toY = turn.to.y();

        //Turn would result in a position outside the field
        if (toX >= 8 || toX <= 0 ||
                toY >= 8 || toY <= 0) {
            return Status.ILLEGAL_TURN;
        }

        //Another piece is at the turn destination
        if (Board.getPiece(toX, toY) != null) {
            return Status.ILLEGAL_TURN;
        }

        //Attempt to move backwards but piece is not a king
        Piece piece = Board.getPiece(fromX, fromY);
        if (! piece.isKing) {
            if (turn.activePlayer == Player.RED) {
                return toY < fromY ? turn.status : Status.ILLEGAL_TURN;
            } else {
                return toY > fromY ? turn.status : Status.ILLEGAL_TURN;
            }
        }

        //Turn destination is not one diagonal or two diagonal if an enemy sits on the first diagonal
        return !canMoveDiagonally(turn) ? Status.ILLEGAL_TURN : turn.status;
    }

    public static boolean canMoveDiagonally(Turn turn) {
        int x = turn.from.x();
        int y = turn.from.y();

        if (turn.activePlayer == Player.WHITE || Board.getPiece(turn.from).isKing) {
            if (x > 1 && y < 8) {
                if (Board.getPiece(x - 1, y + 1) == null) return true;
            }
            if (x < 8 && y < 8) {
                if (Board.getPiece(x + 1, y + 1) == null) return true;
            }
        }

        if (turn.activePlayer == Player.RED || Board.getPiece(turn.from).isKing) {
            if (x > 1) {
                return Board.getPiece(x - 1, y - 1) == null;
            } else {
                return Board.getPiece(x + 1, y - 1) == null;
            }
        }

        return false;
    }

    public static boolean isJumpPossible(int posX, int posY) {
        // TODO: is enemy on diagonal?
        // one tile diagonally behind the enemy must be free
        return false;
    }
}
