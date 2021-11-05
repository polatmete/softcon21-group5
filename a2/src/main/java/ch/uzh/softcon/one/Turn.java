package ch.uzh.softcon.one;

/**
 * The Turn class.
 * Each player move is wrapped into a Turn for further processing.
 */
public class Turn {

    protected TilePosition from;
    protected TilePosition to;
    private final Player activePlayer;

    /**
     * Turn Status
     */
    public enum Status {
        /**
         * Turn is being processed
         */
        PENDING,
        /**
         * Turn was finished
         */
        COMPLETED,
        /**
         * At least one jump is possible and must be made
         */
        JUMP_REQUIRED,
        /**
         * Another jump is possible and must be made
         */
        ANOTHER_JUMP_REQUIRED,
        /**
         * Targeted piece does not exist
         */
        NO_PIECE,
        /**
         * Targeted piece is an enemy piece
         */
        ENEMY_PIECE,
        /**
         * Turn would result outside the board
         */
        OUTSIDE_BOARD,
        /**
         * Piece at destination
         */
        PIECE_AT_DESTINATION,
        /**
         * Attempt to move backwards while not being a king
         */
        ILLEGAL_BACKWARDS,
        /**
         * Trying to jump with a piece that is not in the active multi jump
         */
        NOT_MULTI_JUMP_PIECE,
        /**
         * Turn is not possible otherwise
         */
        ILLEGAL_TURN,
    }

    /**
     * Class constructor for the turn class.
     * A single turn consisting of two tile positions.
     * @param x_from FROM x axis
     * @param y_from FROM y axis
     * @param x_to TO x axis
     * @param y_to TO y axis
     * @param p Active player
     */
    public Turn(int x_from, int y_from, int x_to, int y_to, Player p) {
        from = new TilePosition(x_from, y_from);
        to = new TilePosition(x_to, y_to);
        activePlayer = p;
    }

    /**
     * TilePosition class.
     * Represents a single board tile position.
     * @param x
     * @param y
     */
    protected record TilePosition(int x, int y) {
    }

    /**
     * Returns the active player
     * @return active player
     */
    public Player getActivePlayer() {
        return activePlayer;
    }
}