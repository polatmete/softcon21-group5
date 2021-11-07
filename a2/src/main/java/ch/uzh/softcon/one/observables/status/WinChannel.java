package ch.uzh.softcon.one.observables.status;

import ch.uzh.softcon.one.abstraction.GameHandling;
import ch.uzh.softcon.one.observables.Observer;

public class WinChannel implements Observer {

    private void processWin(String msg) {
        GameHandling.updateStatusMessage(msg);
        GameHandling.createRematchInterface();
    }

    @Override
    public void update(Object obj) {
        if (obj instanceof StatusChangeSubscriber) {
            StatusChangeSubscriber scn = (StatusChangeSubscriber) obj;
            processWin(scn.getStatusMessage());
        }
    }
}
