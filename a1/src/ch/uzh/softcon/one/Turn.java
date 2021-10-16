package ch.uzh.softcon.one;

/**
 * The Turn class.
 * Each player move is wrapped into a Turn for further processing.
 */
public class Turn {

    protected TilePosition from;
    protected TilePosition to;
    public Player activePlayer;
    public Status status;

    /**
     * Turn Status
     */
    public enum Status {
        /**
         * Initial Processing needed
         */
        PENDING,
        /**
         * Turn is processed and valid
         */
        COMPLETED,
        /**
         * Move is invalid because there is at least one jump possible
         */
        JUMP_REQUIRED,
        /**
         * Turn was executed but another jump must be taken
         */
        JUMP_AGAIN,
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
     * Class constructor for the turn class.
     * A single turn consisting of two tile positions.
     * @param pos x_from, y_from, x_to, y_to
     * @param p Active player
     */
    public Turn(int[] pos, Player p, Status s) {
        from = new TilePosition(pos[0], pos[1]);
        to = new TilePosition(pos[2], pos[3]);
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
}
