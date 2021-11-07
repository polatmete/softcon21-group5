package ch.uzh.softcon.one.utils;

import ch.uzh.softcon.one.abstraction.Board;
import ch.uzh.softcon.one.abstraction.GameHandling;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) { // Setup and initialize game
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GameHandling.initialize(stage);
    }

//    public static void gameLoop(Turn turn) {
//        if (!winStatus) {
//
//            // Notify status change
//            Status status = TurnHandler.runTurnSequence(turn);
//
//            if (status == Status.JUMP_REQUIRED) GameHandling.updateStatusMessage(activePlayer() + ": A jump is required.");
//            else if (status == Status.ANOTHER_JUMP_REQUIRED)
//                GameHandling.updateStatusMessage(activePlayer() + ": Another jump is required.");
//            else if (status == Status.NO_PIECE)
//                GameHandling.updateStatusMessage(activePlayer() + ": Targeted piece does not exist.");
//            else if (status == Status.ENEMY_PIECE)
//                GameHandling.updateStatusMessage(activePlayer() + ": Targeted piece is an enemy piece.");
//            else if (status == Status.OUTSIDE_BOARD)
//                GameHandling.updateStatusMessage(activePlayer() + ": Turn would result outside of the board.");
//            else if (status == Status.PIECE_AT_DESTINATION)
//                GameHandling.updateStatusMessage(activePlayer() + ": A piece blocks the desired destination.");
//            else if (status == Status.ILLEGAL_BACKWARDS)
//                GameHandling.updateStatusMessage(activePlayer() + ": Non-King piece cannot move backwards.");
//            else if (status == Status.NOT_MULTI_JUMP_PIECE)
//                GameHandling.updateStatusMessage(activePlayer() + ": Another piece is in a multi-jump already.");
//            else if (status == Status.ILLEGAL_TURN) GameHandling.updateStatusMessage(activePlayer() + ": Desired move is not possible.");
//        } else {
//            reset();
//        }
//    }

//    public static void win(Player player) {
//        winStatus = true;
//
//        String playerString = "";
//        if (player == Player.RED) playerString = "Player red";
//        else if (player == Player.WHITE) playerString = "Player white";
//
//        GameHandling.updateStatusMessage("Congratulations " + playerString + ", you won! Do you want a revenge?");
//        GameHandling.createRematchInterface();
//    }

//    public static void reset() {
//        winStatus = false;
//        playerSubject.notifyObservers(Player.RED);
//        Board.initialize();
//        GameHandling.updateStatusMessage("Welcome to the Checkers Game. Player red may begin. Please enter your move");
//    }
}