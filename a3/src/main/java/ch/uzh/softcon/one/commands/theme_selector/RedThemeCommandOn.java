package ch.uzh.softcon.one.commands.theme_selector;

import ch.uzh.softcon.one.commands.Command;
import ch.uzh.softcon.one.commands.theme_selector.themes.RedTheme;

public class RedThemeCommandOn implements Command {

    private RedTheme redTheme;

    public RedThemeCommandOn(RedTheme redTheme) {
        this.redTheme = redTheme;
    }

    @Override
    public boolean execute() {
        redTheme.updateColors();
        return true;
    }

    @Override
    public void undo() {

    }
}
