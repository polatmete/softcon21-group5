package ch.uzh.softcon.one.observables.status;

import ch.uzh.softcon.one.abstraction.GameHandling;
import ch.uzh.softcon.one.observables.Observer;

public class WinChannel implements Observer {

    @Override
    public void update(Object obj) {
        if (obj instanceof StatusChangeSubscriber) {
            StatusChangeSubscriber scs = (StatusChangeSubscriber) obj;
            if (scs.getWin())
                GameHandling.createRematchInterface();
        }
    }
}
