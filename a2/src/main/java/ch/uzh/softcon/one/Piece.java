package ch.uzh.softcon.one;

public class Piece {

    private final Player color;
    private boolean isKing;
    private boolean isMultiJumping;
    private static boolean globalMultiJump = false;

    public Piece(Player color) {
        this.color = color;
        isKing = false;
        isMultiJumping = false;
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
        } else {
            System.out.println("Fatal error occurred: " +
                    "tried to start multiJumping with a piece but another one is already in a multiJump!");
        }
    }

    public void endMultiJump() {
        if (globalMultiJump) {
            isMultiJumping = false;
            globalMultiJump = false;
        }
    }
}
