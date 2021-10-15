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
        // isPieceStuck()
        // isJumpRequired()
        // setTurnStatus()
    }

    private static boolean checkWin(Turn turn) {
        // TODO: also check if p cannot move? -> lose
        if (turn.activePlayer == Player.RED) {
            return Board.pieceCountWhite == 0;
        } else {
            return Board.pieceCountRed == 0;
        }
    }

    // check if the player is forced to move somewhere and cannot just move anywhere
    // -> Turn object only for player needed
    private static boolean isJumpRequired(Turn turn) {
        // TODO
        return false;
    }

    private static boolean isJumpPossible(int posX, int posY) {
        // TODO
        return false;
    }

    // TODO: rename on UML from checkMovePossible
    // This must come before isPieceStuck() and isJumpRequired()
    /**
     * it is not stuck if:
     *  - it can move in any of its two or four diagonal directions
     *  - it can capture any enemy pieces on the two or four diagonally adjacent tiles
     */
    private static boolean isPieceStuck(Turn turn) {
        int x = turn.status == Turn.Status.COMPLETED ? turn.to.x() : turn.from.x();
        int y = turn.status == Turn.Status.COMPLETED ? turn.to.y() : turn.from.y();

        // TODO: From where does the red player start?
        if (turn.activePlayer == Player.RED || Board.getPiece(turn.from).isKing) {
            if (x > 1 && y < 8) {
                if (Board.getPiece(x - 1, y + 1) == null) return false;
            }
            if (x < 8 && y < 8) {
                if (Board.getPiece(x + 1, y + 1) == null) return false;
            }
        }

        if (turn.activePlayer == Player.WHITE || Board.getPiece(turn.from).isKing) {
            if (x > 1) {
                if (Board.getPiece(x - 1, y - 1) == null) return false;
            } else {
                if (Board.getPiece(x + 1, y - 1) == null) return false;
            }
        }

        return !isJumpPossible(x, y);
    }

    private static boolean checkTransformNeeded(Turn turn) {
        if (turn.activePlayer == Player.RED) {
            return turn.to.y() == 0;
        } else {
            return turn.to.y() == 7;
        }
    }

    private static void executeCapture(Turn turn) {
        int enemyX = (turn.from.x() + turn.to.x()) / 2;
        int enemyY = (turn.from.y() + turn.to.y()) / 2;
        Board.removePiece(enemyX, enemyY);
        Board.movePiece(turn);
    }

    private static Turn setTurnStatus(Turn turn) {
        // TODO: Implement and add to UML!
    }
}