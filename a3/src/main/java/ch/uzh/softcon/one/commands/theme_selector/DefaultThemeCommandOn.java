package ch.uzh.softcon.one.commands.theme_selector;

import ch.uzh.softcon.one.commands.Command;
import ch.uzh.softcon.one.commands.theme_selector.themes.DefaultTheme;

public class DefaultThemeCommandOn implements Command {

    private DefaultTheme defaultTheme;

    public DefaultThemeCommandOn(DefaultTheme defaultTheme) {
        this.defaultTheme = defaultTheme;
    }

    public boolean execute() {
        defaultTheme.updateColors();
        return true;
    }

    @Override
    public void undo() {

    }
}
