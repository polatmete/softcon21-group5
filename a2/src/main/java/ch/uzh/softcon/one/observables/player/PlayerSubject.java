package ch.uzh.softcon.one.observables.player;

import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.observables.Observer;

public interface PlayerSubject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
    Player activePlayer();
    void changePlayer(Player p);
}
