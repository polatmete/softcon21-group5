package ch.uzh.softcon.one;

public class Piece {

    public Player color;
    private boolean isKing = false;
    private boolean isMultiJumping = false;

    public boolean isKing() {
        return isKing;
    }

    public void setKing() {
        isKing = true;
    }

    public boolean isMultiJumping() {
        return isMultiJumping;
    }

    public void setMultiJumping(boolean multiJumping) {
        isMultiJumping = multiJumping;
    }
}