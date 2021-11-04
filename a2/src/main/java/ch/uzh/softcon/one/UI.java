package ch.uzh.softcon.one;

import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

import java.util.Optional;

public class UI {

    private static final float windowWidth = 1000;
    private static final float windowHeight = 750;
    private static final float tileWidth = 75;
    private static final float tileHeight = 75;
    private static int selectedPieceX = -1;
    private static int selectedPieceY = -1;
    private static Group pieces;
    private static Group board;
    private static Group texts;
    private static Group rematch;
    private static Stage stage;
    private static Group buttons;
    private static Scene scene;

    public static void initialize(Stage stage) {

        UI.stage = stage;
        Group root = new Group();
        board = new Group();
        pieces = new Group();
        texts = new Group();
        rematch = new Group();
        buttons = new Group();
        root.getChildren().add(board);
        root.getChildren().add(pieces);
        root.getChildren().add(texts);
        root.getChildren().add(rematch);
        root.getChildren().add(buttons);


        scene = new Scene(root);

        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);
        stage.setTitle("Checkers Game");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        updateStatusMessage("Welcome to the Checkers Game. Player red may begin. Please enter your move");
        drawBoard();
        updatePieces();
        drawButtons();

        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, UI::closeWindowEvent);
    }

    private static void closeWindowEvent(WindowEvent event) {
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
                        circle.setFill(Color.WHITE);
                        if (piece.isKing()) {
                            circle.setFill(Color.GRAY);
                        }
                    }
                    int x = j;
                    int y = i;
                    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            String eventType = e.getEventType().getName();
                            Player activePlayer = Game.activePlayer;
                            if (!isGameOver()) { //makes it so no more moves can be made if the game is over
                                if (eventType.equals("MOUSE_CLICKED")) {
                                    if (isPieceSelected()) {
                                        // TODO: DAs isch grusig
                                        //when clicking on any piece while a piece is selected it unselects that selected piece
                                        Turn turn = new Turn(selectedPieceX, selectedPieceY, x, y, activePlayer);
                                        Game.gameLoop(turn); //performs one iteration of the game loop (to update status message)
                                        unselectPiece();
                                        updatePieces();
                                    } else {
                                        if (Board.getPiece(x, y).getColor() == activePlayer) {
                                            circle.setStrokeWidth(5);
                                            circle.setStroke(Color.BLACK);
                                            selectPiece(x, y);
                                        }
                                    }
                                } else if (eventType.equals ("MOUSE_ENTERED")) {
                                    if (!isPieceSelected() && Board.getPiece(x, y).getColor() == activePlayer) {
                                        circle.setStrokeWidth(2);
                                        circle.setStroke(Color.BLACK);
                                        scene.setCursor(Cursor.HAND);
                                    }
                                } else if (eventType.equals ("MOUSE_EXITED")) {
                                    if (!isPieceSelected()) {
                                        circle.setStrokeWidth(0);
                                        scene.setCursor(Cursor.DEFAULT);
                                    }
                                }
                            }
                        }
                    };
                    circle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
                    circle.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler);
                    circle.addEventFilter(MouseEvent.MOUSE_EXITED, eventHandler);
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
                    rectangle.setFill(Color.LIGHTGRAY);
                } else {
                    rectangle.setFill(Color.DARKGRAY);
                }
                rectangle.setStrokeWidth(5);
                rectangle.setStroke(Color.BLACK);
                int x = i;
                int y = j;
                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (isPieceSelected()) { //if a piece is selected
                            // TODO: DAs isch grusig
                            Turn turn = new Turn(selectedPieceX, selectedPieceY, x, y, Game.activePlayer);
                            Game.gameLoop(turn); //performs one iteration of the game loop
                            unselectPiece();
                            scene.setCursor(Cursor.DEFAULT);
                            updatePieces();
                        }
                    }
                };
                rectangle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
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

    private static void drawButtons() {
        //add buttons
        float buttonHeight = 100;
        float buttonWidth = 200;
        String[] buttonNames = {"New Game", "Load Game", "Save Game"};
        int numberOfButtons = buttonNames.length;
        int fontSize = 30;
        int verticalSpacing = 25;
        int margin = 75;

        //float spacing = (windowWidth - numberOfButtons * buttonWidth) / (numberOfButtons + 1); //Even spacing between buttons (for horizontally centered buttons)

        for (int buttonIdx = 0; buttonIdx < numberOfButtons; buttonIdx++) {

            Group button = new Group();

            Rectangle rectangle = new Rectangle();
            //horizontally centered
            //rectangle.setX(spacing + i * (buttonWidth + spacing));
            //rectangle.setY(windowHeight/2 - buttonHeight/2);

            //vertically aligned right
            rectangle.setX(windowWidth - margin - buttonWidth);
            rectangle.setY(margin + buttonIdx * (verticalSpacing + buttonHeight));
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
            //horizontally centered
            //text.setX((spacing + i * (buttonWidth + spacing)) + buttonWidth/2 - text.getLayoutBounds().getWidth() / 2);
            //text.setY(windowHeight/2 - buttonHeight/2 + buttonHeight/2);

            //vertically aligned right
            text.setX(windowWidth - margin - buttonWidth + buttonWidth/2 - text.getLayoutBounds().getWidth() / 2);
            text.setY(margin + buttonIdx * (verticalSpacing + buttonHeight) + buttonHeight/2);

            button.getChildren().add(rectangle);
            button.getChildren().add(text);

            buttons.getChildren().add(button);

            int finalButtonIdx = buttonIdx;
            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    String eventType = e.getEventType().getName();

                    if (eventType.equals("MOUSE_CLICKED")) {
                        if (buttonNames[finalButtonIdx].equals("New Game")) {
                            System.out.println("New Game");
<<<<<<< HEAD
<<<<<<< HEAD
                            Game.activePlayer = Player.RED;
                            Board.initialize();
=======
                            Game.reset();
>>>>>>> master
=======
                            Game.reset();
>>>>>>> master
                            updatePieces();
                        } else if (buttonNames[finalButtonIdx].equals("Load Game")) {
                            System.out.println("Load Game");
                            BoardLoader.loadBoard();
                            updatePieces();
                        } else if (buttonNames[finalButtonIdx].equals("Save Game")) {
                            System.out.println("Save Game");
                            BoardLoader.saveBoard();
                        }
                    } else if (eventType.equals("MOUSE_ENTERED")) {
                        rectangle.setFill(Color.LIGHTGRAY);
                    } else if (eventType.equals("MOUSE_EXITED")) {
                        rectangle.setFill(Color.GRAY);
                    }
                }
            };
            button.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            button.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler);
            button.addEventFilter(MouseEvent.MOUSE_EXITED, eventHandler);
        }
    }
}

