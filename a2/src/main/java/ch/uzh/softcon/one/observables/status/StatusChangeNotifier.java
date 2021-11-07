package ch.uzh.softcon.one.observables.status;

import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.observables.Observer;

import java.util.ArrayList;
import java.util.List;

public class StatusChangeNotifier implements StatusSubject {
    private Player currentStatus;
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers(Player p) {
        this.currentStatus = p;
        for (Observer observer : this.observers) {
            observer.update(this.currentStatus);
        }
    }

    @Override
    public Player currentStatus() {
        return this.currentStatus;
    }
}
