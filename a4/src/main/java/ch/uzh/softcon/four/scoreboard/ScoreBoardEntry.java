package ch.uzh.softcon.four.scoreboard;

/**
 * An entry of the ScoreBoard
 */
public class ScoreBoardEntry {


    /**
     * The current balance of the ScoreBoardEntry.
     */
    private int money;

    /**
     * The name of the ScoreBoardEntry.
     */
    private final String name;

    public ScoreBoardEntry(String name, int money) {
        this.money = money;
        this.name = name;
    }

    /**
     * Provides the current balance.
     * @return the int with the current amount of money.
     */
    public int balance() {
        return this.money;
    }

    /**
     * Provides the name of the player.
     * @return a string with the name.
     */
    public String getName() {
        return this.name;
    }

}
