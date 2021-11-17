package ch.uzh.softcon.one.observables.status;

import ch.uzh.softcon.one.observables.Observer;

import java.util.ArrayList;
import java.util.List;

public class StatusChangeSubscriber implements StatusSubject {
    private final List<Observer> observers = new ArrayList<>();
    private String statusMessage;
    private boolean isWin;

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update(this);
        }
    }

    @Override
    public void setStatusMessage(String msg) {
        this.statusMessage = msg;
    }

    @Override
    public String getStatusMessage() {
        return this.statusMessage;
    }

    @Override
    public boolean getWin() {
        return this.isWin;
    }

    @Override
    public void setWin() {
        this.isWin = true;
    }
}
