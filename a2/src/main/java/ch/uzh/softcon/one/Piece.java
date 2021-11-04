package ch.uzh.softcon.one;

public class Piece {

    private final Player color;
    private boolean isKing;
    private boolean isMultiJumping;

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
