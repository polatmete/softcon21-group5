package ch.uzh.softcon.one.themes.themes;

import ch.uzh.softcon.one.abstraction.GameHandling;
import javafx.scene.paint.Color;

public class DefaultTheme {

    private static final Color darkBoardColor = Color.BLACK;
    private static final Color lightBoardColor = Color.WHITE;

    public static void updateColors() {
        GameHandling.updateColors(darkBoardColor, lightBoardColor);
    }
}
