package ch.uzh.softcon.one;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UI {

    private static final float tileWidth = 75;
    private static final float tileHeight = 75;
    private static Piece selectedPiece = null;
    private static int selectedPieceX = -1;
    private static int selectedPieceY = -1;
    private static Group pieces;
    private static Group board;
    private static Group texts;
    private static Group rematch;
    private static Stage stage;

    public UI(Group pieces, Group board, Group texts, Group rematch, Stage stage) {
        UI.pieces = pieces;
        UI.board = board;
        UI.texts = texts;
        UI.rematch = rematch;
        UI.stage = stage;
    }

    public static void updatePieces() {
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
                            circle.setFill(Color.DARKGRAY);
                        }
                    }
                    int finalI = i;
                    int finalJ = j;
                    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            if (rematch.getChildren().isEmpty()) { //makes it so no more moves can be made if the game is over
                                if (selectedPieceX != -1 || selectedPieceY != -1) {
                                    //when clicking on any piece while a piece is selected it unselects that selected piece
                                    Turn turn = new Turn(selectedPieceX, selectedPieceY, finalI, finalJ, Game.getActivePlayer());
                                    Game.gameLoop(turn); //performs one iteration of the game loop (to update status message)
                                    selectedPieceX = -1;
                                    selectedPieceY = -1;
                                    updatePieces();
                                } else {
                                    circle.setStrokeWidth(5);
                                    circle.setStroke(Color.BLACK);
                                    selectedPieceX = finalJ;
                                    selectedPieceY = finalI;
                                }
                            }
                        }
                    };
                    circle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
                    pieces.getChildren().add(circle);
                }
            }
        }
    }

    public static void drawBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setX(tileWidth*i+tileWidth);
                rectangle.setY(tileHeight*j+tileHeight);
                rectangle.setWidth(tileWidth);
                rectangle.setHeight(tileHeight);
                rectangle.setFill(Color.GRAY);
                rectangle.setStrokeWidth(5);
                rectangle.setStroke(Color.BLACK);
                int finalI = i;
                int finalJ = j;
                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (selectedPieceX != -1 || selectedPieceY != -1) {
                            Turn turn = new Turn(selectedPieceX, selectedPieceY, finalI, finalJ, Game.getActivePlayer());
                            Game.gameLoop(turn); //performs one iteration of the game loop
                            selectedPieceX = -1;
                            selectedPieceY = -1;
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

    public static void clearRematchInterface() {
        rematch.getChildren().clear();
    }
}
