package ch.uzh.softcon.one.themes.commands;

import ch.uzh.softcon.one.themes.themes.GreenTheme;

public class GreenThemeCommandOn implements Command {

    GreenTheme greenTheme;

    public GreenThemeCommandOn(GreenTheme greenTheme) {
        this.greenTheme = greenTheme;
    }

    public void execute() {
        GreenTheme.updateColors();
    }
}
