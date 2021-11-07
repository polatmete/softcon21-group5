package ch.uzh.softcon.one.observables.status;

import ch.uzh.softcon.one.observables.Observer;
import ch.uzh.softcon.one.abstraction.GameHandling;

public class StatusMessageChannel implements Observer {

    @Override
    public void update(Object obj) {
        if (obj instanceof StatusChangeSubscriber) {
            StatusChangeSubscriber scs = (StatusChangeSubscriber) obj;
            GameHandling.updateStatusMessage(scs.getStatusMessage());
        }
    }
}
