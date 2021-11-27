package ch.uzh.softcon.one.turn;

import ch.uzh.softcon.one.abstraction.Board;
import ch.uzh.softcon.one.abstraction.GameHandling;
import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;

public class TurnValidator {

    private static final Board boardInstance = Board.getInstance();

    /**
     * Checks if the attempted turn, whether a jump or a move, is valid.
     * @param turn Current turn
     * @param hasToJump Does the player have to make a jump?
     * @return Returns the in which the move (provisionally) ends.
     */
    public static boolean validateMove(Turn turn, boolean hasToJump) {
        int fromX = turn.from().x(); int toX = turn.to().x();
        int fromY = turn.from().y(); int toY = turn.to().y();
        Piece piece = boardInstance.getPiece(fromX, fromY);
        Player p = GameHandling.activePlayer();

        if (piece == null) return false;
        // Attempt to move backwards but piece is not a king
        if(!piece.isKing() && ((p == Player.RED && toY < fromY)|| p == Player.WHITE && toY > fromY)) {
            GameHandling.setAndNotifyStatusChange("Player " + p.toString().toLowerCase() + ": Non-King piece cannot move backwards.");
            return false;
        }

        // A jump has to be performed but the turn is not a (possible) jump
        if (hasToJump) {
            if (Piece.activeMultiJump() && !piece.isInMultiJump()) {
                GameHandling.setAndNotifyStatusChange("Player " + p.toString().toLowerCase() + ": Another piece is in a multi-jump already.");
                return false;
            }
            if (Math.abs(fromX - toX) != 2 || Math.abs(fromY - toY) != 2 || !isJumpPossible(fromX, fromY, toX, toY, p)) {
                GameHandling.setAndNotifyStatusChange("Player " + p.toString().toLowerCase() + ": A jump is required.");
                return false;
            }
        // A move has to be performed but the turn is not a (possible) move
        } else {
            if (Math.abs(fromX - toX) != 1 || Math.abs(fromY - toY) != 1 || !canMoveDiagonally(toX, toY)) {
                GameHandling.setAndNotifyStatusChange("Player " + p.toString().toLowerCase() + ": Desired move is not possible.");
                return false;
            }
        }

        //Another piece is at the turn destination
        if (boardInstance.getPiece(toX, toY) != null) {
            GameHandling.setAndNotifyStatusChange("Player " + p.toString().toLowerCase() + ": A piece blocks the desired destination.");
            return false;
        }
        return true;
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
                && boardInstance.getPiece(enemyX, enemyY) != null
                && boardInstance.getPiece(enemyX, enemyY).getColor() != p
                && boardInstance.getPiece(toX, toY) == null;
    }

    /**
     * Checks if a move in a desired direction is possible.
     * @param toX To x axis
     * @param toY To y axis
     * @return Diagonal move is possible
     */
    public static boolean canMoveDiagonally(int toX, int toY) {
        return !isOutsideBoard(toX, toY)
                && boardInstance.getPiece(toX, toY) == null;
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
}