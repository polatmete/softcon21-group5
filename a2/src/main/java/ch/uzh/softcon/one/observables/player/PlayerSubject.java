package ch.uzh.softcon.one.observables.player;

import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.observables.Observer;

public interface PlayerSubject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers(Player p);
    public Player activePlayer();
}
