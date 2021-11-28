package ch.uzh.softcon.one.abstraction;

import ch.uzh.softcon.one.commands.Command;
import ch.uzh.softcon.one.commands.state_control.CommandLoadBoard;
import ch.uzh.softcon.one.turn.Turn;

public class Board {

    private static Board instance;

    private Piece[][] board;
    private int pieceCountRed;
    private int pieceCountWhite;

    private Board() {
        cleanBoard();
    }

    public static synchronized Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public void initialize() {
        Command loadBoard = new CommandLoadBoard("initialBoard.csv");
        if (!loadBoard.execute()) {
            System.err.println("Could not load initial Board. Creating an original Board.");
            createOriginalBoard();
        }
    }

    public void placePiece(int posX, int posY, Piece piece) {
        if (getPiece(posX, posY) == null) {
            if (piece.getColor() == Player.RED) pieceCountRed++;
            else pieceCountWhite++;
            board[posX][posY] = piece;
        } else {
            System.err.println("There is already a piece on this tile!");
        }
    }

    public void movePiece(Turn turn) {
        Piece pieceToMove = getPiece(turn.from().x(), turn.from().y());
        board[turn.to().x()][turn.to().y()] = pieceToMove;
        board[turn.from().x()][turn.from().y()] = null;
    }

    public void removePiece(int posX, int posY) {
        if (getPiece(posX, posY) != null) {
            if (getPiece(posX, posY).getColor() == Player.RED) pieceCountRed--;
            else pieceCountWhite--;
            board[posX][posY] = null;
        }
    }

    public Piece getPiece(int posX, int posY) {
        return board[posX][posY];
    }

    public boolean hasNoPieces(Player player) {
        if (player == Player.RED) return pieceCountRed == 0;
        else return pieceCountWhite == 0;
    }

    public void cleanBoard() {
        board = new Piece[8][8];
        pieceCountRed = 0;
        pieceCountWhite = 0;
    }

    public int size() {
        return board.length;
    }

    private void createOriginalBoard() { // In case initialBoard file could not be read

        cleanBoard();
        int[][] initialPosRed   = {{1, 3, 5, 7},
                                   {0, 2, 4, 6},
                                   {1, 3, 5, 7}};

        int[][] initialPosWhite = {{0, 2, 4, 6},
                                   {1, 3, 5, 7},
                                   {0, 2, 4, 6}};

        for (int i = 0; i < initialPosRed.length; ++i) {
            for (int j = 0; j < initialPosRed[0].length; ++j) {
                placePiece(initialPosRed[i][j], i, new Piece((Player.RED)));
                placePiece(initialPosWhite[i][j], board.length-1-i, new Piece((Player.WHITE)));
            }
        }
    }
}