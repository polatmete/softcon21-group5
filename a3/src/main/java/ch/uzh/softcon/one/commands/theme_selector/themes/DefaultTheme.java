package ch.uzh.softcon.one.commands.theme_selector.themes;

import ch.uzh.softcon.one.abstraction.GameHandling;
import javafx.scene.paint.Color;

public class DefaultTheme {

    private static final Color darkBoardColor = Color.BLACK;
    private static final Color lightBoardColor = Color.WHITE;

    public void updateColors() {
        GameHandling.updateColors(darkBoardColor, lightBoardColor);
    }
}
