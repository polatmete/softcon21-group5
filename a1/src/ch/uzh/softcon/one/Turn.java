package ch.uzh.softcon.one;

/**
 * The Turn class.
 * Each player move is wrapped into a Turn for further processing.
 */
public class Turn {

    protected TilePosition from;
    protected TilePosition to;
    public Player player;

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
        player = p;
    }

    /**
     * Class constructor for the turn class.
     * A single turn consisting of two tile positions.
     * @param pos x_from, y_from, x_to, y_to
     * @param p Active player
     */
    public Turn(int[] pos, Player p) {
        from = new TilePosition(pos[0], pos[1]);
        to = new TilePosition(pos[2], pos[3]);
        player = p;
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
