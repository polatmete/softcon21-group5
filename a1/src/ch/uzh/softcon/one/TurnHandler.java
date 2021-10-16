package ch.uzh.softcon.one;

import ch.uzh.softcon.one.Turn.Status;

public class TurnHandler {

    public static Status runTurnSequence(Turn turn) {
        if (isJumpRequired(turn)) {
            setTurnStatus(turn, Status.JUMP_REQUIRED);
        }

        if (checkTransformNeeded(turn)) {
            Board.getPiece(turn.to.x(), turn.to.y()).setKing();
        }

        if (checkWin(turn)) { // TODO: last check
            Game.win(turn.activePlayer);
        }

        if (TurnValidator.validateMove(turn) == Status.ILLEGAL_TURN) {
            return Status.ILLEGAL_TURN;
        }

        // executeTurn()

        // isJumpRequired() again
        // executeTurn()

        // after turn executed re-check jumpRequired, transformNeeded, checkWin

        return turn.status;
    }

    private static boolean checkWin(Turn turn) {
        // TODO: also check if p cannot move? -> lose
        // isPieceStuck()?
        if (turn.activePlayer == Player.RED) {
            return Board.pieceCountWhite == 0;
        } else {
            return Board.pieceCountRed == 0;
        }
    }

    // check if the player is forced to move somewhere and cannot just move anywhere
    // -> Turn object only for player needed
    private static boolean isJumpRequired(Turn turn) {
        for (int i = 1; i <= Board.size; i++) {
            for (int j = 1; j <= Board.size; j++) {
                if (Board.getPiece(turn.from.x(), turn.from.y()).color == turn.activePlayer) {
                    if (TurnValidator.isJumpPossible(i, j)) {
                        return true;
                    }
                }
            }
        }
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
        TurnValidator.canMoveDiagonally(turn);
        return !TurnValidator.isJumpPossible(turn.from.x(), turn.from.y());
    }

    private static boolean checkTransformNeeded(Turn turn) {
        if (turn.activePlayer == Player.RED) {
            return turn.to.y() == 1;
        } else {
            return turn.to.y() == Board.size;
        }
    }

    private static void executeTurn(Turn turn) {



        int enemyX = (turn.from.x() + turn.to.x()) / 2;
        int enemyY = (turn.from.y() + turn.to.y()) / 2;
        Board.removePiece(enemyX, enemyY);
        Board.movePiece(turn);
    }

    // TODO: Add to UML!
    private static Turn setTurnStatus(Turn turn, Status status) {
        turn.status = status;
        return turn;
    }
}