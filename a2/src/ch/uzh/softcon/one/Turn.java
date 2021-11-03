package ch.uzh.softcon.one;

/**
 * The Turn class.
 * Each player move is wrapped into a Turn for further processing.
 */
public class Turn {

    protected TilePosition from;
    protected TilePosition to;
    private Player activePlayer;
    private Status status;

    /**
     * Turn Status
     */
    public enum Status {
        /**
         * Initial Processing needed
         */
        PENDING,
        /**
         * Turn was processed
         */
        COMPLETED,
        /**
         * Move is invalid because there is at least one jump possible
         */
        JUMP_REQUIRED,
        /**
         * Turn was executed but another jump must be taken
         */
        ANOTHER_JUMP_REQUIRED,

        NO_PIECE,
        ENEMY_PIECE,
        OUTSIDE_BOARD,
        PIECE_AT_DESTINATION,
        ILLEGAL_BACKWARDS,
        NOT_MULTI_JUMP_PIECE,

        /**
         * Turn is not possible otherwise
         */
        ILLEGAL_TURN,
    }

    /**
     * Class constructor for the turn class.
     * A single turn consisting of two tile positions.
     * Status defaults to Status.PENDING.
     * @param x_from FROM x axis
     * @param y_from FROM y axis
     * @param x_to TO x axis
     * @param y_to TO y axis
     * @param p Active player
     */
    public Turn(int x_from, int y_from, int x_to, int y_to, Player p) {
        this(x_from, y_from, x_to, y_to, p, Status.PENDING);
    }

    /**
     * Class constructor for the turn class.
     * A single turn consisting of two tile positions.
     * @param x_from FROM x axis
     * @param y_from FROM y axis
     * @param x_to TO x axis
     * @param y_to TO y axis
     * @param p Active player
     * @param s Status
     */
    public Turn(int x_from, int y_from, int x_to, int y_to, Player p, Status s) {
        from = new TilePosition(x_from, y_from);
        to = new TilePosition(x_to, y_to);
        activePlayer = p;
        status = s;
    }

    /**
     * TilePosition class.
     * Represents a single board tile position.
     * @param x
     * @param y
     */
    protected record TilePosition(int x, int y) {
    }

    public Status getStatus() {
        return status;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void anotherJump() {
        status = Status.ANOTHER_JUMP_REQUIRED;
    }
}
