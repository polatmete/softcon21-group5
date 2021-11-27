package ch.uzh.softcon.one.commands.theme_selector;

import ch.uzh.softcon.one.commands.Command;
import ch.uzh.softcon.one.commands.theme_selector.themes.GreenTheme;

public class GreenThemeCommandOn implements Command {

    GreenTheme greenTheme;

    public GreenThemeCommandOn(GreenTheme greenTheme) {
        this.greenTheme = greenTheme;
    }

    @Override
    public boolean execute() {
        GreenTheme.updateColors();
        return true;
    }

    @Override
    public void undo() {

    }
}
