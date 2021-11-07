package ch.uzh.softcon.one.observables.player;

import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.observables.Observer;
import ch.uzh.softcon.one.abstraction.GameHandling;

public class PlayerWinChannel implements Observer {

    @Override
    public void update(Object p) {
        String pStr = "";
        if (p == Player.RED) pStr = "Player red";
        else if (p == Player.WHITE) pStr = "Player white";
        GameHandling.updateStatusMessage(pStr + ": It's your turn. Please enter your move.");
    }
}
