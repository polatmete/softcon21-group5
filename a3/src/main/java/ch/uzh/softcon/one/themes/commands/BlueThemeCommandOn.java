package ch.uzh.softcon.one.themes.commands;

import ch.uzh.softcon.one.themes.themes.BlueTheme;

public class BlueThemeCommandOn implements Command {

    BlueTheme blueTheme;

    public BlueThemeCommandOn(BlueTheme blueTheme) {
        this.blueTheme = blueTheme;
    }

    public void execute() {
        BlueTheme.updateColors();
    }
}
