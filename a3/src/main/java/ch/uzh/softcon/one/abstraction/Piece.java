package ch.uzh.softcon.one.abstraction;

public class Piece {

    private final Player color;
    private boolean isKing;
    private boolean isMultiJumping;
    private static boolean globalMultiJump;

    public Piece(Player color) {
        this.color = color;
        isKing = false;
        isMultiJumping = false;
        globalMultiJump = false;
    }

    public Player getColor() {
        return color;
    }

    public boolean isKing() {
        return isKing;
    }

    public void promote() {
        isKing = true;
    }

    public boolean isInMultiJump() {
        return isMultiJumping;
    }

    public static boolean activeMultiJump() {
        return globalMultiJump;
    }

    public void startMultiJump() {
        if (!globalMultiJump) {
            isMultiJumping = true;
            globalMultiJump = true;
        }
    }

    public void endMultiJump() {
        if (globalMultiJump) {
            isMultiJumping = false;
            globalMultiJump = false;
        }
    }
}
