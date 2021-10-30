package ch.uzh.softcon.one;

public class Piece {

    //TODO: private but no getters & setters?
    public Player color;
    private boolean isKing = false;
    private boolean isMultiJumping = false;

    //TODO: simply new name?
    public boolean isKing() {
        return isKing;
    }

    //TODO: promote() -> piece.promote();
    public void setKing() {
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
