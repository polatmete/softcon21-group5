package ch.uzh.softcon.one.themes.commands;

import ch.uzh.softcon.one.themes.themes.RedTheme;

public class RedThemeCommandOn implements Command {

    RedTheme redTheme;

    public RedThemeCommandOn(RedTheme redTheme) {
        this.redTheme = redTheme;
    }

    public void execute() {
        RedTheme.updateColors();

    }
}
