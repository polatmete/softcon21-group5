package ch.uzh.softcon.one.utils;

import ch.uzh.softcon.one.abstraction.Piece;
import ch.uzh.softcon.one.abstraction.Player;
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

import java.util.Optional;

public class UIDesignHelper {
    private static final float windowWidth = 1000;
    private static final float windowHeight = 750;
    private static final float tileWidth = 75;
    private static final float tileHeight = 75;

    public static ButtonType showDialog(String title, String headerText, String textMessage, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(textMessage);
        Optional<ButtonType> res = alert.showAndWait();

        return res.get();
    }

    public static Circle drawPieces(int i, int j, Piece piece) {
        int padding = 10;
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
            circle.setFill(Color.SEASHELL);
            if (piece.isKing()) {
                circle.setFill(Color.HONEYDEW);
            }
        }
        return circle;
    }

    public static Rectangle drawBoard(int i, int j) {
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
        return rectangle;
    }

    public static Group drawButtons(int numberOfButtons, int buttonIdx, String[] buttonNames, Scene scene, Scene game, Scene home) {
        float buttonHeight = 100;
        float buttonWidth = 200;
        int fontSize = 30;
        int verticalSpacing = 25;
        int margin = 75;

        float spacing = (windowWidth - numberOfButtons * buttonWidth) / (numberOfButtons + 1); //Even spacing between buttons (for horizontally centered buttons)
        Group button = new Group();

        Rectangle rectangle = new Rectangle();
        if (scene == home) {
            //horizontally centered
            rectangle.setX(spacing + buttonIdx * (buttonWidth + spacing));
            rectangle.setY(windowHeight/2 - buttonHeight/2);
        } else if (scene == game) {
            //vertically aligned right
            rectangle.setX(windowWidth - margin - buttonWidth);
            rectangle.setY(margin + buttonIdx * (verticalSpacing + buttonHeight));
        }

        rectangle.setWidth(buttonWidth);
        rectangle.setHeight(buttonHeight);
        rectangle.setFill(Color.WHITE);
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

        button.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {rectangle.setFill(Color.LIGHTGRAY); game.setCursor(Cursor.HAND);});
        button.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {rectangle.setFill((Color.WHITE)); game.setCursor(Cursor.DEFAULT);});
        return button;
    }
}
