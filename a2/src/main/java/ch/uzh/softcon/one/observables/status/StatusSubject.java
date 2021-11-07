package ch.uzh.softcon.one.observables.status;

import ch.uzh.softcon.one.observables.Observer;

public interface StatusSubject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();

    void setStatusMessage(String msg);

    String getStatusMessage();
}
