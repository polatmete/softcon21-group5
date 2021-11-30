package ch.uzh.softcon.one.commands.theme_selector.themes;

import ch.uzh.softcon.one.abstraction.GameHandling;
import javafx.scene.paint.Color;

public class BlueTheme {

    private static final Color darkBoardColor = Color.DARKBLUE;
    private static final Color lightBoardColor = Color.LIGHTBLUE;

    public void updateColors() {
        GameHandling.updateColors(darkBoardColor, lightBoardColor);
    }
}
