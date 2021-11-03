package ch.uzh.softcon.one;

public class Piece {

    private final Player color;
    private boolean isKing = false;
    private boolean isMultiJumping = false;

    public Piece(Player color) {
        this.color = color;
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

    public boolean inMultiJump() {
        return isMultiJumping;
    }

    public void startMultiJump() {
        isMultiJumping = true;
    }

    public void endMultiJump() {
        isMultiJumping = false;
    }
}
