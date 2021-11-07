package ch.uzh.softcon.one.observables.status;

import ch.uzh.softcon.one.observables.Observer;

public interface StatusSubject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
    void setStatusMessage(String msg);
    String getStatusMessage();
    boolean getWin();
    void setWin();
}
