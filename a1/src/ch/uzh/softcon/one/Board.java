package ch.uzh.softcon.one;

public class Board {
    public static Piece[][] positions;
    public static int pieceCountRed;
    public static int pieceCountWhite;

    public void initialize() {

        positions = new Piece[8][8];

        int[][][] initialPosRed = {
                {{0}, {1, 3, 5, 7}},
                {{1}, {0, 2, 4, 6}},
                {{2}, {1, 3, 5, 7}}
        };

        int[][][] initialPosWhite = {
                {{5}, {0, 2, 4, 6}},
                {{6}, {1, 3, 5, 7}},
                {{7}, {0, 2, 4, 6}},
        };

        //Put red pieces on the board

        for (int i = 0; i < initialPosRed.length; i++) {
            Piece newPiece = new Piece();
            newPiece.color = Player.RED;

            int row = initialPosRed[i][0][0];
            int[] columns = initialPosRed[i][1];

            for (int column : columns) {
                positions[row][column] = newPiece;
            }
        }

        //Put white pieces on the board

        for (int i = 0; i < initialPosWhite.length; i++) {
            Piece newPiece = new Piece();
            newPiece.color = Player.WHITE;

            int row = initialPosWhite[i][0][0];
            int[] columns = initialPosWhite[i][1];

            for (int column : columns) {
                positions[row][column] = newPiece;
            }
        }
    }

    public static void movePiece(Turn turn) {
        positions[turn.to.x()][turn.to.y()] = positions[turn.from.x()][turn.from.y()];
        removePiece(turn.from.x(), turn.from.x());
    }

    public static void removePiece(int posX, int posY) {
        positions[posX][posY] = null;
    }

    public static int size() {
        return positions.length;
    }

    public static Piece getPiece(int posX, int posY) {
        return positions[posX][posY];
    }
}