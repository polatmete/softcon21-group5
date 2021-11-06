package ch.uzh.softcon.one.utils;

import ch.uzh.softcon.one.abstraction.Game;
import ch.uzh.softcon.one.abstraction.Board;
import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.turn.Turn;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
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
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Optional;

public class UI {

    private static final float windowWidth = 1000;
    private static final float windowHeight = 750;
    private static final float tileWidth = 75;
    private static final float tileHeight = 75;
    private static int selectedPieceX = -1;
    private static int selectedPieceY = -1;
    private static Stage stage;
    private static Scene game;
    private static Scene home;
    private static Group pieces;
    private static Group board;
    private static Group texts;
    private static Group rematch;
    private static Group gameButtons;
    private static Group homeButtons;

    public static void initialize(Stage stage) {

        UI.stage = stage;

        Group gameRoot = new Group();
        board = new Group();
        pieces = new Group();
        texts = new Group();
        rematch = new Group();
        gameButtons = new Group();
        gameRoot.getChildren().add(board);
        gameRoot.getChildren().add(pieces);
        gameRoot.getChildren().add(texts);
        gameRoot.getChildren().add(rematch);
        gameRoot.getChildren().add(gameButtons);

        game = new Scene(gameRoot);

        homeButtons = new Group();
        Group homeRoot = new Group();
        homeRoot.getChildren().add(homeButtons);

        home = new Scene(homeRoot);

        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);
        stage.setTitle("Checkers Game");
        stage.setResizable(false);
        stage.setScene(game);
        stage.show();

        updateStatusMessage("Welcome to the Checkers Game. Player red may begin. Please enter your move");
        drawBoard();
        updatePieces();
        drawButtons(game);
        drawButtons(home);

        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, UI::closeWindowEvent);
    }

    private static void closeWindowEvent(WindowEvent event) {
        if (!isGameOver() && !Board.isCurrentBoardSaved() && !Board.isInitial()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.CANCEL);
            alert.getButtonTypes().add(ButtonType.YES);
            alert.setTitle("Quit application");
            alert.setContentText(String.format("Close without saving?"));
            alert.initOwner(stage.getOwner());
            Optional<ButtonType> res = alert.showAndWait();

            if(res.isPresent()) {
                if(res.get().equals(ButtonType.CANCEL))
                    event.consume();
            }
        }
    }

    private static boolean isPieceSelected() {
        if (selectedPieceX != -1 || selectedPieceY != -1) {
            return true;
        } else {
            return false;
        }
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
        Player activePlayer = Game.activePlayer();
        if (isPieceSelected()) {
            // TODO: Das isch grusig
            //when clicking on any piece while a piece is selected it unselects that selected piece
            Turn turn = new Turn(selectedPieceX, selectedPieceY, x, y, activePlayer);
            Game.gameLoop(turn); //performs one iteration of the game loop (to update status message)
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
        Player activePlayer = Game.activePlayer();
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
                System.out.println("New Game");
                Game.changePlayer(Player.RED);
                Board.initialize();
                Game.reset();
                updatePieces();
            }
            case "Load Game" -> {
                System.out.println("Load Game");
                BoardLoader.loadBoard();
                updatePieces();
            }
            case "Save Game" -> {
                System.out.println("Save Game");
                BoardLoader.saveBoard();
            }
        }
    }

    private static void updatePieces() {
        pieces.getChildren().clear();
        int padding = 10;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = Board.getPiece(j, i);
                if (piece != null) {
                    Circle circle = new Circle();
                    circle.setCenterX(tileWidth+tileWidth/2 + j*tileWidth);
                    circle.setCenterY(tileHeight+tileHeight/2 + i*tileHeight);
                    circle.setRadius((tileWidth/2)-padding);
                    if (piece.getColor() == Player.RED) {
                        circle.setFill(Color.RED);
                        if (piece.isKing()) {
                            circle.setFill(Color.DARKRED);
                        }
                    } else {
                        circle.setFill(Color.LIGHTGRAY);
                        if (piece.isKing()) {
                            circle.setFill(Color.GRAY);
                        }
                    }
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
                Rectangle rectangle = new Rectangle();
                rectangle.setX(tileWidth*i+tileWidth);
                rectangle.setY(tileHeight*j+tileHeight);
                rectangle.setWidth(tileWidth);
                rectangle.setHeight(tileHeight);
                if ((i + j) % 2 == 0) {
                    rectangle.setFill(Color.WHITE);
                } else {
                    rectangle.setFill(Color.BLACK);
                }
                rectangle.setStrokeWidth(1);
                rectangle.setStroke(Color.BLACK);
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
        text.setX(50);
        text.setY(50);
        text.setFont(Font.font("Verdana", 15));
        texts.getChildren().add(text);
    }

    public static void createRematchInterface() {
        for (int i = 0; i < 2; i++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setX(75+i*525);
            rectangle.setY(75);
            rectangle.setWidth(75);
            rectangle.setHeight(75);

            Text text = new Text();
            text.setX(75+i*525);
            text.setY(125);
            text.setFont(Font.font("Verdana", 40));

            if (i == 0) {
                rectangle.setFill(Color.GREEN);
                text.setText("Yes");
            } else {
                rectangle.setFill(Color.RED);
                text.setText("No");
            }
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(5);
            int finalI = i;
            // TODO: Remove this and replace by message box.
            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (finalI == 0) {
                        UI.updateStatusMessage("Welcome to the Checkers Game. Player red may begin. Please enter your move");
                        Game.gameLoop(null);
                        clearRematchInterface();
                        updatePieces();
                    } else {
                        stage.close();
                    }
                }
            };
            rectangle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            text.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            rematch.getChildren().add(rectangle);
            rematch.getChildren().add(text);
        }

    }

    private static void clearRematchInterface() {
        rematch.getChildren().clear();
    }

    private static boolean isGameOver() {
        return !rematch.getChildren().isEmpty();
    }

    private static void drawButtons(Scene scene) {
        //add buttons
        float buttonHeight = 100;
        float buttonWidth = 200;
        String[] buttonNames = {"Load Game", "New Game"};

        if (scene == game) {
            buttonNames = new String[]{"New Game", "Load Game", "Save Game"};
        } else if (scene == home) {
            buttonNames = new String[]{"Load Game", "Save Game"};
        }
        int numberOfButtons = buttonNames.length;
        int fontSize = 30;
        int verticalSpacing = 25;
        int margin = 75;

        float spacing = (windowWidth - numberOfButtons * buttonWidth) / (numberOfButtons + 1); //Even spacing between buttons (for horizontally centered buttons)

        for (int buttonIdx = 0; buttonIdx < numberOfButtons; buttonIdx++) {

            Group button = new Group();

            Rectangle rectangle = new Rectangle();
            System.out.println(scene == game);
            if (scene == home) {
                //horizontally centered

                rectangle.setX(spacing + buttonIdx * (buttonWidth + spacing));
                rectangle.setY(windowHeight/2 - buttonHeight/2);
            } else if (scene == game) {
                System.out.println("test");
                //vertically aligned right
                rectangle.setX(windowWidth - margin - buttonWidth);
                rectangle.setY(margin + buttonIdx * (verticalSpacing + buttonHeight));
            }

            rectangle.setWidth(buttonWidth);
            rectangle.setHeight(buttonHeight);
            rectangle.setFill(Color.GRAY);
            rectangle.setStrokeWidth(5);
            rectangle.setStroke(Color.BLACK);

            String buttonName = buttonNames[buttonIdx];

            Text text = new Text(buttonName);
            text.setBoundsType(TextBoundsType.VISUAL);
            text.setFont(new Font(fontSize));
            text.setTextAlignment(TextAlignment.CENTER);
            text.setTextOrigin(VPos.CENTER);

            if (scene == home) {
                //horizontally centered
                text.setX((spacing + buttonIdx * (buttonWidth + spacing)) + buttonWidth/2 - text.getLayoutBounds().getWidth() / 2);
                text.setY(windowHeight/2 - buttonHeight/2 + buttonHeight/2);
            } else if (scene == game) {
                //vertically aligned right
                text.setX(windowWidth - margin - buttonWidth + buttonWidth/2 - text.getLayoutBounds().getWidth() / 2);
                text.setY(margin + buttonIdx * (verticalSpacing + buttonHeight) + buttonHeight/2);
            }

            button.getChildren().add(rectangle);
            button.getChildren().add(text);

            if (scene == home) {
                homeButtons.getChildren().add(button);
            } else if (scene == game) {
                gameButtons.getChildren().add(button);
            }

            int finalButtonIdx = buttonIdx;
            String[] finalButtonNames = buttonNames;

            button.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> handleButtonClick(finalButtonNames, finalButtonIdx));
            button.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> rectangle.setFill(Color.LIGHTGRAY));
            button.addEventFilter(MouseEvent.MOUSE_EXITED, e -> rectangle.setFill((Color.GREY)));
        }
    }
}

