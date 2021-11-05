package ch.uzh.softcon.one;

import ch.uzh.softcon.one.Turn.Status;
import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application {
    private static Player activePlayer;
    private static boolean winStatus;

    public static void main(String[] args) { // Setup and initialize game
        winStatus = false;
        Board.initialize();
        activatePlayerRed();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        UI.initialize(stage);
    }

    public static void gameLoop(Turn turn) {
        if (!winStatus) {
            String player = "";
            if (activePlayer == Player.RED) player = "Player red";
            else if (activePlayer == Player.WHITE) player = "Player white";

            Status status = TurnHandler.runTurnSequence(turn);

            if (status == Status.JUMP_REQUIRED) UI.updateStatusMessage(player + ": A jump is required.");
            else if (status == Status.ANOTHER_JUMP_REQUIRED)
                UI.updateStatusMessage(player + ": Another jump is required.");
            else if (status == Status.NO_PIECE)
                UI.updateStatusMessage(player + ": Targeted piece does not exist.");
            else if (status == Status.ENEMY_PIECE)
                UI.updateStatusMessage(player + ": Targeted piece is an enemy piece.");
            else if (status == Status.OUTSIDE_BOARD)
                UI.updateStatusMessage(player + ": Turn would result outside of the board.");
            else if (status == Status.PIECE_AT_DESTINATION)
                UI.updateStatusMessage(player + ": A piece blocks the desired destination.");
            else if (status == Status.ILLEGAL_BACKWARDS)
                UI.updateStatusMessage(player + ": Non-King piece cannot move backwards.");
            else if (status == Status.NOT_MULTI_JUMP_PIECE)
                UI.updateStatusMessage(player + ": Another piece is in a multi-jump already.");
            else if (status == Status.ILLEGAL_TURN) UI.updateStatusMessage(player + ": Desired move is not possible.");
            else if (status == Status.COMPLETED) {
                if (activePlayer == Player.RED) {
                    activatePlayerWhite();
                    player = "Player white";
                } else if (activePlayer == Player.WHITE) {
                    activatePlayerRed();
                    player = "Player red";
                }
                UI.updateStatusMessage(player + ": It's your turn. Please enter your move.");
            }
        } else {
            reset();
        }
    }

    public static void win(Player player) {
        winStatus = true;

        String playerString = "";
        if (player == Player.RED) playerString = "Player red";
        else if (player == Player.WHITE) playerString = "Player white";

        String out = IOFormatter.formatOutput("Congratulations " + playerString + ", you won!",
                true,"Do you want a revenge? (y|n): ");
        UI.updateStatusMessage("Congratulations " + playerString + ", you won! Do you want a revenge?");
        System.out.print(out);

        UI.createRematchInterface();
    }

    public static Player getActivePlayer() {
        return activePlayer;
    }

    public static void activatePlayerRed() {
        activePlayer = Player.RED;
    }

    public static void activatePlayerWhite() {
        activePlayer = Player.WHITE;
    }

    public static void reset() {
        winStatus = false;
        activatePlayerRed();
        Board.initialize();
        UI.updateStatusMessage("Welcome to the Checkers Game. Player red may begin. Please enter your move");
    }
}