package ch.uzh.softcon.one.commands.theme_selector.themes;

import ch.uzh.softcon.one.abstraction.GameHandling;
import javafx.scene.paint.Color;

public class RedTheme {

    private static final Color darkBoardColor = Color.DARKRED;
    private static final Color lightBoardColor = Color.LIGHTPINK;

    public void updateColors() {
        GameHandling.updateColors(darkBoardColor, lightBoardColor);
    }
}
