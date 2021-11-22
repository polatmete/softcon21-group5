package ch.uzh.softcon.one.themes.commands;

import ch.uzh.softcon.one.themes.themes.DefaultTheme;

public class DefaultThemeCommandOn implements Command {
    DefaultTheme defaultTheme;

    public DefaultThemeCommandOn(DefaultTheme defaultTheme) {
        this.defaultTheme = defaultTheme;
    }

    public void execute() {
        DefaultTheme.updateColors();
    }
}
