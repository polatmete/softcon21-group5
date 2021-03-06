package ch.uzh.softcon.one.commands.theme_selector.themes;

import ch.uzh.softcon.one.abstraction.GameHandling;
import javafx.scene.paint.Color;

public class GreenTheme {

    private static final Color darkBoardColor = Color.DARKGREEN;
    private static final Color lightBoardColor = Color.LIGHTGREEN;

    public void updateColors() {
        GameHandling.updateColors(darkBoardColor, lightBoardColor);
    }
}
