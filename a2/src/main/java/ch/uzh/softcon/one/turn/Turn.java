package ch.uzh.softcon.one.turn;

/**
 * The Turn class.
 * Each player move is wrapped into a Turn for further processing.
 */
public class Turn {

    private final TilePosition from;
    private final TilePosition to;

    /**
     * Class constructor for the turn class.
     * A single turn consisting of two tile positions.
     * @param x_from FROM x axis
     * @param y_from FROM y axis
     * @param x_to TO x axis
     * @param y_to TO y axis
     */
    public Turn(int x_from, int y_from, int x_to, int y_to) {
        from = new TilePosition(x_from, y_from);
        to = new TilePosition(x_to, y_to);
    }

    /**
     * TilePosition class.
     * Represents a single board tile position.
     * @param x
     * @param y
     */
    public record TilePosition(int x, int y) {
    }

    /**
     * Returns the from tile position.
     * @return from
     */
    public TilePosition from() {
        return from;
    }

    /**
     * Returns the to tile position
     * @return to
     */
    public TilePosition to() {
        return to;
    }
}