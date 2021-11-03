package ch.uzh.softcon.one;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import ch.uzh.softcon.one.Turn.Status;

public class Game extends Application {
    private static Player activePlayer;
    private static boolean winStatus;
    private static boolean rematch;
    private static boolean multiJump;

    public static void main(String[] args) { // Setup and initialize game
        winStatus = false;
        rematch = false;
        multiJump = false;
        activePlayer = Player.RED;
        Board.initialize();
        String out = IOFormatter.formatOutput("Welcome to the Checkers Game. Player red may begin.",
                true,"Please enter your move: ");
        System.out.print(out);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        int width = 750;
        int height = 750;


        Group root = new Group();
        Group board = new Group();
        Group pieces = new Group();
        Group texts = new Group();
        Group rematch = new Group();
        root.getChildren().add(board);
        root.getChildren().add(pieces);
        root.getChildren().add(texts);
        root.getChildren().add(rematch);


        Scene scene = new Scene(root);

        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Checkers Game");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        UI ui = new UI(pieces, board, texts, rematch, stage);
        UI.updateStatusMessage("Welcome to the Checkers Game. Player red may begin. Please enter your move");
        UI.drawBoard();
        UI.updatePieces();
    }

    public static void gameLoop(Turn turn) {
        if (!winStatus) {
            String player = "";
            if (activePlayer == Player.RED) player = "Player red";
            else if (activePlayer == Player.WHITE) player = "Player white";


            if (multiJump) {
                turn.status = Status.JUMP_AGAIN;
            }
            Status status = TurnHandler.runTurnSequence(turn);

            if (!winStatus) {
                String out = "";
                if (status == Status.ILLEGAL_TURN) {
                    out = IOFormatter.formatOutput(player + ": Invalid move - please try again.",
                            true, "Please enter a valid move: ");
                    UI.updateStatusMessage(player + ": Invalid move - please try again.");
                } else if (status == Status.JUMP_REQUIRED) {
                    out = IOFormatter.formatOutput(player + ": Invalid move - a jump is required.",
                            true, "Please enter a valid move: ");
                    UI.updateStatusMessage(player + ": Invalid move - a jump is required.");
                } else if (status == Status.JUMP_AGAIN) {
                    multiJump = true;
                    out = IOFormatter.formatOutput(player + ": Another jump is required.",
                            true, "Please enter your next move: ");
                    UI.updateStatusMessage(player + ": Another jump is required.");
                } else if (status == Status.COMPLETED) {
                    multiJump = false;
                    if (activePlayer == Player.RED) {
                        activePlayer = Player.WHITE;
                        player = "Player white";
                    } else if (activePlayer == Player.WHITE) {
                        activePlayer = Player.RED;
                        player = "Player red";
                    }
                    out = IOFormatter.formatOutput(player + ": It's your turn",
                            true, "Please enter your move: ");
                    UI.updateStatusMessage(player + ": It's your turn. Please enter your move.");
                }
                System.out.print(out);
            }
        }

        else {
            winStatus = false;
            rematch = false;
            multiJump = false;
            activePlayer = Player.RED;
            Board.initialize();
        }
    }

    public static Player getActivePlayer() {
        return activePlayer;
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
}