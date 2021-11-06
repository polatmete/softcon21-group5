package ch.uzh.softcon.one.observables.player;

import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.observables.Observer;
import ch.uzh.softcon.one.utils.UI;

public class PlayerChannel implements Observer {

    @Override
    public void update(Object p) {
        String pStr = "";
        if (p == Player.RED) pStr = "Player red";
        else if (p == Player.WHITE) pStr = "Player white";
        UI.updateStatusMessage(pStr + ": It's your turn. Please enter your move.");
    }
}
