package ch.uzh.softcon.one;

import ch.uzh.softcon.one.Turn.Status;

public class TurnHandler {

    public static Status runTurnSequence(Turn turn) {

        Status validationResult = TurnValidator.validateMove(turn, isJumpRequired(turn));
        if (validationResult == Status.ILLEGAL_TURN) {
            return Status.ILLEGAL_TURN;
        }

        if (validationResult == Status.JUMP_REQUIRED) {
            return Status.JUMP_REQUIRED;
        }

        executeTurn(turn);

        if (checkTransformNeeded(turn)) {
            Board.getPiece(turn.to.x(), turn.to.y()).setKing();
        }

        if (isJumpRequired(turn)) {
            return Status.JUMP_AGAIN;
        }

        if (checkWin(turn)) {
            Game.win(turn.activePlayer);
        }

        return Status.COMPLETED;
    }

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

    // check if the player is forced to move somewhere and cannot just move anywhere
    // -> Turn object only for player needed
    private static boolean isJumpRequired(Turn turn) {
        for (int i = 1; i <= Board.size; i++) {
            for (int j = 1; j <= Board.size; j++) {
                if (Board.getPiece(i, j).color == turn.activePlayer) {
                    if (TurnValidator.isJumpPossible(i, j, turn.activePlayer)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkTransformNeeded(Turn turn) {
        if (turn.activePlayer == Player.RED) {
            return turn.to.y() == 1;
        } else {
            return turn.to.y() == Board.size;
        }
    }

    private static void executeTurn(Turn turn) {
        if (turn.status == Status.JUMP_REQUIRED) {
            int enemyX = (turn.from.x() + turn.to.x()) / 2;
            int enemyY = (turn.from.y() + turn.to.y()) / 2;
            Board.removePiece(enemyX, enemyY);
        }

        Board.movePiece(turn);
    }


































































    // TODO: rename on UML from checkMovePossible
    /**
     * it is not stuck if:
     *  - it can move in any of its two or four diagonal directions
     *  - it can capture any enemy pieces on the two or four diagonally adjacent tiles
     */
    private static boolean isEnemyStuck(Player p) {
        Player enemy = p == Player.RED ? Player.WHITE : Player.RED;

        for (int i = 1; i <= Board.size; i++) {
            for (int j = 1; j <= Board.size; j++) {
                if (Board.getPiece(i, j).color == p) {
                    if (!TurnValidator.isOutsideBoard(i - 1, j + 1) && Board.getPiece(i - 1, j + 1) == null) {
                        return false;
                    } else if (!TurnValidator.isOutsideBoard(i - 2, j + 2) && Board.getPiece(i - 2, j + 2) == null) {
                        return false;
                    }
                    if (!TurnValidator.isOutsideBoard(i + 1, j + 1) && Board.getPiece(i + 1, j + 1) == null) {
                        return false;
                    } else if (!TurnValidator.isOutsideBoard(i + 2, j + 2) && Board.getPiece(i + 2, j + 2) == null) {
                        return false;
                    }
                    if (!TurnValidator.isOutsideBoard(i - 1, j - 1) && Board.getPiece(i - 1, j - 1) == null) {
                        return false;
                    } else if (!TurnValidator.isOutsideBoard(i - 2, j - 2) && Board.getPiece(i - 2, j - 2) == null) {
                        return false;
                    }
                    if (!TurnValidator.isOutsideBoard(i + 1, j - 1) && Board.getPiece(i + 1, j - 1) == null) {
                        return false;
                    } else if (!TurnValidator.isOutsideBoard(i + 2, j - 2) && Board.getPiece(i + 2, j - 2) == null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}