package ch.uzh.softcon.four.scoreboard;

public class ScoreBoardEntry {

    private int money;
    private final String name;

    public ScoreBoardEntry(String name, int money) {
        this.money = money;
        this.name = name;
    }

    public int balance() {
        return this.money;
    }

    public String getName() {
        return this.name;
    }

}
