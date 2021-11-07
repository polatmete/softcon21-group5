package ch.uzh.softcon.one.abstraction;

import ch.uzh.softcon.one.turn.Turn;
import ch.uzh.softcon.one.utils.BoardLoader;

public class Board {

    private static Piece[][] board;
    private static int pieceCountRed;
    private static int pieceCountWhite;

    public static void initialize() {
        if (!BoardLoader.loadBoard("initialBoard.csv")) {
            System.err.println("Could not load initial Board. Creating an original Board.");
            createOriginalBoard();
        }
    }

    public static void placePiece(int posX, int posY, Piece piece) {
        if (getPiece(posX, posY) == null) {
            if (piece.getColor() == Player.RED) pieceCountRed++;
            else pieceCountWhite++;
            board[posX][posY] = piece;
        }
    }

    public static void movePiece(Turn turn) {
        Piece pieceToMove = getPiece(turn.from().x(), turn.from().y());
        board[turn.to().x()][turn.to().y()] = pieceToMove;
        board[turn.from().x()][turn.from().y()] = null;
    }

    public static void removePiece(int posX, int posY) {
        if (getPiece(posX, posY) != null) {
            if (getPiece(posX, posY).getColor() == Player.RED) pieceCountRed--;
            else pieceCountWhite--;
            board[posX][posY] = null;
        }
    }

    public static Piece getPiece(int posX, int posY) {
        return board[posX][posY];
    }

    public static boolean hasNoPieces(Player player) {
        if (player == Player.RED) return pieceCountRed == 0;
        else return pieceCountWhite == 0;
    }

    public static void cleanBoard() {
        board = new Piece[8][8];
    }

    public static int size() {
        return board.length;
    }

    private static void createOriginalBoard() { // In case initialBoard file could not be read
        cleanBoard();
        int[][] initialPosRed   = {{1, 3, 5, 7},
                                   {0, 2, 4, 6},
                                   {1, 3, 5, 7}};

        int[][] initialPosWhite = {{0, 2, 4, 6},
                                   {1, 3, 5, 7},
                                   {0, 2, 4, 6}};

        //Put red pieces on the board
        for (int i = 0; i < initialPosRed.length; ++i) {
            for (int j = 0; j < initialPosRed[0].length; ++j) {
                placePiece(initialPosRed[i][j], i, new Piece((Player.RED)));
                placePiece(initialPosWhite[i][j], board.length-1-i, new Piece((Player.WHITE)));
            }
        }
    }
}