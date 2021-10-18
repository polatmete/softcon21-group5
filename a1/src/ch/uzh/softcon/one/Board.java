package ch.uzh.softcon.one;

public class Board {
    public static Piece[][] positions;
    public static int pieceCountRed;
    public static int pieceCountWhite;

    public static void initialize() {

        positions = new Piece[8][8];

        /*int[][][] initialPosRed = {
                {{0}, {1, 3, 5, 7}},
                {{1}, {0, 2, 4, 6}},
                {{2}, {1, 3, 5, 7}}
        };

        int[][][] initialPosWhite = {
                {{5}, {0, 2, 4, 6}},
                {{6}, {1, 3, 5, 7}},
                {{7}, {0, 2, 4, 6}},
        };*/

        int[][][] initialPosRed = {
                //{{0}, {1, 3, 5, 7}},
                {{1}, {0, 6}},
                //{{2}, {1, 3, 5, 7}}
        };

        int[][][] initialPosWhite = {
                {{2}, {1, 5}},
                {{4}, {5}},
                //{{7}, {0, 2, 4, 6}},
        };

        //Put red pieces on the board

        for (int i = 0; i < initialPosRed.length; i++) {
            int row = initialPosRed[i][0][0];
            int[] columns = initialPosRed[i][1];

            for (int column : columns) {
                // Make sure we instantiate a new object for every piece such that we have no shared references.
                Piece newPiece = new Piece();
                newPiece.color = Player.RED;
                positions[column][row] = newPiece;
                pieceCountRed++;
            }
        }

        //Put white pieces on the board

        for (int i = 0; i < initialPosWhite.length; i++) {
            int row = initialPosWhite[i][0][0];
            int[] columns = initialPosWhite[i][1];

            for (int column : columns) {
                // Same here
                Piece newPiece = new Piece();
                newPiece.color = Player.WHITE;
                positions[column][row] = newPiece;
                pieceCountWhite++;
            }
        }
    }

    public static void movePiece(Turn turn) {
        Piece pieceToMove = getPiece(turn.from.x(), turn.from.y());
        positions[turn.to.x()][turn.to.y()] = pieceToMove;
        positions[turn.from.x()][turn.from.y()] = null;
    }

    public static void removePiece(int posX, int posY) {
        if (getPiece(posX, posY) != null &&
                getPiece(posX, posY).color == Player.RED) {
            pieceCountRed--;
        } else {
            pieceCountWhite--;
        }
        positions[posX][posY] = null;
    }

    public static int size() {
        return positions.length;
    }

    public static Piece getPiece(int posX, int posY) {
        return positions[posX][posY];
    }
}