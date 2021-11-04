package ch.uzh.softcon.one;

public class Board {

    private static Piece[][] board;
    private static int pieceCountRed;
    private static int pieceCountWhite;

    public static void initialize() {

        board = new Piece[8][8];

        int[][][] initialPosRed = {
                {{0}, {1, 3, 5, 7}},
                {{1}, {0, 2, 4, 6}},
                {{2}, {1, 3, 5, 7}}
        };

        int[][][] initialPosWhite = {
                {{5}, {0, 2, 4, 6}},
                {{6}, {1, 3, 5, 7}},
                {{7}, {0, 2, 4, 6}}
        };

        //Put red pieces on the board
        for (int i = 0; i < initialPosRed.length; i++) {
            int row = initialPosRed[i][0][0];
            int[] columns = initialPosRed[i][1];

            for (int column : columns) {
                placePiece(column, row, new Piece(Player.RED));
            }
        }

        //Put white pieces on the board
        for (int i = 0; i < initialPosWhite.length; i++) {
            int row = initialPosWhite[i][0][0];
            int[] columns = initialPosWhite[i][1];

            for (int column : columns) {
                placePiece(column, row, new Piece(Player.WHITE));
            }
        }
    }

    public static void placePiece(int posX, int posY, Piece piece) {
        if (getPiece(posX, posY) == null) {
            if (piece.getColor() == Player.RED) {
                pieceCountRed++;
            } else {
                pieceCountWhite++;
            }
            board[posX][posY] = piece;
        }
    }

    public static void movePiece(Turn turn) {
        Piece pieceToMove = getPiece(turn.from.x(), turn.from.y());
        board[turn.to.x()][turn.to.y()] = pieceToMove;
        board[turn.from.x()][turn.from.y()] = null;
    }

    public static void removePiece(int posX, int posY) {
        if (getPiece(posX, posY) != null) {
            if (getPiece(posX, posY).getColor() == Player.RED) {
                pieceCountRed--;
            } else {
                pieceCountWhite--;
            }
            board[posX][posY] = null;
        }
    }

    public static Piece getPiece(int posX, int posY) {
        return board[posX][posY];
    }

    //checks if a player has no more pieces left
    public static boolean hasNoPieces(Player player) {
        if (player == Player.RED) {
            return pieceCountRed == 0;
        } else {
            return pieceCountWhite == 0;
        }
    }

    public static void cleanBoard() {
        board = new Piece[8][8];
    }

    public static int size() {
        return board.length;
    }
}