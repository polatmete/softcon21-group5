package ch.uzh.softcon.one;

public class TurnHandler {

    public static void runTurnSequence(Turn turn) {
        if (checkWin(turn)) {
            Game.win(turn.activePlayer);
        }

        //restliche checks

        if (checkTransformNeeded(turn)) {
            Board.getPiece(turn.to).setKing();
        }

    }

    public static boolean checkWin(Turn turn) {
        // TODO: also check if p cannot move? -> lose
        if (turn.activePlayer == Player.RED) {
            return Board.pieceCountWhite == 0;
        } else {
            return Board.pieceCountRed == 0;
        }
    }

    // check if the player is forced to move somewhere and cannot just move anywhere
    // -> Turn object only for player needed
    public static boolean checkJumpRequired(Turn turn) {
        // TODO
        return false;
    }

    public static boolean checkMovePossible(Turn turn) {
        // TODO
        return false;
    }

    public static boolean checkTransformNeeded(Turn turn) {
        if (turn.activePlayer == Player.RED) {
            return turn.to.y() == 0;
        } else {
            return turn.to.y() == 7;
        }
    }

    public static void executeCapture(Turn turn) {
        int enemyX = (turn.from.x() + turn.to.x()) / 2;
        int enemyY = (turn.from.y() + turn.to.y()) / 2;
        Board.removePiece(enemyX, enemyY);
        Board.movePiece(turn);
    }

}