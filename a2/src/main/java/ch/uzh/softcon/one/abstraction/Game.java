package ch.uzh.softcon.one.abstraction;

import ch.uzh.softcon.one.observables.Observer;
import ch.uzh.softcon.one.observables.player.PlayerChangeNotifier;
import ch.uzh.softcon.one.observables.player.PlayerChannel;
import ch.uzh.softcon.one.observables.player.PlayerSubject;
import ch.uzh.softcon.one.turn.Turn;
import ch.uzh.softcon.one.turn.Turn.Status;
import ch.uzh.softcon.one.turn.TurnHandler;
import ch.uzh.softcon.one.utils.UI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application {
    private static boolean winStatus;
    private static PlayerSubject playerSubject;

    public static void main(String[] args) { // Setup and initialize game
        winStatus = false;
        Board.initialize();
        launch(args);
    }

    private static void registerObservers() {
        // Active Player
        playerSubject = new PlayerChangeNotifier();
        Observer activePlayerObserver = new PlayerChannel();

        playerSubject.registerObserver(activePlayerObserver);

        // ...
    }

    @Override
    public void start(Stage stage) {
        UI.initialize(stage);
        registerObservers();
        playerSubject.notifyObservers(Player.RED);
    }

    public static Player activePlayer() {
        return playerSubject.activePlayer();
    }

    public static void changePlayer(Player p) {
        playerSubject.notifyObservers(p);
    }

    public static void gameLoop(Turn turn) {
        if (!winStatus) {

            // Notify status change
            Status status = TurnHandler.runTurnSequence(turn);

            if (status == Status.JUMP_REQUIRED) UI.updateStatusMessage(activePlayer() + ": A jump is required.");
            else if (status == Status.ANOTHER_JUMP_REQUIRED)
                UI.updateStatusMessage(activePlayer() + ": Another jump is required.");
            else if (status == Status.NO_PIECE)
                UI.updateStatusMessage(activePlayer() + ": Targeted piece does not exist.");
            else if (status == Status.ENEMY_PIECE)
                UI.updateStatusMessage(activePlayer() + ": Targeted piece is an enemy piece.");
            else if (status == Status.OUTSIDE_BOARD)
                UI.updateStatusMessage(activePlayer() + ": Turn would result outside of the board.");
            else if (status == Status.PIECE_AT_DESTINATION)
                UI.updateStatusMessage(activePlayer() + ": A piece blocks the desired destination.");
            else if (status == Status.ILLEGAL_BACKWARDS)
                UI.updateStatusMessage(activePlayer() + ": Non-King piece cannot move backwards.");
            else if (status == Status.NOT_MULTI_JUMP_PIECE)
                UI.updateStatusMessage(activePlayer() + ": Another piece is in a multi-jump already.");
            else if (status == Status.ILLEGAL_TURN) UI.updateStatusMessage(activePlayer() + ": Desired move is not possible.");
        } else {
            reset();
        }
    }

    public static void win(Player player) {
        winStatus = true;

        String playerString = "";
        if (player == Player.RED) playerString = "Player red";
        else if (player == Player.WHITE) playerString = "Player white";

        UI.updateStatusMessage("Congratulations " + playerString + ", you won! Do you want a revenge?");
        UI.createRematchInterface();
    }

    public static void reset() {
        winStatus = false;
        playerSubject.notifyObservers(Player.RED);
        Board.initialize();
        UI.updateStatusMessage("Welcome to the Checkers Game. Player red may begin. Please enter your move");
    }
}