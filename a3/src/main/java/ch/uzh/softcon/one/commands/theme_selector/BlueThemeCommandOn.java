package ch.uzh.softcon.one.commands.theme_selector;

import ch.uzh.softcon.one.commands.Command;
import ch.uzh.softcon.one.commands.theme_selector.themes.BlueTheme;

public class BlueThemeCommandOn implements Command {

    private BlueTheme blueTheme;

    public BlueThemeCommandOn(BlueTheme blueTheme) {
        this.blueTheme = blueTheme;
    }

    @Override
    public boolean execute() {
        blueTheme.updateColors();
        return true;
    }

    @Override
    public void undo() {

    }
}
