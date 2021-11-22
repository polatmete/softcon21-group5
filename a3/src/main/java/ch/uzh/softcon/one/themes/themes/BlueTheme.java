package ch.uzh.softcon.one.themes.themes;


import ch.uzh.softcon.one.abstraction.GameHandling;
import javafx.scene.paint.Color;

public class BlueTheme {

    private static final Color darkBoardColor = Color.DARKBLUE;
    private static final Color lightBoardColor = Color.LIGHTBLUE;

    public static void updateColors() {
        GameHandling.updateColors(darkBoardColor, lightBoardColor);
    }
}
