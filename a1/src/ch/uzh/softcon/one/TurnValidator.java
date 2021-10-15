package ch.uzh.softcon.one;

public class TurnValidator {

    public static boolean validateMove(Turn turn) {
        int fromX = turn.from.x(); int toX = turn.to.x();
        int fromY = turn.from.y(); int toY = turn.to.y();

        //Turn would result in a position outside the field
        if (toX >= 8 || toX <= 0 ||
                toY >= 8 || toY <= 0) {
            return false;
        }

        //Another piece is at the turn destination
        if (Board.getPiece(toX, toY) != null) {
            return false;
        }

        //Attempt to move backwards but piece is not a king
        Piece piece = Board.getPiece(fromX, fromY);
        if (! piece.isKing) {
            if (turn.activePlayer == Player.RED) {
                return toY < fromY;
            } else {
                return toY > fromY;
            }
        }

        //Turn destination is not one diagonal or two diagonal if an enemy sits on the first diagonal
        if ((toX != fromX - 1 && toX != fromX + 1) &&
                (toY != fromY -1 && toY != fromY + 1)) {
            //TODO
            System.out.println("");
        }



        return true;
    }
}
