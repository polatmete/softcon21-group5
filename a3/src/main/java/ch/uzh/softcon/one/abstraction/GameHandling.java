package ch.uzh.softcon.one.abstraction;

import ch.uzh.softcon.one.observables.Observer;
import ch.uzh.softcon.one.observables.player.ActivePlayerChannel;
import ch.uzh.softcon.one.observables.player.PlayerChangeSubscriber;
import ch.uzh.softcon.one.observables.player.PlayerSubject;
import ch.uzh.softcon.one.observables.status.StatusChangeSubscriber;
import ch.uzh.softcon.one.observables.status.StatusMessageChannel;
import ch.uzh.softcon.one.observables.status.StatusSubject;
import ch.uzh.softcon.one.observables.status.WinChannel;
import ch.uzh.softcon.one.statecontrol.CommandTurn;
import ch.uzh.softcon.one.turn.Turn;
import ch.uzh.softcon.one.turn.TurnHandler;
import ch.uzh.softcon.one.utils.BoardLoader;
import ch.uzh.softcon.one.utils.UIDesignHelper;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameHandling {

    private static int selectedPieceX = -1;
    private static int selectedPieceY = -1;
    private static Stage stage;
    private static Scene game;
    private static Scene home;
    private static Group pieces;
    private static Group board;
    private static Group texts;
    private static Group homeButtons;
    private static Group gameButtons;
    private static final float windowWidth = 1000;
    private static final float windowHeight = 750;

    private static PlayerSubject playerSubject;
    private static StatusSubject statusSubject;

    public static void initialize(Stage stage) {
        registerObservers();

        GameHandling.stage = stage;
        Group gameRoot = new Group();
        board = new Group();
        pieces = new Group();
        texts = new Group();
        gameButtons = new Group();
        homeButtons = new Group();
        gameRoot.getChildren().add(board);
        gameRoot.getChildren().add(pieces);
        gameRoot.getChildren().add(texts);
        gameRoot.getChildren().add(gameButtons);

        game = new Scene(gameRoot, Color.BEIGE);

        Group homeRoot = new Group();

        home = new Scene(homeRoot);

        stage.setTitle("Checkers Game");
        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);
        stage.setResizable(false);
        //stage.setScene(game);
        stage.setScene(home);
        stage.show();

        playerSubject.changePlayer(Player.RED);
        playerSubject.notifyObservers();
        Board.initialize();

        updateStatusMessage("Welcome to the Checkers Game. Player red may begin. Please enter your move");
        drawBoard();
        updatePieces();
        drawButtons(game);
        drawButtons(home);

        drawHomePage(homeRoot);

        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, GameHandling::closeWindowEvent);
    }

    public static PlayerSubject playerSubject() {
        return playerSubject;
    }

    private static void registerObservers() {
        playerSubject = new PlayerChangeSubscriber();
        Observer activePlayerObserver = new ActivePlayerChannel();

        playerSubject.registerObserver(activePlayerObserver);

        statusSubject = new StatusChangeSubscriber();
        Observer statusMessageObserver = new StatusMessageChannel();
        Observer winObserver = new WinChannel();

        statusSubject.registerObserver(statusMessageObserver);
        statusSubject.registerObserver(winObserver);
    }

    public static Player activePlayer() {
        return playerSubject.activePlayer();
    }

    public static void setAndNotifyStatusChange(String msg) {
        statusSubject.setStatusMessage(msg);
        statusSubject.notifyObservers();
    }

    public static void changePlayer(Player p) {
        playerSubject.changePlayer(p);
        playerSubject.notifyObservers();
    }

    public static void win(Player player) {
        updatePieces();
        statusSubject.setWin();
        statusSubject.setStatusMessage(String.format("Congratulations player %s, you won! Do you want a revenge?", player.toString().toLowerCase()));
        statusSubject.notifyObservers();
    }

    public static void reset() {
        playerSubject.changePlayer(Player.RED);
        playerSubject.notifyObservers();
        Board.initialize();
        updateStatusMessage("Welcome to the Checkers Game. Player red may begin. Please enter your move");
    }

    private static void closeWindowEvent(WindowEvent event) {
        if (game.getWindow() != null) {
            if (event != null) event.consume();
            ButtonType res = UIDesignHelper.showDialog("Quit game", "There might be unsaved changes",
                    "Are you sure you want to quit?", Alert.AlertType.CONFIRMATION);
            if (res.equals(ButtonType.YES))
                if (event != null)
                    stage.close();
                else
                    stage.setScene(home);
        }
    }

    private static boolean isPieceSelected() {
        return selectedPieceX != -1 && selectedPieceY != -1;
    }

    private static void unselectPiece() {
        selectedPieceX = -1;
        selectedPieceY = -1;
    }

    private static void selectPiece(int x, int y) {
        selectedPieceX = x;
        selectedPieceY = y;
    }

    private static void handleClick(int x, int y, Circle circle) {
        Player activePlayer = playerSubject.activePlayer();
        if (isPieceSelected()) {
            //when clicking on any piece while a piece is selected it unselects that selected piece
            Turn turn = new Turn(selectedPieceX, selectedPieceY, x, y);
            TurnHandler.runTurnSequence(turn);
            unselectPiece();
            game.setCursor(Cursor.DEFAULT);
            updatePieces();
        } else {
            if (Board.getPiece(x, y).getColor() == activePlayer) {
                circle.setStrokeWidth(6);
                circle.setStroke(Color.GOLD);
                selectPiece(x, y);
            }
        }
    }

    private static void handleHover(int x, int y, Circle circle) {
        Player activePlayer = playerSubject.activePlayer();
        if (!isPieceSelected() && Board.getPiece(x, y).getColor() == activePlayer) {
            circle.setStrokeWidth(3);
            circle.setStroke(Color.GOLD);
            game.setCursor(Cursor.HAND);
        }
    }

    private static void handleExit(Circle circle) {
        if (!isPieceSelected()) {
            circle.setStrokeWidth(0);
            game.setCursor(Cursor.DEFAULT);
        }
    }

    private static void handleButtonClick(String[] buttonNames, int finalButtonIdx) {
        switch (buttonNames[finalButtonIdx]) {
            case "New Game" -> {
                stage.setScene(game);
                reset();
                updatePieces();
            }
            case "Load Game" -> {
                if(BoardLoader.loadBoard()) {
                    stage.setScene(game);
                    updatePieces();
                }
            }
            case "Save Game" -> {
                BoardLoader.saveBoard();
            }
            case "Back to Main" -> {
                closeWindowEvent(null);
            }
            case "Undo Move" -> {
                new CommandTurn().undo();
                updatePieces();
            }
        }
    }

    private static void updatePieces() {
        pieces.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = Board.getPiece(j, i);
                if (piece != null) {
                    Circle circle = UIDesignHelper.drawPieces(i, j, piece);
                    int x = j;
                    int y = i;
                    circle.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> handleClick(x, y, circle));
                    circle.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> handleHover(x, y, circle));
                    circle.addEventFilter(MouseEvent.MOUSE_EXITED, e -> handleExit(circle));
                    pieces.getChildren().add(circle);
                }
            }
        }
    }

    private static void drawBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle rectangle = UIDesignHelper.drawBoard(i, j);
                int x = i;
                int y = j;
                rectangle.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                    if (isPieceSelected()) handleClick(x, y, null);
                });
                board.getChildren().add(rectangle);
            }
        }
    }

    public static void updateStatusMessage(String string) {
        texts.getChildren().clear();
        Text text = new Text();
        text.setText(string);
        text.setX(75);
        text.setY(50);
        text.setFont(Font.font("Verdana", 15));
        texts.getChildren().add(text);
    }

    public static void createRematchInterface() {
        ButtonType res = UIDesignHelper.showDialog("Game over", String.format("Player %s won the game!",
                activePlayer().toString().toLowerCase()), "Do you want revenge?", Alert.AlertType.CONFIRMATION);

        if (res.equals(ButtonType.YES))
            reset();
        else
            stage.close();
    }

    private static void drawButtons(Scene scene) {
        //add buttons
        String[] buttonNames;

        if (scene == game) buttonNames = new String[]{"Save Game", "Back to Main", "Undo Move"};
        else buttonNames = new String[]{"New Game", "Load Game"};

        int numberOfButtons = buttonNames.length;

        for (int buttonIdx = 0; buttonIdx < numberOfButtons; buttonIdx++) {
            Group button = UIDesignHelper.drawButtons(numberOfButtons, buttonIdx, buttonNames, scene, game, home);

            if (scene == home) {
                homeButtons.getChildren().add(button);
            } else if (scene == game) {
                gameButtons.getChildren().add(button);
            }

            int finalButtonIdx = buttonIdx;
            String[] finalButtonNames = buttonNames;
            button.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> handleButtonClick(finalButtonNames, finalButtonIdx));
        }
    }


    private static void drawHomePage(Group homeRoot) {
        Group background = UIDesignHelper.drawHomeBackground();
        Group title = UIDesignHelper.drawHomeTitle();
        drawButtons(home); //to add buttons to homeButtons

        homeRoot.getChildren().add(background);
        homeRoot.getChildren().add(homeButtons); //added here to make sure that the background is in the background
        homeRoot.getChildren().add(title);
    }
}