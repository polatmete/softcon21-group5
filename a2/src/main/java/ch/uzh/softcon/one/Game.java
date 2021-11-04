package ch.uzh.softcon.one;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import ch.uzh.softcon.one.Turn.Status;

public class Game extends Application {
    //TODO De wieder privat?
    protected static Player activePlayer;
    private static boolean winStatus;
    private static boolean rematch; //TODO remove

    public static void main(String[] args) { // Setup and initialize game
        rematch = false; //TODO remove
        winStatus = false;

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
        boolean multiJump = false; //TODO remove
        if (!winStatus) {
            String player = "";
            if (activePlayer == Player.RED) player = "Player red";
            else if (activePlayer == Player.WHITE) player = "Player white";

            if (multiJump) { //TODO remove
                turn.anotherJump();
            }
            Status status = TurnHandler.runTurnSequence(turn);

            String out = "";
            if (status == Status.JUMP_REQUIRED) {
                out = IOFormatter.formatOutput(player + ": Desired jump is not possible.",
                        false, "Please enter a valid move: ");
                UI.updateStatusMessage(player + ": Desired jump is not possible.");
            }
            else if (status == Status.ANOTHER_JUMP_REQUIRED) {
                multiJump = true; //TODO remove
                out = IOFormatter.formatOutput(player + ": Another jump is required.",
                        true, "Please enter your next move: ");
                UI.updateStatusMessage(player + ": Another jump is required.");
            } else if (status == Status.NO_PIECE)
                out = IOFormatter.formatOutput(player + ": Targeted piece does not exist.",
                        false, "Please enter a valid move: ");
            else if (status == Status.ENEMY_PIECE)
                out = IOFormatter.formatOutput(player + ": Targeted piece is an enemy piece.",
                        false, "Please enter a valid move: ");
            else if (status == Status.OUTSIDE_BOARD)
                out = IOFormatter.formatOutput(player + ": Turn would result outside of the board.",
                        false, "Please enter a valid move: ");
            else if (status == Status.PIECE_AT_DESTINATION)
                out = IOFormatter.formatOutput(player + ": A piece blocks the desired destination.",
                        false, "Please enter a valid move: ");
            else if (status == Status.ILLEGAL_BACKWARDS)
                out = IOFormatter.formatOutput(player + ": Non-King piece cannot move backwards.",
                        false, "Please enter a valid move: ");
            else if (status == Status.NOT_MULTI_JUMP_PIECE)
                out = IOFormatter.formatOutput(player + ": Another piece is in a multi-jump already.",
                        false, "Please enter a valid move: ");
            else if (status == Status.ILLEGAL_TURN) {
                out = IOFormatter.formatOutput(player + ": Desired move is not possible.",
                        false, "Please enter a valid move: ");
                UI.updateStatusMessage(player + ": Desired move is not possible.");
            }
            else if (status == Status.COMPLETED) {
                multiJump = false; //TODO remove
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
        } else {
            winStatus = false;
            rematch = false; //TODO remove
            multiJump = false; //TODO remove
            activePlayer = Player.RED;
            Board.initialize();
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
}