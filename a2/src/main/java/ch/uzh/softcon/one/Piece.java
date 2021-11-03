package ch.uzh.softcon.one;

public class Piece {

    //TODO: private but no getters & setters?
    private Player color;
    private boolean isKing = false;
    private boolean isMultiJumping = false;

    public Piece(Player color) {
        this.color = color;
    }

    public Player getColor() {
        return color;
    }

    //TODO: simply new name?
    public boolean isKing() {
        return isKing;
    }

    public void promote() {
        isKing = true;
    }

    //TODO simply new name?
    public boolean isMultiJumping() {
        return isMultiJumping;
    }

    //TODO: remove? -> isInMultiJump ???
    public void setMultiJumping(boolean multiJumping) {
        isMultiJumping = multiJumping;
    }
}
