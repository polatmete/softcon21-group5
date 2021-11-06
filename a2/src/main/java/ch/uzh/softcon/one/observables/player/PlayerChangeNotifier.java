package ch.uzh.softcon.one.observables.player;

import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.observables.Observer;

import java.util.ArrayList;
import java.util.List;

public class PlayerChangeNotifier implements PlayerSubject {
    private Player activePlayer;
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
        this.activePlayer = p;
        for (Observer observer : this.observers) {
            observer.update(this.activePlayer);
        }
    }

    @Override
    public Player activePlayer() {
        return this.activePlayer;
    }
}
